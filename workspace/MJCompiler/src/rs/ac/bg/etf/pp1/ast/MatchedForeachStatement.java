// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class MatchedForeachStatement extends MatchedStatement {

    private ForeachHeader ForeachHeader;
    private MatchedStatement MatchedStatement;

    public MatchedForeachStatement (ForeachHeader ForeachHeader, MatchedStatement MatchedStatement) {
        this.ForeachHeader=ForeachHeader;
        if(ForeachHeader!=null) ForeachHeader.setParent(this);
        this.MatchedStatement=MatchedStatement;
        if(MatchedStatement!=null) MatchedStatement.setParent(this);
    }

    public ForeachHeader getForeachHeader() {
        return ForeachHeader;
    }

    public void setForeachHeader(ForeachHeader ForeachHeader) {
        this.ForeachHeader=ForeachHeader;
    }

    public MatchedStatement getMatchedStatement() {
        return MatchedStatement;
    }

    public void setMatchedStatement(MatchedStatement MatchedStatement) {
        this.MatchedStatement=MatchedStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForeachHeader!=null) ForeachHeader.accept(visitor);
        if(MatchedStatement!=null) MatchedStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForeachHeader!=null) ForeachHeader.traverseTopDown(visitor);
        if(MatchedStatement!=null) MatchedStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForeachHeader!=null) ForeachHeader.traverseBottomUp(visitor);
        if(MatchedStatement!=null) MatchedStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedForeachStatement(\n");

        if(ForeachHeader!=null)
            buffer.append(ForeachHeader.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MatchedStatement!=null)
            buffer.append(MatchedStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedForeachStatement]");
        return buffer.toString();
    }
}
