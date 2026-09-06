// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class AdditiveTermsMultiple extends AdditiveTail {

    private AdditiveTail AdditiveTail;
    private AdditiveOperator AdditiveOperator;
    private Term Term;

    public AdditiveTermsMultiple (AdditiveTail AdditiveTail, AdditiveOperator AdditiveOperator, Term Term) {
        this.AdditiveTail=AdditiveTail;
        if(AdditiveTail!=null) AdditiveTail.setParent(this);
        this.AdditiveOperator=AdditiveOperator;
        if(AdditiveOperator!=null) AdditiveOperator.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
    }

    public AdditiveTail getAdditiveTail() {
        return AdditiveTail;
    }

    public void setAdditiveTail(AdditiveTail AdditiveTail) {
        this.AdditiveTail=AdditiveTail;
    }

    public AdditiveOperator getAdditiveOperator() {
        return AdditiveOperator;
    }

    public void setAdditiveOperator(AdditiveOperator AdditiveOperator) {
        this.AdditiveOperator=AdditiveOperator;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AdditiveTail!=null) AdditiveTail.accept(visitor);
        if(AdditiveOperator!=null) AdditiveOperator.accept(visitor);
        if(Term!=null) Term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AdditiveTail!=null) AdditiveTail.traverseTopDown(visitor);
        if(AdditiveOperator!=null) AdditiveOperator.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AdditiveTail!=null) AdditiveTail.traverseBottomUp(visitor);
        if(AdditiveOperator!=null) AdditiveOperator.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AdditiveTermsMultiple(\n");

        if(AdditiveTail!=null)
            buffer.append(AdditiveTail.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AdditiveOperator!=null)
            buffer.append(AdditiveOperator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AdditiveTermsMultiple]");
        return buffer.toString();
    }
}
