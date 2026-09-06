package rs.ac.bg.etf.pp1;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private static final int DEFAULT_INTEGER_WIDTH = 5;
	private static final int DEFAULT_CHARACTER_WIDTH = 1;
	private static final int MISSING_METHOD_ADDRESS = -1;
	private static final String THIS_NAME = "this";

	private final List<Obj> classObjects = new ArrayList<Obj>();
	private final Set<Struct> abstractClassTypes =
		Collections.newSetFromMap(new IdentityHashMap<Struct, Boolean>());
	private final Map<Struct, Integer> virtualTableAddresses =
		new IdentityHashMap<Struct, Integer>();
	private final Map<SyntaxNode, Integer> scratchAddresses =
		new IdentityHashMap<SyntaxNode, Integer>();
	private final Deque<LoopContext> loops = new ArrayDeque<LoopContext>();

	private Obj programObject;
	private Obj currentClass;
	private Obj currentMethod;
	private boolean generated;

	public int getMainPc() {
		return Code.mainPc;
	}

	public void generate(Program program) {
		if (generated) {
			throw new IllegalStateException("Code has already been generated");
		}
		if (program == null || program.obj == null) {
			throw new IllegalStateException(
				"Semantic analysis must complete before code generation"
			);
		}

		generated = true;
		resetRuntimeCode();
		programObject = program.obj;
		collectClasses(program);
		allocateVirtualTables();
		generateGlobalDeclarations(program.getGlobalDeclarationList());
		generateMethodDeclarations(program.getMethodDeclarationList());

		if (Code.mainPc < 0) {
			throw new IllegalStateException("Code generation did not find void main()");
		}
	}

	@Override
	public void visit(Program program) {
		if (!generated) {
			generate(program);
		}
	}

	private void resetRuntimeCode() {
		Code.buf = new byte[8192];
		Code.pc = 0;
		Code.mainPc = -1;
		Code.dataSize = 0;
		Code.greska = false;
	}

	private void collectClasses(Program program) {
		program.traverseBottomUp(new VisitorAdaptor() {
			@Override
			public void visit(CompleteClassDeclaration node) {
				addClass(node.obj, false);
			}

			@Override
			public void visit(ClassDeclarationRecoveredTypedField node) {
				addClass(node.obj, false);
			}

			@Override
			public void visit(CompleteAbstractClassDeclaration node) {
				addClass(node.obj, true);
			}

			@Override
			public void visit(AbstractClassDeclarationRecoveredTypedField node) {
				addClass(node.obj, true);
			}
		});
	}

	private void addClass(Obj classObject, boolean isAbstract) {
		if (classObject == null || classObject == Tab.noObj) {
			throw new IllegalStateException("Class has no semantic symbol");
		}
		classObjects.add(classObject);
		if (isAbstract) {
			abstractClassTypes.add(classObject.getType());
		}
	}

	private void allocateVirtualTables() {
		Code.dataSize = globalDataSize();
		for (Obj classObject : classObjects) {
			Struct classType = classObject.getType();
			int tableAddress = Code.dataSize;
			virtualTableAddresses.put(classType, tableAddress);
			classObject.setAdr(tableAddress);

			for (Obj member : classType.getMembers()) {
				if (member.getKind() == Obj.Meth) {
					Code.dataSize += member.getName().length() + 2;
				}
			}
			Code.dataSize++;
		}
	}

	private int globalDataSize() {
		int size = 0;
		for (Obj symbol : programObject.getLocalSymbols()) {
			if (symbol.getKind() == Obj.Var && symbol.getLevel() == 0) {
				size = Math.max(size, symbol.getAdr() + 1);
			}
		}
		return size;
	}

	private void generateGlobalDeclarations(GlobalDeclarationList declarations) {
		if (!(declarations instanceof GlobalDeclarationsMultiple)) {
			return;
		}

		GlobalDeclarationsMultiple multiple = (GlobalDeclarationsMultiple) declarations;
		generateGlobalDeclarations(multiple.getGlobalDeclarationList());
		GlobalDeclaration declaration = multiple.getGlobalDeclaration();

		if (declaration instanceof GlobalClassDeclaration) {
			generateClass(
				((GlobalClassDeclaration) declaration).getClassDeclaration()
			);
		} else if (declaration instanceof GlobalAbstractClassDeclaration) {
			generateAbstractClass(
				((GlobalAbstractClassDeclaration) declaration)
					.getAbstractClassDeclaration()
			);
		}
	}

	private void generateClass(ClassDeclaration declaration) {
		Obj previousClass = currentClass;
		currentClass = declaration.obj;

		if (declaration instanceof CompleteClassDeclaration) {
			ConcreteMethodSectionOpt section =
				((CompleteClassDeclaration) declaration).getConcreteMethodSectionOpt();
			generateConcreteMethodSection(section);
		} else if (declaration instanceof ClassDeclarationRecoveredTypedField) {
			generateMethodDeclarations(
				((ClassDeclarationRecoveredTypedField) declaration)
					.getMethodDeclarationList()
			);
		}

		currentClass = previousClass;
	}

	private void generateConcreteMethodSection(ConcreteMethodSectionOpt section) {
		if (section instanceof ConcreteMethodSection) {
			generateMethodDeclarations(
				((ConcreteMethodSection) section).getMethodDeclarationList()
			);
		} else if (section instanceof ConcreteMethodSectionRecoveredField) {
			generateMethodDeclarations(
				((ConcreteMethodSectionRecoveredField) section)
					.getMethodDeclarationList()
			);
		}
	}

	private void generateAbstractClass(AbstractClassDeclaration declaration) {
		Obj previousClass = currentClass;
		currentClass = declaration.obj;

		if (declaration instanceof CompleteAbstractClassDeclaration) {
			AbstractMethodSectionOpt section =
				((CompleteAbstractClassDeclaration) declaration)
					.getAbstractMethodSectionOpt();
			generateAbstractMethodSection(section);
		} else if (declaration
			instanceof AbstractClassDeclarationRecoveredTypedField) {
			generateAbstractClassMembers(
				((AbstractClassDeclarationRecoveredTypedField) declaration)
					.getAbstractClassMemberList()
			);
		}

		currentClass = previousClass;
	}

	private void generateAbstractMethodSection(AbstractMethodSectionOpt section) {
		if (section instanceof AbstractMethodSection) {
			generateAbstractClassMembers(
				((AbstractMethodSection) section).getAbstractClassMemberList()
			);
		} else if (section instanceof AbstractMethodSectionRecoveredField) {
			generateAbstractClassMembers(
				((AbstractMethodSectionRecoveredField) section)
					.getAbstractClassMemberList()
			);
		}
	}

	private void generateAbstractClassMembers(AbstractClassMemberList members) {
		if (!(members instanceof AbstractClassMembersMultiple)) {
			return;
		}

		AbstractClassMembersMultiple multiple =
			(AbstractClassMembersMultiple) members;
		generateAbstractClassMembers(multiple.getAbstractClassMemberList());
		AbstractClassMember member = multiple.getAbstractClassMember();

		if (member instanceof AbstractClassConcreteMethod) {
			generateMethod(
				((AbstractClassConcreteMethod) member).getMethodDeclaration()
			);
		} else if (member instanceof AbstractClassAbstractMethod) {
			AbstractMethodDeclaration method =
				((AbstractClassAbstractMethod) member)
					.getAbstractMethodDeclaration();
			if (method.obj != null && method.obj != Tab.noObj) {
				method.obj.setAdr(MISSING_METHOD_ADDRESS);
			}
		}
	}

	private void generateMethodDeclarations(MethodDeclarationList declarations) {
		if (!(declarations instanceof MethodDeclarationsMultiple)) {
			return;
		}

		MethodDeclarationsMultiple multiple =
			(MethodDeclarationsMultiple) declarations;
		generateMethodDeclarations(multiple.getMethodDeclarationList());
		generateMethod(multiple.getMethodDeclaration());
	}

	private void generateMethod(MethodDeclaration methodDeclaration) {
		Obj method = methodDeclaration.obj;
		if (method == null || method == Tab.noObj) {
			throw new IllegalStateException("Method has no semantic symbol");
		}

		CounterVisitor.FormParamCounter parameterCounter =
			new CounterVisitor.FormParamCounter();
		methodDeclaration.getMethodHeader().traverseBottomUp(parameterCounter);
		int expectedParameterCount =
			parameterCounter.getCount() + (currentClass == null ? 0 : 1);
		if (method.getLevel() != expectedParameterCount) {
			throw new IllegalStateException(
				"Semantic parameter count does not match method "
					+ method.getName()
			);
		}

		CounterVisitor.VarCounter variableCounter = new CounterVisitor.VarCounter();
		methodDeclaration
			.getLocalVariableDeclarationList()
			.traverseBottomUp(variableCounter);

		int declaredFrameSize = Math.max(
			method.getLevel() + variableCounter.getCount(),
			methodFrameSize(method)
		);
		int frameSize = planScratchSlots(methodDeclaration, declaredFrameSize);

		Obj previousMethod = currentMethod;
		currentMethod = method;
		method.setAdr(Code.pc);
		if (currentClass == null && "main".equals(method.getName())) {
			Code.mainPc = Code.pc;
		}

		Code.put(Code.enter);
		Code.put(method.getLevel());
		Code.put(frameSize);

		if (Code.pc - 3 == Code.mainPc) {
			emitVirtualTableInitialization();
		}

		generateStatements(methodDeclaration.getStatementList());
		if (method.getType() == Tab.noType) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		} else {
			Code.put(Code.trap);
			Code.put(1);
		}

		currentMethod = previousMethod;
	}

	private int planScratchSlots(
		MethodDeclaration methodDeclaration,
		final int firstFreeSlot
	) {
		final int[] nextSlot = { firstFreeSlot };
		methodDeclaration.traverseBottomUp(new VisitorAdaptor() {
			private void allocate(SyntaxNode node, int size) {
				scratchAddresses.put(node, nextSlot[0]);
				nextSlot[0] += size;
			}

			@Override
			public void visit(DesignatorCall node) {
				if (node.getDesignator() instanceof DesignatorMember) {
					allocate(node, 1);
				}
			}

			@Override
			public void visit(FactorDesignator node) {
				if (node.getDesignator() instanceof DesignatorMember
					&& node.getFactorCallOpt() instanceof FactorCall) {
					allocate((FactorCall) node.getFactorCallOpt(), 1);
				}
			}

			@Override
			public void visit(FindAnyStatement node) {
				allocate(node, 3);
			}

			@Override
			public void visit(MapStatement node) {
				allocate(node, 3);
			}

			@Override
			public void visit(ForeachHeader node) {
				allocate(node, 2);
			}
		});
		return nextSlot[0];
	}

	private int methodFrameSize(Obj method) {
		int size = 0;
		for (Obj local : method.getLocalSymbols()) {
			if (local.getKind() == Obj.Var) {
				size = Math.max(size, local.getAdr() + 1);
			}
		}
		return size;
	}

	private void emitVirtualTableInitialization() {
		for (Obj classObject : classObjects) {
			Struct classType = classObject.getType();
			int address = virtualTableAddresses.get(classType);

			for (Obj method : classType.getMembers()) {
				if (method.getKind() != Obj.Meth) {
					continue;
				}
				for (int index = 0; index < method.getName().length(); index++) {
					emitPutStatic(address++, method.getName().charAt(index));
				}
				emitPutStatic(address++, -1);

				int methodAddress =
					resolveMethodAddress(classType, method.getName());
				if (methodAddress < 0 && !abstractClassTypes.contains(classType)) {
					throw new IllegalStateException(
						"Concrete class " + classObject.getName()
							+ " has no implementation for " + method.getName()
					);
				}
				emitPutStatic(address++, methodAddress);
			}
			emitPutStatic(address, -2);
		}
	}

	private int resolveMethodAddress(Struct classType, String methodName) {
		Struct current = classType;
		while (current != null && current != Tab.noType) {
			for (Obj member : current.getMembers()) {
				if (member.getKind() == Obj.Meth
					&& methodName.equals(member.getName())
					&& member.getAdr() >= 0) {
					return member.getAdr();
				}
			}
			current = current.getElemType();
		}
		return MISSING_METHOD_ADDRESS;
	}

	private void generateStatements(StatementList statements) {
		if (!(statements instanceof StatementsMultiple)) {
			return;
		}

		StatementsMultiple multiple = (StatementsMultiple) statements;
		generateStatements(multiple.getStatementList());
		generateStatement(multiple.getStatement());
	}

	private void generateStatement(Statement statement) {
		if (statement instanceof MatchedStatementNode) {
			generateMatchedStatement(
				((MatchedStatementNode) statement).getMatchedStatement()
			);
		} else if (statement instanceof UnmatchedStatementNode) {
			generateUnmatchedStatement(
				((UnmatchedStatementNode) statement).getUnmatchedStatement()
			);
		}
	}

	private void generateMatchedStatement(MatchedStatement statement) {
		if (statement instanceof MatchedDesignatorStatement) {
			generateDesignatorStatement(
				((MatchedDesignatorStatement) statement).getDesignatorStatement()
			);
		} else if (statement instanceof MatchedFindAnyStatement) {
			generateFindAny(
				((MatchedFindAnyStatement) statement).getFindAnyStatement()
			);
		} else if (statement instanceof MatchedMapStatement) {
			generateMap(((MatchedMapStatement) statement).getMapStatement());
		} else if (statement instanceof MatchedIfElseStatement) {
			generateMatchedIfElse((MatchedIfElseStatement) statement);
		} else if (statement instanceof MatchedForStatement) {
			MatchedForStatement forStatement = (MatchedForStatement) statement;
			generateFor(
				forStatement.getForHeader(),
				forStatement.getMatchedStatement()
			);
		} else if (statement instanceof MatchedForeachStatement) {
			MatchedForeachStatement foreachStatement =
				(MatchedForeachStatement) statement;
			generateForeach(
				foreachStatement.getForeachHeader(),
				foreachStatement.getMatchedStatement()
			);
		} else if (statement instanceof MatchedBreakStatement) {
			loops.peek().breakPatches.add(emitJumpPlaceholder());
		} else if (statement instanceof MatchedContinueStatement) {
			loops.peek().continuePatches.add(emitJumpPlaceholder());
		} else if (statement instanceof MatchedReturnStatement) {
			generateReturn((MatchedReturnStatement) statement);
		} else if (statement instanceof MatchedReadStatement) {
			generateRead((MatchedReadStatement) statement);
		} else if (statement instanceof MatchedPrintStatement) {
			generatePrint((MatchedPrintStatement) statement);
		} else if (statement instanceof MatchedBlockStatement) {
			generateStatements(
				((MatchedBlockStatement) statement).getStatementList()
			);
		}
	}

	private void generateUnmatchedStatement(UnmatchedStatement statement) {
		if (statement instanceof UnmatchedIfStatement) {
			UnmatchedIfStatement ifStatement = (UnmatchedIfStatement) statement;
			BranchResult branch =
				generateIfCondition(ifStatement.getIfCondition());
			patchAll(branch.truePatches, Code.pc);
			generateStatement(ifStatement.getStatement());
			patchAll(branch.falsePatches, Code.pc);
		} else if (statement instanceof UnmatchedIfElseStatement) {
			UnmatchedIfElseStatement ifStatement =
				(UnmatchedIfElseStatement) statement;
			BranchResult branch =
				generateIfCondition(ifStatement.getIfCondition());
			patchAll(branch.truePatches, Code.pc);
			generateMatchedStatement(ifStatement.getMatchedStatement());
			int endPatch = emitJumpPlaceholder();
			patchAll(branch.falsePatches, Code.pc);
			generateUnmatchedStatement(ifStatement.getUnmatchedStatement());
			patch(endPatch, Code.pc);
		} else if (statement instanceof UnmatchedForStatement) {
			UnmatchedForStatement forStatement =
				(UnmatchedForStatement) statement;
			generateFor(
				forStatement.getForHeader(),
				forStatement.getUnmatchedStatement()
			);
		} else if (statement instanceof UnmatchedForeachStatement) {
			UnmatchedForeachStatement foreachStatement =
				(UnmatchedForeachStatement) statement;
			generateForeach(
				foreachStatement.getForeachHeader(),
				foreachStatement.getUnmatchedStatement()
			);
		}
	}

	private void generateMatchedIfElse(MatchedIfElseStatement statement) {
		BranchResult branch = generateIfCondition(statement.getIfCondition());
		patchAll(branch.truePatches, Code.pc);
		generateMatchedStatement(statement.getMatchedStatement());
		int endPatch = emitJumpPlaceholder();
		patchAll(branch.falsePatches, Code.pc);
		generateMatchedStatement(statement.getMatchedStatement1());
		patch(endPatch, Code.pc);
	}

	private BranchResult generateIfCondition(IfCondition condition) {
		if (condition instanceof ValidIfCondition) {
			return generateCondition(
				((ValidIfCondition) condition).getCondition()
			);
		}

		BranchResult result = new BranchResult();
		result.truePatches.add(emitJumpPlaceholder());
		return result;
	}

	private void generateFor(ForHeader header, SyntaxNode body) {
		generateDesignatorStatementOpt(header.getDesignatorStatementOpt());
		int conditionAddress = Code.pc;
		BranchResult condition = null;

		if (header.getConditionOpt() instanceof ConditionPresent) {
			condition = generateCondition(
				((ConditionPresent) header.getConditionOpt()).getCondition()
			);
			patchAll(condition.truePatches, Code.pc);
		}

		LoopContext loop = new LoopContext();
		loops.push(loop);
		if (body instanceof MatchedStatement) {
			generateMatchedStatement((MatchedStatement) body);
		} else {
			generateUnmatchedStatement((UnmatchedStatement) body);
		}

		int updateAddress = Code.pc;
		patchAll(loop.continuePatches, updateAddress);
		generateDesignatorStatementOpt(header.getDesignatorStatementOpt1());
		Code.putJump(conditionAddress);

		int endAddress = Code.pc;
		if (condition != null) {
			patchAll(condition.falsePatches, endAddress);
		}
		patchAll(loop.breakPatches, endAddress);
		loops.pop();
	}

	private void generateForeach(ForeachHeader header, SyntaxNode body) {
		int scratch = scratch(header, 2);
		int arrayAddress = scratch;
		int indexAddress = scratch + 1;

		generateDesignatorValue(header.getDesignator1());
		emitStoreLocal(arrayAddress);
		Code.loadConst(0);
		emitStoreLocal(indexAddress);

		int conditionAddress = Code.pc;
		emitLoadLocal(indexAddress);
		emitLoadLocal(arrayAddress);
		Code.put(Code.arraylength);
		int endPatch = emitFalseJump(Code.lt);

		prepareStore(header.getDesignator());
		emitLoadLocal(arrayAddress);
		emitLoadLocal(indexAddress);
		Code.put(
			isCharacter(header.getDesignator1().obj.getType().getElemType())
				? Code.baload
				: Code.aload
		);
		Code.store(header.getDesignator().obj);

		LoopContext loop = new LoopContext();
		loops.push(loop);
		if (body instanceof MatchedStatement) {
			generateMatchedStatement((MatchedStatement) body);
		} else {
			generateUnmatchedStatement((UnmatchedStatement) body);
		}

		int updateAddress = Code.pc;
		patchAll(loop.continuePatches, updateAddress);
		emitLoadLocal(indexAddress);
		Code.loadConst(1);
		Code.put(Code.add);
		emitStoreLocal(indexAddress);
		Code.putJump(conditionAddress);

		int endAddress = Code.pc;
		patch(endPatch, endAddress);
		patchAll(loop.breakPatches, endAddress);
		loops.pop();
	}

	private void generateDesignatorStatementOpt(
		DesignatorStatementOpt optionalStatement
	) {
		if (optionalStatement instanceof DesignatorStatementPresent) {
			generateDesignatorStatement(
				((DesignatorStatementPresent) optionalStatement)
					.getDesignatorStatement()
			);
		}
	}

	private void generateReturn(MatchedReturnStatement statement) {
		ReturnExpressionOpt expression = statement.getReturnExpressionOpt();
		if (expression instanceof ReturnExpressionPresent) {
			generateExpression(
				((ReturnExpressionPresent) expression).getExpression()
			);
		}
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	private void generateRead(MatchedReadStatement statement) {
		Designator destination = statement.getDesignator();
		prepareStore(destination);
		Code.put(isCharacter(destination.obj.getType()) ? Code.bread : Code.read);
		Code.store(destination.obj);
	}

	private void generatePrint(MatchedPrintStatement statement) {
		generateExpression(statement.getExpression());
		boolean character = isCharacter(statement.getExpression().struct);
		int width = character
			? DEFAULT_CHARACTER_WIDTH
			: DEFAULT_INTEGER_WIDTH;
		if (statement.getPrintWidthOpt() instanceof PrintWidthPresent) {
			width =
				((PrintWidthPresent) statement.getPrintWidthOpt()).getN1();
		}
		Code.loadConst(width);
		Code.put(character ? Code.bprint : Code.print);
	}

	private void generateDesignatorStatement(DesignatorStatement statement) {
		if (statement instanceof DesignatorAssignment) {
			DesignatorAssignment assignment = (DesignatorAssignment) statement;
			prepareStore(assignment.getDesignator());
			generateExpression(assignment.getExpression());
			Code.store(assignment.getDesignator().obj);
		} else if (statement instanceof DesignatorCall) {
			DesignatorCall call = (DesignatorCall) statement;
			generateCall(
				call,
				call.getDesignator(),
				call.getActualParametersOpt(),
				true
			);
		} else if (statement instanceof DesignatorIncrement) {
			generateIncrement(
				((DesignatorIncrement) statement).getDesignator(),
				Code.add
			);
		} else if (statement instanceof DesignatorDecrement) {
			generateIncrement(
				((DesignatorDecrement) statement).getDesignator(),
				Code.sub
			);
		}
	}

	private void generateIncrement(Designator designator, int operation) {
		prepareStore(designator);
		if (designator.obj.getKind() == Obj.Fld) {
			Code.put(Code.dup);
		} else if (designator.obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designator.obj);
		Code.loadConst(1);
		Code.put(operation);
		Code.store(designator.obj);
	}

	private void generateFindAny(FindAnyStatement statement) {
		prepareStore(statement.getDesignator());
		int scratch = scratch(statement, 3);
		int arrayAddress = scratch;
		int valueAddress = scratch + 1;
		int indexAddress = scratch + 2;

		generateDesignatorValue(statement.getDesignator1());
		emitStoreLocal(arrayAddress);
		generateExpression(statement.getExpression());
		emitStoreLocal(valueAddress);
		Code.loadConst(0);
		emitStoreLocal(indexAddress);

		int loopAddress = Code.pc;
		emitLoadLocal(indexAddress);
		emitLoadLocal(arrayAddress);
		Code.put(Code.arraylength);
		int endPatch = emitFalseJump(Code.lt);

		emitLoadLocal(arrayAddress);
		emitLoadLocal(indexAddress);
		Code.put(
			isCharacter(statement.getDesignator1().obj.getType().getElemType())
				? Code.baload
				: Code.aload
		);
		emitLoadLocal(valueAddress);
		int nextPatch = emitFalseJump(Code.eq);
		Code.loadConst(1);
		int resultPatch = emitJumpPlaceholder();

		patch(nextPatch, Code.pc);
		emitLoadLocal(indexAddress);
		Code.loadConst(1);
		Code.put(Code.add);
		emitStoreLocal(indexAddress);
		Code.putJump(loopAddress);

		patch(endPatch, Code.pc);
		Code.loadConst(0);
		patch(resultPatch, Code.pc);
		Code.store(statement.getDesignator().obj);
	}

	private void generateMap(MapStatement statement) {
		prepareStore(statement.getDesignator());
		int scratch = scratch(statement, 3);
		int sourceAddress = scratch;
		int resultAddress = scratch + 1;
		int indexAddress = scratch + 2;

		generateDesignatorValue(statement.getDesignator1());
		emitStoreLocal(sourceAddress);
		emitLoadLocal(sourceAddress);
		Code.put(Code.arraylength);
		Struct resultElementType =
			statement.getDesignator().obj.getType().getElemType();
		Code.put(Code.newarray);
		Code.put(isCharacter(resultElementType) ? 0 : 1);
		emitStoreLocal(resultAddress);
		Code.loadConst(0);
		emitStoreLocal(indexAddress);

		Obj mapper = resolveMapper(statement.getMapper());
		int loopAddress = Code.pc;
		emitLoadLocal(indexAddress);
		emitLoadLocal(sourceAddress);
		Code.put(Code.arraylength);
		int endPatch = emitFalseJump(Code.lt);

		emitLoadLocal(sourceAddress);
		emitLoadLocal(indexAddress);
		Code.put(
			isCharacter(statement.getDesignator1().obj.getType().getElemType())
				? Code.baload
				: Code.aload
		);
		Code.store(mapper);

		emitLoadLocal(resultAddress);
		emitLoadLocal(indexAddress);
		generateExpression(statement.getExpression());
		Code.put(isCharacter(resultElementType) ? Code.bastore : Code.astore);

		emitLoadLocal(indexAddress);
		Code.loadConst(1);
		Code.put(Code.add);
		emitStoreLocal(indexAddress);
		Code.putJump(loopAddress);

		patch(endPatch, Code.pc);
		emitLoadLocal(resultAddress);
		Code.store(statement.getDesignator().obj);
	}

	private Obj resolveMapper(String name) {
		Obj mapper = findByName(currentMethod.getLocalSymbols(), name);
		if (mapper == null && currentClass != null) {
			mapper = findByName(currentClass.getType().getMembers(), name);
		}
		if (mapper == null) {
			mapper = findByName(programObject.getLocalSymbols(), name);
		}
		if (mapper == null || mapper.getKind() != Obj.Var) {
			throw new IllegalStateException("Cannot resolve map variable " + name);
		}
		return mapper;
	}

	private Obj findByName(Collection<Obj> symbols, String name) {
		for (Obj symbol : symbols) {
			if (name.equals(symbol.getName())) {
				return symbol;
			}
		}
		return null;
	}

	private void generateExpression(Expression expression) {
		if (expression instanceof ExpressionWithoutTernary) {
			generateNonTernaryExpression(
				((ExpressionWithoutTernary) expression)
					.getNonTernaryExpression()
			);
		} else {
			generateTernaryExpression(
				((ExpressionWithTernary) expression).getTernaryExpression()
			);
		}
	}

	private void generateTernaryExpression(TernaryExpression expression) {
		BranchResult branch = generateCondition(expression.getCondition());
		patchAll(branch.truePatches, Code.pc);
		generateExpression(expression.getExpression());
		int endPatch = emitJumpPlaceholder();
		patchAll(branch.falsePatches, Code.pc);
		generateExpression(expression.getExpression1());
		patch(endPatch, Code.pc);
	}

	private void generateNonTernaryExpression(
		NonTernaryExpression expression
	) {
		if (expression instanceof PositiveNonTernaryExpression) {
			PositiveNonTernaryExpression positive =
				(PositiveNonTernaryExpression) expression;
			generateTerm(positive.getTerm());
			generateAdditiveTail(positive.getAdditiveTail());
		} else {
			NegativeNonTernaryExpression negative =
				(NegativeNonTernaryExpression) expression;
			generateTerm(negative.getTerm());
			Code.put(Code.neg);
			generateAdditiveTail(negative.getAdditiveTail());
		}
	}

	private void generateAdditiveTail(AdditiveTail tail) {
		if (!(tail instanceof AdditiveTermsMultiple)) {
			return;
		}

		AdditiveTermsMultiple multiple = (AdditiveTermsMultiple) tail;
		generateAdditiveTail(multiple.getAdditiveTail());
		generateTerm(multiple.getTerm());
		Code.put(
			multiple.getAdditiveOperator() instanceof AdditivePlus
				? Code.add
				: Code.sub
		);
	}

	private void generateTerm(Term term) {
		if (term instanceof TermFactor) {
			generateFactor(((TermFactor) term).getFactor());
			return;
		}

		TermWithMultiplication multiplication =
			(TermWithMultiplication) term;
		generateTerm(multiplication.getTerm());
		generateFactor(multiplication.getFactor());
		MultiplicativeOperator operator =
			multiplication.getMultiplicativeOperator();
		if (operator instanceof MultiplicativeMultiply) {
			Code.put(Code.mul);
		} else if (operator instanceof MultiplicativeDivide) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}

	private void generateFactor(Factor factor) {
		if (factor instanceof FactorDesignator) {
			FactorDesignator designatorFactor = (FactorDesignator) factor;
			if (designatorFactor.getFactorCallOpt() instanceof FactorCall) {
				FactorCall call =
					(FactorCall) designatorFactor.getFactorCallOpt();
				generateCall(
					call,
					designatorFactor.getDesignator(),
					call.getActualParametersOpt(),
					false
				);
			} else {
				generateDesignatorValue(designatorFactor.getDesignator());
			}
		} else if (factor instanceof FactorConstant) {
			generateConstant(((FactorConstant) factor).getConstValue());
		} else if (factor instanceof FactorNewObject) {
			generateNewObject((FactorNewObject) factor);
		} else if (factor instanceof FactorNewArray) {
			FactorNewArray array = (FactorNewArray) factor;
			generateExpression(array.getExpression());
			Code.put(Code.newarray);
			Code.put(isCharacter(array.getType().struct) ? 0 : 1);
		} else {
			generateExpression(
				((FactorParenthesized) factor).getExpression()
			);
		}
	}

	private void generateConstant(ConstValue constant) {
		if (constant instanceof NumericConstValue) {
			Code.loadConst(((NumericConstValue) constant).getValue());
		} else if (constant instanceof CharacterConstValue) {
			String value = ((CharacterConstValue) constant).getValue();
			Code.loadConst(value.charAt(1));
		} else {
			Code.loadConst(
				"true".equals(((BooleanConstValue) constant).getValue())
					? 1
					: 0
			);
		}
	}

	private void generateNewObject(FactorNewObject object) {
		Struct classType = object.getType().struct;
		Integer tableAddress = virtualTableAddresses.get(classType);
		if (tableAddress == null) {
			throw new IllegalStateException("Class has no virtual table");
		}

		Code.put(Code.new_);
		Code.put2(classType.getNumberOfFields() * 4);
		Code.put(Code.dup);
		Code.loadConst(tableAddress);
		Code.put(Code.putfield);
		Code.put2(0);
	}

	private void generateCall(
		SyntaxNode callNode,
		Designator designator,
		ActualParametersOpt parameters,
		boolean discardResult
	) {
		Obj method = designator.obj;
		if (method == Tab.chrObj || method == Tab.ordObj) {
			generateActualParameters(parameters);
		} else if (method == Tab.lenObj) {
			generateActualParameters(parameters);
			Code.put(Code.arraylength);
		} else if (isVirtualCall(designator, method)) {
			generateVirtualCall(callNode, designator, parameters, method);
		} else {
			generateActualParameters(parameters);
			emitStaticCall(method.getAdr());
		}

		if (discardResult && method.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}

	private boolean isVirtualCall(Designator designator, Obj method) {
		if (designator instanceof DesignatorMember) {
			return true;
		}
		if (currentClass == null) {
			return false;
		}
		for (Obj member : currentClass.getType().getMembers()) {
			if (member == method && member.getKind() == Obj.Meth) {
				return true;
			}
		}
		return false;
	}

	private void generateVirtualCall(
		SyntaxNode callNode,
		Designator designator,
		ActualParametersOpt parameters,
		Obj method
	) {
		if (designator instanceof DesignatorMember) {
			Designator owner =
				((DesignatorMember) designator).getDesignator();
			int objectAddress = scratch(callNode, 1);
			generateDesignatorValue(owner);
			Code.put(Code.dup);
			emitStoreLocal(objectAddress);
			generateActualParameters(parameters);
			emitLoadLocal(objectAddress);
		} else {
			emitLoadThis();
			generateActualParameters(parameters);
			emitLoadThis();
		}

		Code.put(Code.getfield);
		Code.put2(0);
		Code.put(Code.invokevirtual);
		for (int index = 0; index < method.getName().length(); index++) {
			Code.put4(method.getName().charAt(index));
		}
		Code.put4(-1);
	}

	private void generateActualParameters(ActualParametersOpt parameters) {
		if (parameters instanceof ActualParametersPresent) {
			generateActualParameters(
				((ActualParametersPresent) parameters).getActualParameters()
			);
		}
	}

	private void generateActualParameters(ActualParameters parameters) {
		if (parameters instanceof ActualParametersSingle) {
			generateExpression(
				((ActualParametersSingle) parameters).getExpression()
			);
		} else {
			ActualParametersMultiple multiple =
				(ActualParametersMultiple) parameters;
			generateActualParameters(multiple.getActualParameters());
			generateExpression(multiple.getExpression());
		}
	}

	private void generateDesignatorValue(Designator designator) {
		if (designator instanceof DesignatorName) {
			if (designator.obj.getKind() == Obj.Fld) {
				emitLoadThis();
			}
			Code.load(designator.obj);
		} else if (designator instanceof DesignatorMember) {
			DesignatorMember member = (DesignatorMember) designator;
			generateDesignatorValue(member.getDesignator());
			Code.load(member.obj);
		} else if (designator instanceof DesignatorLength) {
			generateDesignatorValue(
				((DesignatorLength) designator).getDesignator()
			);
			Code.put(Code.arraylength);
		} else {
			DesignatorElement element = (DesignatorElement) designator;
			generateDesignatorValue(element.getDesignator());
			generateExpression(element.getExpression());
			Code.load(element.obj);
		}
	}

	private void prepareStore(Designator designator) {
		if (designator instanceof DesignatorName) {
			if (designator.obj.getKind() == Obj.Fld) {
				emitLoadThis();
			}
		} else if (designator instanceof DesignatorMember) {
			generateDesignatorValue(
				((DesignatorMember) designator).getDesignator()
			);
		} else if (designator instanceof DesignatorElement) {
			DesignatorElement element = (DesignatorElement) designator;
			generateDesignatorValue(element.getDesignator());
			generateExpression(element.getExpression());
		}
	}

	private BranchResult generateCondition(Condition condition) {
		if (condition instanceof ConditionSingleTerm) {
			return generateConditionTerm(
				((ConditionSingleTerm) condition).getConditionTerm()
			);
		}

		ConditionWithOr disjunction = (ConditionWithOr) condition;
		BranchResult left = generateCondition(disjunction.getCondition());
		patchAll(left.falsePatches, Code.pc);
		BranchResult right =
			generateConditionTerm(disjunction.getConditionTerm());
		right.truePatches.addAll(0, left.truePatches);
		return right;
	}

	private BranchResult generateConditionTerm(ConditionTerm term) {
		if (term instanceof ConditionTermSingleFactor) {
			return generateConditionFactor(
				((ConditionTermSingleFactor) term).getConditionFactor()
			);
		}

		ConditionTermWithAnd conjunction = (ConditionTermWithAnd) term;
		BranchResult left =
			generateConditionTerm(conjunction.getConditionTerm());
		patchAll(left.truePatches, Code.pc);
		BranchResult right =
			generateConditionFactor(conjunction.getConditionFactor());
		right.falsePatches.addAll(0, left.falsePatches);
		return right;
	}

	private BranchResult generateConditionFactor(ConditionFactor factor) {
		if (factor instanceof ConditionFactorExpression) {
			generateNonTernaryExpression(
				((ConditionFactorExpression) factor)
					.getNonTernaryExpression()
			);
			Code.loadConst(0);
			return emitBranch(Code.ne);
		}

		ConditionFactorRelational relational =
			(ConditionFactorRelational) factor;
		generateNonTernaryExpression(
			relational.getNonTernaryExpression()
		);
		generateNonTernaryExpression(
			relational.getNonTernaryExpression1()
		);
		return emitBranch(relationalOperator(relational.getRelationalOperator()));
	}

	private int relationalOperator(RelationalOperator operator) {
		if (operator instanceof RelationalEqual) {
			return Code.eq;
		}
		if (operator instanceof RelationalNotEqual) {
			return Code.ne;
		}
		if (operator instanceof RelationalLess) {
			return Code.lt;
		}
		if (operator instanceof RelationalLessOrEqual) {
			return Code.le;
		}
		if (operator instanceof RelationalGreater) {
			return Code.gt;
		}
		return Code.ge;
	}

	private BranchResult emitBranch(int operator) {
		BranchResult result = new BranchResult();
		result.falsePatches.add(emitFalseJump(operator));
		result.truePatches.add(emitJumpPlaceholder());
		return result;
	}

	private int emitFalseJump(int operator) {
		Code.putFalseJump(operator, 0);
		return Code.pc - 2;
	}

	private int emitJumpPlaceholder() {
		Code.putJump(0);
		return Code.pc - 2;
	}

	private void emitStaticCall(int targetAddress) {
		int instructionAddress = Code.pc;
		Code.put(Code.call);
		Code.put2(targetAddress - instructionAddress);
	}

	private void emitLoadThis() {
		Code.put(Code.load_n);
	}

	private void emitPutStaticFromStack(int address) {
		Code.put(Code.putstatic);
		Code.put2(address);
	}

	private void emitPutStatic(int address, int value) {
		Code.loadConst(value);
		emitPutStaticFromStack(address);
	}

	private void emitLoadLocal(int address) {
		if (address >= 0 && address <= 3) {
			Code.put(Code.load_n + address);
		} else {
			Code.put(Code.load);
			Code.put(address);
		}
	}

	private void emitStoreLocal(int address) {
		if (address >= 0 && address <= 3) {
			Code.put(Code.store_n + address);
		} else {
			Code.put(Code.store);
			Code.put(address);
		}
	}

	private int scratch(SyntaxNode owner, int size) {
		Integer address = scratchAddresses.get(owner);
		if (address == null) {
			throw new IllegalStateException(
				"Scratch space was not planned for " + owner.getClass().getName()
			);
		}
		return address;
	}

	private void patchAll(List<Integer> patches, int targetAddress) {
		for (Integer patchAddress : patches) {
			patch(patchAddress, targetAddress);
		}
	}

	private void patch(int patchAddress, int targetAddress) {
		Code.put2(
			patchAddress,
			targetAddress - patchAddress + 1
		);
	}

	private boolean isCharacter(Struct type) {
		return type != null && type.getKind() == Struct.Char;
	}

	private static final class BranchResult {
		private final List<Integer> truePatches = new ArrayList<Integer>();
		private final List<Integer> falsePatches = new ArrayList<Integer>();
	}

	private static final class LoopContext {
		private final List<Integer> breakPatches = new ArrayList<Integer>();
		private final List<Integer> continuePatches = new ArrayList<Integer>();
	}
}
