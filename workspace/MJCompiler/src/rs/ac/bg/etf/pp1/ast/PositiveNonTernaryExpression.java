// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class PositiveNonTernaryExpression extends NonTernaryExpression {

    private Term Term;
    private AdditiveTail AdditiveTail;

    public PositiveNonTernaryExpression (Term Term, AdditiveTail AdditiveTail) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.AdditiveTail=AdditiveTail;
        if(AdditiveTail!=null) AdditiveTail.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public AdditiveTail getAdditiveTail() {
        return AdditiveTail;
    }

    public void setAdditiveTail(AdditiveTail AdditiveTail) {
        this.AdditiveTail=AdditiveTail;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Term!=null) Term.accept(visitor);
        if(AdditiveTail!=null) AdditiveTail.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(AdditiveTail!=null) AdditiveTail.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(AdditiveTail!=null) AdditiveTail.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PositiveNonTernaryExpression(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AdditiveTail!=null)
            buffer.append(AdditiveTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PositiveNonTernaryExpression]");
        return buffer.toString();
    }
}
