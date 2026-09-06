// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassAbstractMethod extends AbstractClassMember {

    private AbstractMethodDeclaration AbstractMethodDeclaration;

    public AbstractClassAbstractMethod (AbstractMethodDeclaration AbstractMethodDeclaration) {
        this.AbstractMethodDeclaration=AbstractMethodDeclaration;
        if(AbstractMethodDeclaration!=null) AbstractMethodDeclaration.setParent(this);
    }

    public AbstractMethodDeclaration getAbstractMethodDeclaration() {
        return AbstractMethodDeclaration;
    }

    public void setAbstractMethodDeclaration(AbstractMethodDeclaration AbstractMethodDeclaration) {
        this.AbstractMethodDeclaration=AbstractMethodDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractMethodDeclaration!=null) AbstractMethodDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractMethodDeclaration!=null) AbstractMethodDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractMethodDeclaration!=null) AbstractMethodDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassAbstractMethod(\n");

        if(AbstractMethodDeclaration!=null)
            buffer.append(AbstractMethodDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassAbstractMethod]");
        return buffer.toString();
    }
}
