// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class VariableDeclaratorsMultiple extends VariableDeclaratorList {

    private VariableDeclaratorListPrefix VariableDeclaratorListPrefix;
    private VariableDeclarator VariableDeclarator;

    public VariableDeclaratorsMultiple (VariableDeclaratorListPrefix VariableDeclaratorListPrefix, VariableDeclarator VariableDeclarator) {
        this.VariableDeclaratorListPrefix=VariableDeclaratorListPrefix;
        if(VariableDeclaratorListPrefix!=null) VariableDeclaratorListPrefix.setParent(this);
        this.VariableDeclarator=VariableDeclarator;
        if(VariableDeclarator!=null) VariableDeclarator.setParent(this);
    }

    public VariableDeclaratorListPrefix getVariableDeclaratorListPrefix() {
        return VariableDeclaratorListPrefix;
    }

    public void setVariableDeclaratorListPrefix(VariableDeclaratorListPrefix VariableDeclaratorListPrefix) {
        this.VariableDeclaratorListPrefix=VariableDeclaratorListPrefix;
    }

    public VariableDeclarator getVariableDeclarator() {
        return VariableDeclarator;
    }

    public void setVariableDeclarator(VariableDeclarator VariableDeclarator) {
        this.VariableDeclarator=VariableDeclarator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VariableDeclaratorListPrefix!=null) VariableDeclaratorListPrefix.accept(visitor);
        if(VariableDeclarator!=null) VariableDeclarator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VariableDeclaratorListPrefix!=null) VariableDeclaratorListPrefix.traverseTopDown(visitor);
        if(VariableDeclarator!=null) VariableDeclarator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VariableDeclaratorListPrefix!=null) VariableDeclaratorListPrefix.traverseBottomUp(visitor);
        if(VariableDeclarator!=null) VariableDeclarator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDeclaratorsMultiple(\n");

        if(VariableDeclaratorListPrefix!=null)
            buffer.append(VariableDeclaratorListPrefix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VariableDeclarator!=null)
            buffer.append(VariableDeclarator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDeclaratorsMultiple]");
        return buffer.toString();
    }
}
