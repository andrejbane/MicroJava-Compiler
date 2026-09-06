// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class MatchedPrintStatement extends MatchedStatement {

    private Expression Expression;
    private PrintWidthOpt PrintWidthOpt;

    public MatchedPrintStatement (Expression Expression, PrintWidthOpt PrintWidthOpt) {
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
        this.PrintWidthOpt=PrintWidthOpt;
        if(PrintWidthOpt!=null) PrintWidthOpt.setParent(this);
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public PrintWidthOpt getPrintWidthOpt() {
        return PrintWidthOpt;
    }

    public void setPrintWidthOpt(PrintWidthOpt PrintWidthOpt) {
        this.PrintWidthOpt=PrintWidthOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expression!=null) Expression.accept(visitor);
        if(PrintWidthOpt!=null) PrintWidthOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
        if(PrintWidthOpt!=null) PrintWidthOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        if(PrintWidthOpt!=null) PrintWidthOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedPrintStatement(\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(PrintWidthOpt!=null)
            buffer.append(PrintWidthOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedPrintStatement]");
        return buffer.toString();
    }
}
