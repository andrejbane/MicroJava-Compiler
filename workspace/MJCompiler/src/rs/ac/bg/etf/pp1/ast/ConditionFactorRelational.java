// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class ConditionFactorRelational extends ConditionFactor {

    private NonTernaryExpression NonTernaryExpression;
    private RelationalOperator RelationalOperator;
    private NonTernaryExpression NonTernaryExpression1;

    public ConditionFactorRelational (NonTernaryExpression NonTernaryExpression, RelationalOperator RelationalOperator, NonTernaryExpression NonTernaryExpression1) {
        this.NonTernaryExpression=NonTernaryExpression;
        if(NonTernaryExpression!=null) NonTernaryExpression.setParent(this);
        this.RelationalOperator=RelationalOperator;
        if(RelationalOperator!=null) RelationalOperator.setParent(this);
        this.NonTernaryExpression1=NonTernaryExpression1;
        if(NonTernaryExpression1!=null) NonTernaryExpression1.setParent(this);
    }

    public NonTernaryExpression getNonTernaryExpression() {
        return NonTernaryExpression;
    }

    public void setNonTernaryExpression(NonTernaryExpression NonTernaryExpression) {
        this.NonTernaryExpression=NonTernaryExpression;
    }

    public RelationalOperator getRelationalOperator() {
        return RelationalOperator;
    }

    public void setRelationalOperator(RelationalOperator RelationalOperator) {
        this.RelationalOperator=RelationalOperator;
    }

    public NonTernaryExpression getNonTernaryExpression1() {
        return NonTernaryExpression1;
    }

    public void setNonTernaryExpression1(NonTernaryExpression NonTernaryExpression1) {
        this.NonTernaryExpression1=NonTernaryExpression1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NonTernaryExpression!=null) NonTernaryExpression.accept(visitor);
        if(RelationalOperator!=null) RelationalOperator.accept(visitor);
        if(NonTernaryExpression1!=null) NonTernaryExpression1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NonTernaryExpression!=null) NonTernaryExpression.traverseTopDown(visitor);
        if(RelationalOperator!=null) RelationalOperator.traverseTopDown(visitor);
        if(NonTernaryExpression1!=null) NonTernaryExpression1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NonTernaryExpression!=null) NonTernaryExpression.traverseBottomUp(visitor);
        if(RelationalOperator!=null) RelationalOperator.traverseBottomUp(visitor);
        if(NonTernaryExpression1!=null) NonTernaryExpression1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionFactorRelational(\n");

        if(NonTernaryExpression!=null)
            buffer.append(NonTernaryExpression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RelationalOperator!=null)
            buffer.append(RelationalOperator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NonTernaryExpression1!=null)
            buffer.append(NonTernaryExpression1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionFactorRelational]");
        return buffer.toString();
    }
}
