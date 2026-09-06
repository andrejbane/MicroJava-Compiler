// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclaratorsMultiple extends ConstDeclaratorList {

    private ConstDeclaratorList ConstDeclaratorList;
    private ConstDeclarator ConstDeclarator;

    public ConstDeclaratorsMultiple (ConstDeclaratorList ConstDeclaratorList, ConstDeclarator ConstDeclarator) {
        this.ConstDeclaratorList=ConstDeclaratorList;
        if(ConstDeclaratorList!=null) ConstDeclaratorList.setParent(this);
        this.ConstDeclarator=ConstDeclarator;
        if(ConstDeclarator!=null) ConstDeclarator.setParent(this);
    }

    public ConstDeclaratorList getConstDeclaratorList() {
        return ConstDeclaratorList;
    }

    public void setConstDeclaratorList(ConstDeclaratorList ConstDeclaratorList) {
        this.ConstDeclaratorList=ConstDeclaratorList;
    }

    public ConstDeclarator getConstDeclarator() {
        return ConstDeclarator;
    }

    public void setConstDeclarator(ConstDeclarator ConstDeclarator) {
        this.ConstDeclarator=ConstDeclarator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclaratorList!=null) ConstDeclaratorList.accept(visitor);
        if(ConstDeclarator!=null) ConstDeclarator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclaratorList!=null) ConstDeclaratorList.traverseTopDown(visitor);
        if(ConstDeclarator!=null) ConstDeclarator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclaratorList!=null) ConstDeclaratorList.traverseBottomUp(visitor);
        if(ConstDeclarator!=null) ConstDeclarator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclaratorsMultiple(\n");

        if(ConstDeclaratorList!=null)
            buffer.append(ConstDeclaratorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclarator!=null)
            buffer.append(ConstDeclarator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclaratorsMultiple]");
        return buffer.toString();
    }
}
