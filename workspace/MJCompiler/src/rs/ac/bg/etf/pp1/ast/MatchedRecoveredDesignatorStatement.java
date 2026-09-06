// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class MatchedRecoveredDesignatorStatement extends MatchedStatement {

    private Designator Designator;
    private DesignatorStatementRecovery DesignatorStatementRecovery;

    public MatchedRecoveredDesignatorStatement (Designator Designator, DesignatorStatementRecovery DesignatorStatementRecovery) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorStatementRecovery=DesignatorStatementRecovery;
        if(DesignatorStatementRecovery!=null) DesignatorStatementRecovery.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorStatementRecovery getDesignatorStatementRecovery() {
        return DesignatorStatementRecovery;
    }

    public void setDesignatorStatementRecovery(DesignatorStatementRecovery DesignatorStatementRecovery) {
        this.DesignatorStatementRecovery=DesignatorStatementRecovery;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorStatementRecovery!=null) DesignatorStatementRecovery.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorStatementRecovery!=null) DesignatorStatementRecovery.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorStatementRecovery!=null) DesignatorStatementRecovery.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedRecoveredDesignatorStatement(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementRecovery!=null)
            buffer.append(DesignatorStatementRecovery.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedRecoveredDesignatorStatement]");
        return buffer.toString();
    }
}
