// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class LocalVariableDeclarationsMultiple extends LocalVariableDeclarationList {

    private LocalVariableDeclarationList LocalVariableDeclarationList;
    private VariableDeclaration VariableDeclaration;

    public LocalVariableDeclarationsMultiple (LocalVariableDeclarationList LocalVariableDeclarationList, VariableDeclaration VariableDeclaration) {
        this.LocalVariableDeclarationList=LocalVariableDeclarationList;
        if(LocalVariableDeclarationList!=null) LocalVariableDeclarationList.setParent(this);
        this.VariableDeclaration=VariableDeclaration;
        if(VariableDeclaration!=null) VariableDeclaration.setParent(this);
    }

    public LocalVariableDeclarationList getLocalVariableDeclarationList() {
        return LocalVariableDeclarationList;
    }

    public void setLocalVariableDeclarationList(LocalVariableDeclarationList LocalVariableDeclarationList) {
        this.LocalVariableDeclarationList=LocalVariableDeclarationList;
    }

    public VariableDeclaration getVariableDeclaration() {
        return VariableDeclaration;
    }

    public void setVariableDeclaration(VariableDeclaration VariableDeclaration) {
        this.VariableDeclaration=VariableDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVariableDeclarationList!=null) LocalVariableDeclarationList.accept(visitor);
        if(VariableDeclaration!=null) VariableDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVariableDeclarationList!=null) LocalVariableDeclarationList.traverseTopDown(visitor);
        if(VariableDeclaration!=null) VariableDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVariableDeclarationList!=null) LocalVariableDeclarationList.traverseBottomUp(visitor);
        if(VariableDeclaration!=null) VariableDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LocalVariableDeclarationsMultiple(\n");

        if(LocalVariableDeclarationList!=null)
            buffer.append(LocalVariableDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VariableDeclaration!=null)
            buffer.append(VariableDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LocalVariableDeclarationsMultiple]");
        return buffer.toString();
    }
}
