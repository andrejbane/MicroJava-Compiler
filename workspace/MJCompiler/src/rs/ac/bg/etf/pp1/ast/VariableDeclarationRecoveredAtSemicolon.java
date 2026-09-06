// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class VariableDeclarationRecoveredAtSemicolon extends VariableDeclaration {

    private Type Type;
    private VariableDeclarationRecovery VariableDeclarationRecovery;

    public VariableDeclarationRecoveredAtSemicolon (Type Type, VariableDeclarationRecovery VariableDeclarationRecovery) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VariableDeclarationRecovery=VariableDeclarationRecovery;
        if(VariableDeclarationRecovery!=null) VariableDeclarationRecovery.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VariableDeclarationRecovery getVariableDeclarationRecovery() {
        return VariableDeclarationRecovery;
    }

    public void setVariableDeclarationRecovery(VariableDeclarationRecovery VariableDeclarationRecovery) {
        this.VariableDeclarationRecovery=VariableDeclarationRecovery;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VariableDeclarationRecovery!=null) VariableDeclarationRecovery.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VariableDeclarationRecovery!=null) VariableDeclarationRecovery.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VariableDeclarationRecovery!=null) VariableDeclarationRecovery.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDeclarationRecoveredAtSemicolon(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VariableDeclarationRecovery!=null)
            buffer.append(VariableDeclarationRecovery.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDeclarationRecoveredAtSemicolon]");
        return buffer.toString();
    }
}
