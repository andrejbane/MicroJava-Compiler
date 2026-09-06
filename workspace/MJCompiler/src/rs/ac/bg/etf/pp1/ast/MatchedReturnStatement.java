// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class MatchedReturnStatement extends MatchedStatement {

    private ReturnExpressionOpt ReturnExpressionOpt;

    public MatchedReturnStatement (ReturnExpressionOpt ReturnExpressionOpt) {
        this.ReturnExpressionOpt=ReturnExpressionOpt;
        if(ReturnExpressionOpt!=null) ReturnExpressionOpt.setParent(this);
    }

    public ReturnExpressionOpt getReturnExpressionOpt() {
        return ReturnExpressionOpt;
    }

    public void setReturnExpressionOpt(ReturnExpressionOpt ReturnExpressionOpt) {
        this.ReturnExpressionOpt=ReturnExpressionOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ReturnExpressionOpt!=null) ReturnExpressionOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReturnExpressionOpt!=null) ReturnExpressionOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReturnExpressionOpt!=null) ReturnExpressionOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedReturnStatement(\n");

        if(ReturnExpressionOpt!=null)
            buffer.append(ReturnExpressionOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedReturnStatement]");
        return buffer.toString();
    }
}
