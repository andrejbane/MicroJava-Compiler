// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class GlobalAbstractClassDeclaration extends GlobalDeclaration {

    private AbstractClassDeclaration AbstractClassDeclaration;

    public GlobalAbstractClassDeclaration (AbstractClassDeclaration AbstractClassDeclaration) {
        this.AbstractClassDeclaration=AbstractClassDeclaration;
        if(AbstractClassDeclaration!=null) AbstractClassDeclaration.setParent(this);
    }

    public AbstractClassDeclaration getAbstractClassDeclaration() {
        return AbstractClassDeclaration;
    }

    public void setAbstractClassDeclaration(AbstractClassDeclaration AbstractClassDeclaration) {
        this.AbstractClassDeclaration=AbstractClassDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassDeclaration!=null) AbstractClassDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassDeclaration!=null) AbstractClassDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassDeclaration!=null) AbstractClassDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalAbstractClassDeclaration(\n");

        if(AbstractClassDeclaration!=null)
            buffer.append(AbstractClassDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalAbstractClassDeclaration]");
        return buffer.toString();
    }
}
