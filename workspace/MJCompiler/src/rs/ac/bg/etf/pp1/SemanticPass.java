package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.factory.SymbolTableFactory;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SemanticPass extends VisitorAdaptor {

	public static final Struct boolType = new Struct(Struct.Bool);

	private static final String THIS_NAME = "this";
	private static final String VTABLE_FIELD_NAME = "$tvf";
	private static final int MAX_LOCAL_VARIABLES = 256;
	private static final int MAX_GLOBAL_VARIABLES = 65536;
	private static final int MAX_CLASS_FIELDS = 65536;

	private final Logger log = Logger.getLogger(getClass());
	private final Map<SyntaxNode, List<Struct>> actualParameterTypes =
		new IdentityHashMap<SyntaxNode, List<Struct>>();
	private final Map<Obj, List<Struct>> methodParameterTypes =
		new IdentityHashMap<Obj, List<Struct>>();
	private final Map<Obj, Obj> overriddenMethods = new IdentityHashMap<Obj, Obj>();
	private final Set<Obj> inheritedMembers = identitySet();
	private final Set<Obj> abstractMethods = identitySet();
	private final Set<Obj> formalParameters = identitySet();
	private final Set<Struct> abstractClasses = identitySet();

	private Obj programObject;
	private Obj currentClass;
	private Obj currentMethod;
	private Scope currentClassScope;
	private Struct currentParentClass = Tab.noType;
	private boolean currentClassIsAbstract;
	private boolean errorDetected;
	private int currentFormalParameterCount;
	private int loopDepth;
	public int nVars;

	public SemanticPass() {
		Tab.init();
		Tab.insert(Obj.Type, "bool", boolType);

		methodParameterTypes.put(
			Tab.chrObj,
			Collections.singletonList(Tab.intType)
		);
		methodParameterTypes.put(
			Tab.ordObj,
			Collections.singletonList(Tab.charType)
		);
		methodParameterTypes.put(
			Tab.lenObj,
			Collections.singletonList(new Struct(Struct.Array, Tab.noType))
		);
	}

	private static <T> Set<T> identitySet() {
		return Collections.newSetFromMap(new IdentityHashMap<T, Boolean>());
	}

	public boolean passed() {
		return !errorDetected;
	}

	public int getNVars() {
		return nVars;
	}

	public Obj getProgramObject() {
		return programObject;
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		log.error(withLine(message, info));
	}

	public void report_info(String message, SyntaxNode info) {
		log.info(withLine(message, info));
	}

	private String withLine(String message, SyntaxNode info) {
		if (info != null && info.getLine() > 0) {
			return message + " na liniji " + info.getLine();
		}
		return message;
	}

	private void reportSymbol(String name, Obj symbol, SyntaxNode info) {
		if (symbol != null && symbol != Tab.noObj) {
			int line = info == null ? 0 : info.getLine();
			log.info(
				"Pretraga na " + line + " (" + name + "), nadjeno " + symbol
			);
		}
	}

	private boolean isNoType(Struct type) {
		return type == null || type == Tab.noType;
	}

	private boolean isBoolean(Struct type) {
		return type == boolType || (type != null && type.getKind() == Struct.Bool);
	}

	private boolean isPrimitive(Struct type) {
		return type == Tab.intType || type == Tab.charType || isBoolean(type);
	}

	private boolean isReference(Struct type) {
		return type != null
			&& (type.getKind() == Struct.Array || type.getKind() == Struct.Class);
	}

	private boolean equivalent(Struct first, Struct second) {
		if (first == second) {
			return true;
		}
		if (first == null || second == null) {
			return false;
		}
		if (first.getKind() == Struct.Array && second.getKind() == Struct.Array) {
			return equivalent(first.getElemType(), second.getElemType());
		}
		return false;
	}

	private boolean compatible(Struct first, Struct second) {
		if (equivalent(first, second)) {
			return true;
		}
		return first == Tab.nullType && isReference(second)
			|| second == Tab.nullType && isReference(first)
			|| isSubclassOf(first, second)
			|| isSubclassOf(second, first);
	}

	private boolean assignable(Struct source, Struct destination) {
		if (equivalent(source, destination)) {
			return true;
		}
		if (source == Tab.nullType && isReference(destination)) {
			return true;
		}
		if (source != null
			&& destination != null
			&& source.getKind() == Struct.Array
			&& destination.getKind() == Struct.Array
			&& destination.getElemType() == Tab.noType) {
			return true;
		}
		return isSubclassOf(source, destination);
	}

	private boolean isSubclassOf(Struct source, Struct destination) {
		if (source == null
			|| destination == null
			|| source == Tab.nullType
			|| source.getKind() != Struct.Class
			|| destination.getKind() != Struct.Class) {
			return false;
		}

		Struct current = source.getElemType();
		while (current != null && current != Tab.noType) {
			if (current == destination) {
				return true;
			}
			if (current.getKind() != Struct.Class) {
				break;
			}
			current = current.getElemType();
		}
		return false;
	}

	private boolean isWritable(Obj symbol) {
		if (symbol == null || symbol == Tab.noObj || THIS_NAME.equals(symbol.getName())) {
			return false;
		}
		int kind = symbol.getKind();
		return kind == Obj.Var || kind == Obj.Fld || kind == Obj.Elem;
	}

	private boolean isValue(Obj symbol) {
		if (symbol == null || symbol == Tab.noObj) {
			return false;
		}
		int kind = symbol.getKind();
		return kind == Obj.Con || kind == Obj.Var || kind == Obj.Fld || kind == Obj.Elem;
	}

	private Obj insertUnique(int kind, String name, Struct type, SyntaxNode info) {
		Obj existing = Tab.currentScope().findSymbol(name);
		if (existing != null) {
			report_error("Simbol " + name + " je vec deklarisan u ovom opsegu", info);
			return Tab.noObj;
		}
		return Tab.insert(kind, name, type);
	}

	private <T extends SyntaxNode> T ancestor(
		SyntaxNode node,
		Class<T> ancestorType
	) {
		SyntaxNode current = node == null ? null : node.getParent();
		while (current != null) {
			if (ancestorType.isInstance(current)) {
				return ancestorType.cast(current);
			}
			current = current.getParent();
		}
		return null;
	}

	private Struct declaredVariableType(VariableDeclarator declarator) {
		ValidVariableDeclaration declaration =
			ancestor(declarator, ValidVariableDeclaration.class);
		if (declaration == null || declaration.getType() == null) {
			return Tab.noType;
		}
		Struct type = declaration.getType().struct;
		if (declarator.getArraySuffix() instanceof ArraySuffixPresent) {
			return new Struct(Struct.Array, type);
		}
		return type;
	}

	private Struct declaredConstantType(ConstDeclarator declarator) {
		ConstDeclaration declaration = ancestor(declarator, ConstDeclaration.class);
		return declaration == null ? Tab.noType : declaration.getType().struct;
	}

	private ReturnType methodReturnType(MethodName methodName) {
		if (methodName.getParent() instanceof ValidMethodHeader) {
			return ((ValidMethodHeader) methodName.getParent()).getReturnType();
		}
		if (methodName.getParent() instanceof MethodHeaderRecoveredFormalParameter) {
			return ((MethodHeaderRecoveredFormalParameter) methodName.getParent())
				.getReturnType();
		}
		return null;
	}

	private MethodName methodName(MethodHeader header) {
		if (header instanceof ValidMethodHeader) {
			return ((ValidMethodHeader) header).getMethodName();
		}
		if (header instanceof MethodHeaderRecoveredFormalParameter) {
			return ((MethodHeaderRecoveredFormalParameter) header).getMethodName();
		}
		return null;
	}

	private Struct parentType(ConcreteClassHeader header) {
		if (header instanceof ConcreteClassHeaderWithExtends) {
			return ((ConcreteClassHeaderWithExtends) header).getType().struct;
		}
		return Tab.noType;
	}

	private Struct parentType(AbstractClassHeader header) {
		if (header instanceof AbstractClassHeaderWithExtends) {
			return ((AbstractClassHeaderWithExtends) header).getType().struct;
		}
		return Tab.noType;
	}

	private String className(ConcreteClassHeader header) {
		if (header instanceof ConcreteClassHeaderWithoutExtends) {
			return ((ConcreteClassHeaderWithoutExtends) header).getName();
		}
		if (header instanceof ConcreteClassHeaderWithExtends) {
			return ((ConcreteClassHeaderWithExtends) header).getName();
		}
		return ((ConcreteClassHeaderRecoveredExtends) header).getName();
	}

	private String className(AbstractClassHeader header) {
		if (header instanceof AbstractClassHeaderWithoutExtends) {
			return ((AbstractClassHeaderWithoutExtends) header).getName();
		}
		if (header instanceof AbstractClassHeaderWithExtends) {
			return ((AbstractClassHeaderWithExtends) header).getName();
		}
		return ((AbstractClassHeaderRecoveredExtends) header).getName();
	}

	private void openClass(
		String name,
		Struct parent,
		boolean isAbstract,
		SyntaxNode info
	) {
		if (parent == null || (parent != Tab.noType && parent.getKind() != Struct.Class)) {
			report_error("Tip iz extends deklaracije mora biti klasa", info);
			parent = Tab.noType;
		}

		Struct classType = new Struct(Struct.Class);
		classType.setElementType(parent);
		Obj classObject = insertUnique(Obj.Type, name, classType, info);
		if (classObject == Tab.noObj) {
			classObject = new Obj(Obj.Type, name, classType);
		}

		currentClass = classObject;
		currentParentClass = parent;
		currentClassIsAbstract = isAbstract;
		if (isAbstract) {
			abstractClasses.add(classType);
		}

		Tab.openScope();
		currentClassScope = Tab.currentScope();
		if (parent != Tab.noType) {
			copyInheritedMembers(parent);
		} else {
			Tab.insert(Obj.Fld, VTABLE_FIELD_NAME, Tab.noType);
		}
	}

	private void copyInheritedMembers(Struct parent) {
		for (Obj member : parent.getMembers()) {
			if (member.getKind() == Obj.Fld) {
				Struct fieldType = VTABLE_FIELD_NAME.equals(member.getName())
					? Tab.noType
					: member.getType();
				Obj copy = Tab.insert(Obj.Fld, member.getName(), fieldType);
				inheritedMembers.add(copy);
			} else if (member.getKind() == Obj.Meth) {
				Obj copy = Tab.insert(Obj.Meth, member.getName(), member.getType());
				copy.setAdr(-1);
				copy.setLevel(member.getLevel());
				copy.setLocals(cloneMethodLocals(member));
				inheritedMembers.add(copy);
				methodParameterTypes.put(
					copy,
					new ArrayList<Struct>(parametersFor(member))
				);
				if (abstractMethods.contains(member)) {
					abstractMethods.add(copy);
				}
			}
		}
	}

	private SymbolDataStructure cloneMethodLocals(Obj method) {
		SymbolDataStructure locals =
			SymbolTableFactory.instance().createSymbolTableDataStructure();
		for (Obj local : method.getLocalSymbols()) {
			Struct type = THIS_NAME.equals(local.getName())
				? currentClass.getType()
				: local.getType();
			Obj copy = new Obj(
				local.getKind(),
				local.getName(),
				type,
				local.getAdr(),
				local.getLevel()
			);
			copy.setFpPos(local.getFpPos());
			locals.insertKey(copy);
		}
		return locals;
	}

	private void finishClass(Obj classObject, SyntaxNode info) {
		if (currentClass == null) {
			return;
		}

		if (currentClassScope.getnVars() > MAX_CLASS_FIELDS) {
			report_error(
				"Klasa ne sme imati vise od " + MAX_CLASS_FIELDS + " polja",
				info
			);
		}

		if (!currentClassIsAbstract) {
			for (Obj member : currentClassScope.values()) {
				if (member.getKind() == Obj.Meth && abstractMethods.contains(member)) {
					report_error(
						"Klasa " + currentClass.getName()
							+ " ne implementira apstraktnu metodu " + member.getName(),
						info
					);
				}
			}
		}

		Tab.chainLocalSymbols(currentClass.getType());
		Tab.closeScope();
		if (classObject != null) {
			classObject.setLocals(currentClass.getType().getMembersTable());
		}

		currentClass = null;
		currentClassScope = null;
		currentParentClass = Tab.noType;
		currentClassIsAbstract = false;
	}

	private void openMethod(MethodName methodName) {
		ReturnType returnType = methodReturnType(methodName);
		Struct resultType = returnType == null || returnType.struct == null
			? Tab.noType
			: returnType.struct;
		String name = methodName.getName();
		Obj overridden = null;
		Obj existing = Tab.currentScope().findSymbol(name);

		if (existing != null
			&& currentClass != null
			&& existing.getKind() == Obj.Meth
			&& inheritedMembers.contains(existing)) {
			overridden = existing;
			Tab.currentScope().getLocals().deleteKey(name);
		} else if (existing != null) {
			report_error("Metoda " + name + " je vec deklarisana", methodName);
		}

		Obj method;
		if (existing == null || overridden != null) {
			method = Tab.insert(Obj.Meth, name, resultType);
		} else {
			method = new Obj(Obj.Meth, name, resultType);
		}

		if (overridden != null) {
			overriddenMethods.put(method, overridden);
		}

		methodName.obj = method;
		currentMethod = method;
		currentFormalParameterCount = 0;
		methodParameterTypes.put(method, new ArrayList<Struct>());
		Tab.openScope();

		if (currentClass != null) {
			Obj thisObject = Tab.insert(Obj.Var, THIS_NAME, currentClass.getType());
			thisObject.setFpPos(currentFormalParameterCount++);
			formalParameters.add(thisObject);
		}
	}

	private void finishMethod(
		MethodHeader header,
		boolean isAbstract,
		SyntaxNode info
	) {
		if (currentMethod == null) {
			return;
		}

		currentMethod.setLevel(currentFormalParameterCount);
		validateOverride(currentMethod, info);
		if (isAbstract) {
			abstractMethods.add(currentMethod);
		}

		if (Tab.currentScope().getnVars() > MAX_LOCAL_VARIABLES) {
			report_error(
				"Metoda ne sme imati vise od " + MAX_LOCAL_VARIABLES
					+ " lokalnih promenljivih",
				info
			);
		}

		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		header.obj = currentMethod;

		currentMethod = null;
		currentFormalParameterCount = 0;
	}

	private void validateOverride(Obj method, SyntaxNode info) {
		Obj inherited = overriddenMethods.get(method);
		if (inherited == null) {
			return;
		}

		boolean valid = equivalent(method.getType(), inherited.getType());
		List<Struct> actual = parametersFor(method);
		List<Struct> expected = parametersFor(inherited);
		if (actual.size() != expected.size()) {
			valid = false;
		} else {
			for (int index = 0; index < actual.size(); index++) {
				if (!equivalent(actual.get(index), expected.get(index))) {
					valid = false;
					break;
				}
			}
		}

		if (!valid) {
			report_error(
				"Redefinisana metoda " + method.getName()
					+ " mora imati isti potpis kao nasledjena metoda",
				info
			);
			if (abstractMethods.contains(inherited)) {
				abstractMethods.add(method);
			}
		}
	}

	private List<Struct> parametersFor(Obj method) {
		List<Struct> parameters = methodParameterTypes.get(method);
		if (parameters != null) {
			return parameters;
		}

		List<Obj> locals = new ArrayList<Obj>(method.getLocalSymbols());
		Collections.sort(locals, new Comparator<Obj>() {
			@Override
			public int compare(Obj first, Obj second) {
				return first.getFpPos() - second.getFpPos();
			}
		});

		List<Struct> inferred = new ArrayList<Struct>();
		int formalCount = method.getLevel();
		for (Obj local : locals) {
			if (formalCount <= 0) {
				break;
			}
			formalCount--;
			if (!THIS_NAME.equals(local.getName())) {
				inferred.add(local.getType());
			}
		}
		methodParameterTypes.put(method, inferred);
		return inferred;
	}

	private boolean checkCall(Obj method, List<Struct> actual, SyntaxNode info) {
		if (method == null || method == Tab.noObj) {
			return false;
		}
		if (method.getKind() != Obj.Meth) {
			report_error(method.getName() + " nije metoda ili funkcija", info);
			return false;
		}

		List<Struct> expected = parametersFor(method);
		if (actual.size() != expected.size()) {
			report_error(
				"Poziv " + method.getName() + " ima pogresan broj argumenata",
				info
			);
			return false;
		}

		boolean valid = true;
		for (int index = 0; index < actual.size(); index++) {
			if (!assignable(actual.get(index), expected.get(index))) {
				report_error(
					"Argument " + (index + 1) + " poziva " + method.getName()
						+ " nema odgovarajuci tip",
					info
				);
				valid = false;
			}
		}
		return valid;
	}

	private List<Struct> actualParameters(SyntaxNode node) {
		List<Struct> types = actualParameterTypes.get(node);
		return types == null ? Collections.<Struct>emptyList() : types;
	}

	private Obj findMember(Struct classType, String name) {
		if (classType == null || classType.getKind() != Struct.Class) {
			return Tab.noObj;
		}
		if (currentClass != null
			&& classType == currentClass.getType()
			&& currentClassScope != null) {
			Obj currentMember = currentClassScope.findSymbol(name);
			if (currentMember != null) {
				return currentMember;
			}
		}
		for (Obj member : classType.getMembers()) {
			if (name.equals(member.getName())) {
				return member;
			}
		}
		Struct parent = classType.getElemType();
		if (parent != null && parent != Tab.noType) {
			return findMember(parent, name);
		}
		return Tab.noObj;
	}

	private Obj resolveName(String name, SyntaxNode info) {
		Obj symbol = Tab.find(name);
		if (symbol == Tab.noObj && currentParentClass != Tab.noType) {
			symbol = findMember(currentParentClass, name);
		}
		if (symbol == Tab.noObj) {
			report_error("Simbol " + name + " nije deklarisan", info);
		} else {
			reportSymbol(name, symbol, info);
		}
		return symbol;
	}

	@Override
	public void visit(ProgramName node) {
		programObject = insertUnique(Obj.Prog, node.getName(), Tab.noType, node);
		if (programObject == Tab.noObj) {
			programObject = new Obj(Obj.Prog, node.getName(), Tab.noType);
		}
		node.obj = programObject;
		Tab.openScope();
	}

	@Override
	public void visit(Program node) {
		node.obj = node.getProgramName().obj;
		Obj main = Tab.currentScope().findSymbol("main");
		if (main == null
			|| main.getKind() != Obj.Meth
			|| main.getType() != Tab.noType
			|| main.getLevel() != 0) {
			report_error("Program mora imati globalnu void main() funkciju", node);
		}

		nVars = Tab.currentScope().getnVars();
		if (nVars > MAX_GLOBAL_VARIABLES) {
			report_error(
				"Program ne sme imati vise od " + MAX_GLOBAL_VARIABLES
					+ " globalnih promenljivih",
				node
			);
		}
		Tab.chainLocalSymbols(programObject);
		Tab.closeScope();
	}

	@Override
	public void visit(Type node) {
		Obj type = Tab.find(node.getName());
		if (type == Tab.noObj) {
			report_error("Tip " + node.getName() + " nije deklarisan", node);
			node.struct = Tab.noType;
		} else if (type.getKind() != Obj.Type) {
			report_error(node.getName() + " ne predstavlja tip", node);
			node.struct = Tab.noType;
		} else {
			node.struct = type.getType();
		}
	}

	@Override
	public void visit(VoidReturnType node) {
		node.struct = Tab.noType;
	}

	@Override
	public void visit(TypedReturnType node) {
		node.struct = node.getType().struct;
	}

	@Override
	public void visit(NumericConstValue node) {
		node.struct = Tab.intType;
	}

	@Override
	public void visit(CharacterConstValue node) {
		node.struct = Tab.charType;
	}

	@Override
	public void visit(BooleanConstValue node) {
		node.struct = boolType;
	}

	@Override
	public void visit(ConstDeclarator node) {
		Struct declaredType = declaredConstantType(node);
		Struct valueType = node.getConstValue().struct;
		if (!isPrimitive(declaredType)) {
			report_error("Konstanta mora biti tipa int, char ili bool", node);
			node.obj = Tab.noObj;
			return;
		}
		if (!equivalent(valueType, declaredType)) {
			report_error("Tip vrednosti konstante se ne poklapa sa deklarisanim tipom", node);
			node.obj = Tab.noObj;
			return;
		}

		Obj constant = insertUnique(Obj.Con, node.getName(), declaredType, node);
		node.obj = constant;
		if (constant == Tab.noObj) {
			return;
		}
		if (node.getConstValue() instanceof NumericConstValue) {
			constant.setAdr(((NumericConstValue) node.getConstValue()).getValue());
		} else if (node.getConstValue() instanceof CharacterConstValue) {
			String value = ((CharacterConstValue) node.getConstValue()).getValue();
			constant.setAdr(value.charAt(1));
		} else {
			String value = ((BooleanConstValue) node.getConstValue()).getValue();
			constant.setAdr("true".equals(value) ? 1 : 0);
		}
	}

	@Override
	public void visit(VariableDeclarator node) {
		Struct type = declaredVariableType(node);
		int kind = currentClass != null && currentMethod == null ? Obj.Fld : Obj.Var;
		node.obj = insertUnique(kind, node.getName(), type, node);
	}

	@Override
	public void visit(FormalParameter node) {
		Struct type = node.getType().struct;
		if (node.getArraySuffix() instanceof ArraySuffixPresent) {
			type = new Struct(Struct.Array, type);
		}

		Obj parameter = insertUnique(Obj.Var, node.getName(), type, node);
		node.obj = parameter;
		if (currentMethod != null) {
			methodParameterTypes.get(currentMethod).add(type);
			if (parameter != Tab.noObj) {
				parameter.setFpPos(currentFormalParameterCount);
				formalParameters.add(parameter);
			}
			currentFormalParameterCount++;
		}
	}

	@Override
	public void visit(ConcreteClassHeaderWithoutExtends node) {
		openClass(node.getName(), Tab.noType, false, node);
		node.obj = currentClass;
	}

	@Override
	public void visit(ConcreteClassHeaderWithExtends node) {
		openClass(node.getName(), parentType(node), false, node);
		node.obj = currentClass;
	}

	@Override
	public void visit(ConcreteClassHeaderRecoveredExtends node) {
		openClass(node.getName(), Tab.noType, false, node);
		node.obj = currentClass;
	}

	@Override
	public void visit(AbstractClassHeaderWithoutExtends node) {
		openClass(node.getName(), Tab.noType, true, node);
		node.obj = currentClass;
	}

	@Override
	public void visit(AbstractClassHeaderWithExtends node) {
		openClass(node.getName(), parentType(node), true, node);
		node.obj = currentClass;
	}

	@Override
	public void visit(AbstractClassHeaderRecoveredExtends node) {
		openClass(node.getName(), Tab.noType, true, node);
		node.obj = currentClass;
	}

	@Override
	public void visit(CompleteClassDeclaration node) {
		node.obj = node.getConcreteClassHeader().obj;
		finishClass(node.obj, node);
	}

	@Override
	public void visit(ClassDeclarationRecoveredTypedField node) {
		node.obj = node.getConcreteClassHeader().obj;
		finishClass(node.obj, node);
	}

	@Override
	public void visit(CompleteAbstractClassDeclaration node) {
		node.obj = node.getAbstractClassHeader().obj;
		finishClass(node.obj, node);
	}

	@Override
	public void visit(AbstractClassDeclarationRecoveredTypedField node) {
		node.obj = node.getAbstractClassHeader().obj;
		finishClass(node.obj, node);
	}

	@Override
	public void visit(MethodName node) {
		openMethod(node);
	}

	@Override
	public void visit(ValidMethodHeader node) {
		node.obj = node.getMethodName().obj;
	}

	@Override
	public void visit(MethodHeaderRecoveredFormalParameter node) {
		node.obj = node.getMethodName().obj;
	}

	@Override
	public void visit(MethodDeclaration node) {
		node.obj = node.getMethodHeader().obj;
		finishMethod(node.getMethodHeader(), false, node);
		node.obj = node.getMethodHeader().obj;
	}

	@Override
	public void visit(AbstractMethodDeclaration node) {
		node.obj = node.getMethodHeader().obj;
		finishMethod(node.getMethodHeader(), true, node);
		node.obj = node.getMethodHeader().obj;
	}

	@Override
	public void visit(DesignatorName node) {
		node.obj = resolveName(node.getName(), node);
	}

	@Override
	public void visit(DesignatorMember node) {
		Obj owner = node.getDesignator().obj;
		if (!isValue(owner)) {
			if (owner != null && owner != Tab.noObj) {
				report_error("Operator . zahteva objekat kao levi operand", node);
			}
			node.obj = Tab.noObj;
			return;
		}
		if (owner.getType().getKind() != Struct.Class) {
			report_error(owner.getName() + " nije objekat klase", node);
			node.obj = Tab.noObj;
			return;
		}

		node.obj = findMember(owner.getType(), node.getMember());
		if (node.obj == Tab.noObj) {
			report_error(
				"Klasa nema polje ili metodu " + node.getMember(),
				node
			);
		} else {
			reportSymbol(node.getMember(), node.obj, node);
		}
	}

	@Override
	public void visit(DesignatorLength node) {
		Obj array = node.getDesignator().obj;
		if (!isValue(array)
			|| array.getType().getKind() != Struct.Array) {
			report_error("Polje length se moze koristiti samo nad nizom", node);
			node.obj = Tab.noObj;
			return;
		}
		node.obj = new Obj(Obj.Con, "length", Tab.intType);
		reportSymbol("length", node.obj, node);
	}

	@Override
	public void visit(DesignatorElement node) {
		Obj array = node.getDesignator().obj;
		if (!isValue(array)
			|| array.getType().getKind() != Struct.Array) {
			report_error("Indeksiranje se moze primeniti samo na niz", node);
			node.obj = Tab.noObj;
			return;
		}
		if (node.getExpression().struct != Tab.intType) {
			report_error("Indeks niza mora biti tipa int", node);
		}
		node.obj = new Obj(Obj.Elem, array.getName() + "[]", array.getType().getElemType());
		reportSymbol(node.obj.getName(), node.obj, node);
	}

	@Override
	public void visit(DesignatorAssignment node) {
		Obj destination = node.getDesignator().obj;
		if (!isWritable(destination)) {
			report_error("Leva strana dodele mora biti promenljiva, polje ili element niza", node);
			return;
		}
		if (!assignable(node.getExpression().struct, destination.getType())) {
			report_error("Tipovi leve i desne strane dodele nisu kompatibilni", node);
		}
	}

	@Override
	public void visit(DesignatorIncrement node) {
		checkIncrementTarget(node.getDesignator(), node);
	}

	@Override
	public void visit(DesignatorDecrement node) {
		checkIncrementTarget(node.getDesignator(), node);
	}

	private void checkIncrementTarget(Designator designator, SyntaxNode info) {
		if (!isWritable(designator.obj) || designator.obj.getType() != Tab.intType) {
			report_error("Operator ++/-- zahteva promenljivu tipa int", info);
		}
	}

	@Override
	public void visit(NoActualParameters node) {
		actualParameterTypes.put(node, Collections.<Struct>emptyList());
	}

	@Override
	public void visit(ActualParametersPresent node) {
		actualParameterTypes.put(
			node,
			new ArrayList<Struct>(actualParameters(node.getActualParameters()))
		);
	}

	@Override
	public void visit(ActualParametersSingle node) {
		List<Struct> types = new ArrayList<Struct>();
		types.add(node.getExpression().struct);
		actualParameterTypes.put(node, types);
	}

	@Override
	public void visit(ActualParametersMultiple node) {
		List<Struct> types =
			new ArrayList<Struct>(actualParameters(node.getActualParameters()));
		types.add(node.getExpression().struct);
		actualParameterTypes.put(node, types);
	}

	@Override
	public void visit(FactorCall node) {
		actualParameterTypes.put(
			node,
			new ArrayList<Struct>(actualParameters(node.getActualParametersOpt()))
		);
	}

	@Override
	public void visit(DesignatorCall node) {
		checkCall(
			node.getDesignator().obj,
			actualParameters(node.getActualParametersOpt()),
			node
		);
	}

	@Override
	public void visit(FindAnyStatement node) {
		Designator result = node.getDesignator();
		Designator array = node.getDesignator1();

		if (!isWritable(result.obj) || !isBoolean(result.obj.getType())) {
			report_error("Rezultat findAny mora biti promenljiva tipa bool", node);
		}
		if (!isValue(array.obj)
			|| array.obj.getType().getKind() != Struct.Array) {
			report_error("findAny se moze primeniti samo na jednodimenzionalni niz", node);
			return;
		}

		Struct elementType = array.obj.getType().getElemType();
		if (!isPrimitive(elementType)) {
			report_error("findAny podrzava samo nizove ugradjenih tipova", node);
		}
		if (!equivalent(node.getExpression().struct, elementType)) {
			report_error("Argument findAny mora biti tipa elementa niza", node);
		}
	}

	@Override
	public void visit(MapStatement node) {
		Designator result = node.getDesignator();
		Designator array = node.getDesignator1();

		if (!isWritable(result.obj)
			|| result.obj == Tab.noObj
			|| result.obj.getType().getKind() != Struct.Array) {
			report_error("Rezultat map mora biti prethodno deklarisana promenljiva niza", node);
		}
		if (!isValue(array.obj)
			|| array.obj.getType().getKind() != Struct.Array) {
			report_error("map se moze primeniti samo na jednodimenzionalni niz", node);
			return;
		}

		Struct sourceElementType = array.obj.getType().getElemType();
		Obj mapper = resolveName(node.getMapper(), node);
		if (mapper == Tab.noObj
			|| mapper.getKind() != Obj.Var
			|| formalParameters.contains(mapper)) {
			report_error(
				"Identifikator funkcije map mora biti lokalna ili globalna promenljiva",
				node
			);
		} else if (!equivalent(mapper.getType(), sourceElementType)) {
			report_error("Promenljiva funkcije map mora biti tipa elementa izvornog niza", node);
		}

		if (result.obj != null
			&& result.obj != Tab.noObj
			&& result.obj.getType().getKind() == Struct.Array
			&& !assignable(
				node.getExpression().struct,
				result.obj.getType().getElemType()
			)) {
			report_error("Izraz funkcije map ne odgovara tipu rezultujuceg niza", node);
		}
	}

	@Override
	public void visit(FactorConstant node) {
		node.struct = node.getConstValue().struct;
	}

	@Override
	public void visit(FactorDesignator node) {
		Obj symbol = node.getDesignator().obj;
		if (symbol == null || symbol == Tab.noObj) {
			node.struct = Tab.noType;
			return;
		}

		if (node.getFactorCallOpt() instanceof FactorCall) {
			FactorCall call = (FactorCall) node.getFactorCallOpt();
			checkCall(symbol, actualParameters(call), node);
			node.struct = symbol.getKind() == Obj.Meth ? symbol.getType() : Tab.noType;
			return;
		}

		int kind = symbol.getKind();
		if (kind != Obj.Con && kind != Obj.Var && kind != Obj.Fld && kind != Obj.Elem) {
			report_error(symbol.getName() + " ne predstavlja vrednost", node);
			node.struct = Tab.noType;
		} else {
			node.struct = symbol.getType();
		}
	}

	@Override
	public void visit(FactorNewObject node) {
		Struct type = node.getType().struct;
		if (type == null || type.getKind() != Struct.Class) {
			report_error("Operator new bez [] zahteva tip klase", node);
			node.struct = Tab.noType;
		} else if (abstractClasses.contains(type)) {
			report_error("Apstraktna klasa se ne moze instancirati", node);
			node.struct = type;
		} else {
			node.struct = type;
			report_info("Kreiranje objekta klase " + node.getType().getName(), node);
		}
	}

	@Override
	public void visit(FactorNewArray node) {
		if (node.getExpression().struct != Tab.intType) {
			report_error("Velicina niza mora biti tipa int", node);
		}
		node.struct = new Struct(Struct.Array, node.getType().struct);
	}

	@Override
	public void visit(FactorParenthesized node) {
		node.struct = node.getExpression().struct;
	}

	@Override
	public void visit(TermFactor node) {
		node.struct = node.getFactor().struct;
	}

	@Override
	public void visit(TermWithMultiplication node) {
		if (node.getTerm().struct != Tab.intType
			|| node.getFactor().struct != Tab.intType) {
			report_error("Operatori *, / i % zahtevaju operande tipa int", node);
			node.struct = Tab.noType;
		} else {
			node.struct = Tab.intType;
		}
	}

	@Override
	public void visit(NoAdditiveTerms node) {
		node.struct = Tab.noType;
	}

	@Override
	public void visit(AdditiveTermsMultiple node) {
		boolean previousValid = node.getAdditiveTail() instanceof NoAdditiveTerms
			|| node.getAdditiveTail().struct == Tab.intType;
		if (!previousValid || node.getTerm().struct != Tab.intType) {
			report_error("Operatori + i - zahtevaju operande tipa int", node);
			node.struct = Tab.noType;
		} else {
			node.struct = Tab.intType;
		}
	}

	@Override
	public void visit(PositiveNonTernaryExpression node) {
		if (node.getAdditiveTail() instanceof NoAdditiveTerms) {
			node.struct = node.getTerm().struct;
		} else if (node.getTerm().struct == Tab.intType
			&& node.getAdditiveTail().struct == Tab.intType) {
			node.struct = Tab.intType;
		} else {
			node.struct = Tab.noType;
		}
	}

	@Override
	public void visit(NegativeNonTernaryExpression node) {
		if (node.getTerm().struct != Tab.intType
			|| (!(node.getAdditiveTail() instanceof NoAdditiveTerms)
				&& node.getAdditiveTail().struct != Tab.intType)) {
			report_error("Unarni minus i aritmeticki izraz zahtevaju tip int", node);
			node.struct = Tab.noType;
		} else {
			node.struct = Tab.intType;
		}
	}

	@Override
	public void visit(ExpressionWithoutTernary node) {
		node.struct = node.getNonTernaryExpression().struct;
	}

	@Override
	public void visit(ExpressionWithTernary node) {
		node.struct = node.getTernaryExpression().struct;
	}

	@Override
	public void visit(TernaryExpression node) {
		if (!isBoolean(node.getCondition().struct)) {
			report_error("Uslov ternarnog operatora mora biti tipa bool", node);
		}
		if (!equivalent(
			node.getExpression().struct,
			node.getExpression1().struct
		)) {
			report_error("Drugi i treci operand ternarnog operatora moraju biti istog tipa", node);
			node.struct = Tab.noType;
		} else {
			node.struct = node.getExpression().struct;
		}
	}

	@Override
	public void visit(ConditionFactorExpression node) {
		node.struct = node.getNonTernaryExpression().struct;
	}

	@Override
	public void visit(ConditionFactorRelational node) {
		Struct left = node.getNonTernaryExpression().struct;
		Struct right = node.getNonTernaryExpression1().struct;
		if (!compatible(left, right)) {
			report_error("Relacioni operator zahteva kompatibilne operande", node);
			node.struct = Tab.noType;
			return;
		}
		if ((isReference(left)
				|| isReference(right)
				|| left == Tab.nullType
				|| right == Tab.nullType)
			&& !(node.getRelationalOperator() instanceof RelationalEqual)
			&& !(node.getRelationalOperator() instanceof RelationalNotEqual)) {
			report_error("Reference se mogu porediti samo operatorima == i !=", node);
			node.struct = Tab.noType;
			return;
		}
		node.struct = boolType;
	}

	@Override
	public void visit(ConditionTermSingleFactor node) {
		node.struct = node.getConditionFactor().struct;
	}

	@Override
	public void visit(ConditionTermWithAnd node) {
		if (!isBoolean(node.getConditionTerm().struct)
			|| !isBoolean(node.getConditionFactor().struct)) {
			report_error("Operator && zahteva bool operande", node);
			node.struct = Tab.noType;
		} else {
			node.struct = boolType;
		}
	}

	@Override
	public void visit(ConditionSingleTerm node) {
		node.struct = node.getConditionTerm().struct;
	}

	@Override
	public void visit(ConditionWithOr node) {
		if (!isBoolean(node.getCondition().struct)
			|| !isBoolean(node.getConditionTerm().struct)) {
			report_error("Operator || zahteva bool operande", node);
			node.struct = Tab.noType;
		} else {
			node.struct = boolType;
		}
	}

	@Override
	public void visit(ValidIfCondition node) {
		if (!isBoolean(node.getCondition().struct)) {
			report_error("Uslov if iskaza mora biti tipa bool", node);
		}
	}

	@Override
	public void visit(ForHeader node) {
		if (node.getConditionOpt() instanceof ConditionPresent) {
			Condition condition = ((ConditionPresent) node.getConditionOpt()).getCondition();
			if (!isBoolean(condition.struct)) {
				report_error("Uslov for petlje mora biti tipa bool", node);
			}
		}
		loopDepth++;
	}

	@Override
	public void visit(MatchedForStatement node) {
		loopDepth--;
	}

	@Override
	public void visit(UnmatchedForStatement node) {
		loopDepth--;
	}

	@Override
	public void visit(ForeachHeader node) {
		Obj target = node.getDesignator().obj;
		Obj array = node.getDesignator1().obj;

		if (!isWritable(target)) {
			report_error(
				"Leva strana foreach petlje mora biti promenljiva, polje ili element niza",
				node
			);
		}
		if (!isValue(array) || array.getType().getKind() != Struct.Array) {
			report_error("Desna strana foreach petlje mora biti niz", node);
		} else if (isWritable(target)
			&& !assignable(array.getType().getElemType(), target.getType())) {
			report_error(
				"Tip elementa niza nije kompatibilan sa foreach promenljivom",
				node
			);
		}
		loopDepth++;
	}

	@Override
	public void visit(MatchedForeachStatement node) {
		loopDepth--;
	}

	@Override
	public void visit(UnmatchedForeachStatement node) {
		loopDepth--;
	}

	@Override
	public void visit(MatchedBreakStatement node) {
		if (loopDepth <= 0) {
			report_error("break se moze koristiti samo unutar petlje", node);
		}
	}

	@Override
	public void visit(MatchedContinueStatement node) {
		if (loopDepth <= 0) {
			report_error("continue se moze koristiti samo unutar petlje", node);
		}
	}

	@Override
	public void visit(MatchedReadStatement node) {
		Obj target = node.getDesignator().obj;
		if (!isWritable(target)
			|| (!isPrimitive(target.getType()))) {
			report_error("read zahteva promenljivu tipa int, char ili bool", node);
		}
	}

	@Override
	public void visit(MatchedPrintStatement node) {
		if (!isPrimitive(node.getExpression().struct)) {
			report_error("print zahteva izraz tipa int, char ili bool", node);
		}
	}

	@Override
	public void visit(MatchedReturnStatement node) {
		if (currentMethod == null) {
			report_error("return se moze koristiti samo unutar metode", node);
			return;
		}

		ReturnExpressionOpt optional = node.getReturnExpressionOpt();
		if (optional instanceof NoReturnExpression) {
			if (currentMethod.getType() != Tab.noType) {
				report_error("Metoda koja vraca vrednost zahteva return izraz", node);
			}
		} else {
			Struct returned =
				((ReturnExpressionPresent) optional).getExpression().struct;
			if (!assignable(returned, currentMethod.getType())) {
				report_error("Tip return izraza ne odgovara povratnom tipu metode", node);
			}
		}
	}
}
