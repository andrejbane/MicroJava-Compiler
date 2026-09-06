// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class ExpressionWithoutTernary extends Expression {

    private NonTernaryExpression NonTernaryExpression;

    public ExpressionWithoutTernary (NonTernaryExpression NonTernaryExpression) {
        this.NonTernaryExpression=NonTernaryExpression;
        if(NonTernaryExpression!=null) NonTernaryExpression.setParent(this);
    }

    public NonTernaryExpression getNonTernaryExpression() {
        return NonTernaryExpression;
    }

    public void setNonTernaryExpression(NonTernaryExpression NonTernaryExpression) {
        this.NonTernaryExpression=NonTernaryExpression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NonTernaryExpression!=null) NonTernaryExpression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NonTernaryExpression!=null) NonTernaryExpression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NonTernaryExpression!=null) NonTernaryExpression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExpressionWithoutTernary(\n");

        if(NonTernaryExpression!=null)
            buffer.append(NonTernaryExpression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExpressionWithoutTernary]");
        return buffer.toString();
    }
}
