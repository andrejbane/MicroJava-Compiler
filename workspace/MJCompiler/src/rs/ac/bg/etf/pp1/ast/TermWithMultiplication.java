// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class TermWithMultiplication extends Term {

    private Term Term;
    private MultiplicativeOperator MultiplicativeOperator;
    private Factor Factor;

    public TermWithMultiplication (Term Term, MultiplicativeOperator MultiplicativeOperator, Factor Factor) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.MultiplicativeOperator=MultiplicativeOperator;
        if(MultiplicativeOperator!=null) MultiplicativeOperator.setParent(this);
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public MultiplicativeOperator getMultiplicativeOperator() {
        return MultiplicativeOperator;
    }

    public void setMultiplicativeOperator(MultiplicativeOperator MultiplicativeOperator) {
        this.MultiplicativeOperator=MultiplicativeOperator;
    }

    public Factor getFactor() {
        return Factor;
    }

    public void setFactor(Factor Factor) {
        this.Factor=Factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Term!=null) Term.accept(visitor);
        if(MultiplicativeOperator!=null) MultiplicativeOperator.accept(visitor);
        if(Factor!=null) Factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(MultiplicativeOperator!=null) MultiplicativeOperator.traverseTopDown(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(MultiplicativeOperator!=null) MultiplicativeOperator.traverseBottomUp(visitor);
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TermWithMultiplication(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultiplicativeOperator!=null)
            buffer.append(MultiplicativeOperator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TermWithMultiplication]");
        return buffer.toString();
    }
}
