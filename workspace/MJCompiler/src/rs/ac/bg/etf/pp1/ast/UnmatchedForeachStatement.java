// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class UnmatchedForeachStatement extends UnmatchedStatement {

    private ForeachHeader ForeachHeader;
    private UnmatchedStatement UnmatchedStatement;

    public UnmatchedForeachStatement (ForeachHeader ForeachHeader, UnmatchedStatement UnmatchedStatement) {
        this.ForeachHeader=ForeachHeader;
        if(ForeachHeader!=null) ForeachHeader.setParent(this);
        this.UnmatchedStatement=UnmatchedStatement;
        if(UnmatchedStatement!=null) UnmatchedStatement.setParent(this);
    }

    public ForeachHeader getForeachHeader() {
        return ForeachHeader;
    }

    public void setForeachHeader(ForeachHeader ForeachHeader) {
        this.ForeachHeader=ForeachHeader;
    }

    public UnmatchedStatement getUnmatchedStatement() {
        return UnmatchedStatement;
    }

    public void setUnmatchedStatement(UnmatchedStatement UnmatchedStatement) {
        this.UnmatchedStatement=UnmatchedStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForeachHeader!=null) ForeachHeader.accept(visitor);
        if(UnmatchedStatement!=null) UnmatchedStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForeachHeader!=null) ForeachHeader.traverseTopDown(visitor);
        if(UnmatchedStatement!=null) UnmatchedStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForeachHeader!=null) ForeachHeader.traverseBottomUp(visitor);
        if(UnmatchedStatement!=null) UnmatchedStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("UnmatchedForeachStatement(\n");

        if(ForeachHeader!=null)
            buffer.append(ForeachHeader.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(UnmatchedStatement!=null)
            buffer.append(UnmatchedStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [UnmatchedForeachStatement]");
        return buffer.toString();
    }
}
