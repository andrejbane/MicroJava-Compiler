// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class ActualParametersMultiple extends ActualParameters {

    private ActualParameters ActualParameters;
    private Expression Expression;

    public ActualParametersMultiple (ActualParameters ActualParameters, Expression Expression) {
        this.ActualParameters=ActualParameters;
        if(ActualParameters!=null) ActualParameters.setParent(this);
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
    }

    public ActualParameters getActualParameters() {
        return ActualParameters;
    }

    public void setActualParameters(ActualParameters ActualParameters) {
        this.ActualParameters=ActualParameters;
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActualParameters!=null) ActualParameters.accept(visitor);
        if(Expression!=null) Expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActualParameters!=null) ActualParameters.traverseTopDown(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActualParameters!=null) ActualParameters.traverseBottomUp(visitor);
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualParametersMultiple(\n");

        if(ActualParameters!=null)
            buffer.append(ActualParameters.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualParametersMultiple]");
        return buffer.toString();
    }
}
