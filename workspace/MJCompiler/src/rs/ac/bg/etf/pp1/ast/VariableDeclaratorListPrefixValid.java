// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class VariableDeclaratorListPrefixValid extends VariableDeclaratorListPrefix {

    private VariableDeclaratorList VariableDeclaratorList;

    public VariableDeclaratorListPrefixValid (VariableDeclaratorList VariableDeclaratorList) {
        this.VariableDeclaratorList=VariableDeclaratorList;
        if(VariableDeclaratorList!=null) VariableDeclaratorList.setParent(this);
    }

    public VariableDeclaratorList getVariableDeclaratorList() {
        return VariableDeclaratorList;
    }

    public void setVariableDeclaratorList(VariableDeclaratorList VariableDeclaratorList) {
        this.VariableDeclaratorList=VariableDeclaratorList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VariableDeclaratorList!=null) VariableDeclaratorList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VariableDeclaratorList!=null) VariableDeclaratorList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VariableDeclaratorList!=null) VariableDeclaratorList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDeclaratorListPrefixValid(\n");

        if(VariableDeclaratorList!=null)
            buffer.append(VariableDeclaratorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDeclaratorListPrefixValid]");
        return buffer.toString();
    }
}
