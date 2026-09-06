// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class FormalParametersPrefixRecovered extends FormalParametersPrefix {

    private FormalParameterCommaRecovery FormalParameterCommaRecovery;

    public FormalParametersPrefixRecovered (FormalParameterCommaRecovery FormalParameterCommaRecovery) {
        this.FormalParameterCommaRecovery=FormalParameterCommaRecovery;
        if(FormalParameterCommaRecovery!=null) FormalParameterCommaRecovery.setParent(this);
    }

    public FormalParameterCommaRecovery getFormalParameterCommaRecovery() {
        return FormalParameterCommaRecovery;
    }

    public void setFormalParameterCommaRecovery(FormalParameterCommaRecovery FormalParameterCommaRecovery) {
        this.FormalParameterCommaRecovery=FormalParameterCommaRecovery;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormalParameterCommaRecovery!=null) FormalParameterCommaRecovery.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalParameterCommaRecovery!=null) FormalParameterCommaRecovery.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalParameterCommaRecovery!=null) FormalParameterCommaRecovery.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParametersPrefixRecovered(\n");

        if(FormalParameterCommaRecovery!=null)
            buffer.append(FormalParameterCommaRecovery.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParametersPrefixRecovered]");
        return buffer.toString();
    }
}
