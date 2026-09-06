package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FormalParameter;
import rs.ac.bg.etf.pp1.ast.VariableDeclarator;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public abstract class CounterVisitor extends VisitorAdaptor {

	protected int count;

	public int getCount() {
		return count;
	}

	public static final class FormParamCounter extends CounterVisitor {

		@Override
		public void visit(FormalParameter node) {
			count++;
		}
	}

	public static final class VarCounter extends CounterVisitor {

		@Override
		public void visit(VariableDeclarator node) {
			count++;
		}
	}
}
