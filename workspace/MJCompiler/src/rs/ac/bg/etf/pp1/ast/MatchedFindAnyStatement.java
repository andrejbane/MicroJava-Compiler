// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class MatchedFindAnyStatement extends MatchedStatement {

    private FindAnyStatement FindAnyStatement;

    public MatchedFindAnyStatement (FindAnyStatement FindAnyStatement) {
        this.FindAnyStatement=FindAnyStatement;
        if(FindAnyStatement!=null) FindAnyStatement.setParent(this);
    }

    public FindAnyStatement getFindAnyStatement() {
        return FindAnyStatement;
    }

    public void setFindAnyStatement(FindAnyStatement FindAnyStatement) {
        this.FindAnyStatement=FindAnyStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FindAnyStatement!=null) FindAnyStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FindAnyStatement!=null) FindAnyStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FindAnyStatement!=null) FindAnyStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedFindAnyStatement(\n");

        if(FindAnyStatement!=null)
            buffer.append(FindAnyStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedFindAnyStatement]");
        return buffer.toString();
    }
}
