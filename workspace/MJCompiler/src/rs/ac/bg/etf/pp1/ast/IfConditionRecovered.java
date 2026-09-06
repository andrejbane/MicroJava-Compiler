// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class IfConditionRecovered extends IfCondition {

    private IfConditionEndRecovery IfConditionEndRecovery;

    public IfConditionRecovered (IfConditionEndRecovery IfConditionEndRecovery) {
        this.IfConditionEndRecovery=IfConditionEndRecovery;
        if(IfConditionEndRecovery!=null) IfConditionEndRecovery.setParent(this);
    }

    public IfConditionEndRecovery getIfConditionEndRecovery() {
        return IfConditionEndRecovery;
    }

    public void setIfConditionEndRecovery(IfConditionEndRecovery IfConditionEndRecovery) {
        this.IfConditionEndRecovery=IfConditionEndRecovery;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfConditionEndRecovery!=null) IfConditionEndRecovery.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfConditionEndRecovery!=null) IfConditionEndRecovery.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfConditionEndRecovery!=null) IfConditionEndRecovery.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfConditionRecovered(\n");

        if(IfConditionEndRecovery!=null)
            buffer.append(IfConditionEndRecovery.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfConditionRecovered]");
        return buffer.toString();
    }
}
