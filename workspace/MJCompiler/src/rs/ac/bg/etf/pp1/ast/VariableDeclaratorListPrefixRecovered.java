// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class VariableDeclaratorListPrefixRecovered extends VariableDeclaratorListPrefix {

    private VariableDeclaratorCommaRecovery VariableDeclaratorCommaRecovery;

    public VariableDeclaratorListPrefixRecovered (VariableDeclaratorCommaRecovery VariableDeclaratorCommaRecovery) {
        this.VariableDeclaratorCommaRecovery=VariableDeclaratorCommaRecovery;
        if(VariableDeclaratorCommaRecovery!=null) VariableDeclaratorCommaRecovery.setParent(this);
    }

    public VariableDeclaratorCommaRecovery getVariableDeclaratorCommaRecovery() {
        return VariableDeclaratorCommaRecovery;
    }

    public void setVariableDeclaratorCommaRecovery(VariableDeclaratorCommaRecovery VariableDeclaratorCommaRecovery) {
        this.VariableDeclaratorCommaRecovery=VariableDeclaratorCommaRecovery;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VariableDeclaratorCommaRecovery!=null) VariableDeclaratorCommaRecovery.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VariableDeclaratorCommaRecovery!=null) VariableDeclaratorCommaRecovery.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VariableDeclaratorCommaRecovery!=null) VariableDeclaratorCommaRecovery.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDeclaratorListPrefixRecovered(\n");

        if(VariableDeclaratorCommaRecovery!=null)
            buffer.append(VariableDeclaratorCommaRecovery.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDeclaratorListPrefixRecovered]");
        return buffer.toString();
    }
}
