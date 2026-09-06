// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class DesignatorCall extends DesignatorStatement {

    private Designator Designator;
    private ActualParametersOpt ActualParametersOpt;

    public DesignatorCall (Designator Designator, ActualParametersOpt ActualParametersOpt) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ActualParametersOpt=ActualParametersOpt;
        if(ActualParametersOpt!=null) ActualParametersOpt.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ActualParametersOpt getActualParametersOpt() {
        return ActualParametersOpt;
    }

    public void setActualParametersOpt(ActualParametersOpt ActualParametersOpt) {
        this.ActualParametersOpt=ActualParametersOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ActualParametersOpt!=null) ActualParametersOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ActualParametersOpt!=null) ActualParametersOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ActualParametersOpt!=null) ActualParametersOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorCall(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActualParametersOpt!=null)
            buffer.append(ActualParametersOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorCall]");
        return buffer.toString();
    }
}
