// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class CompleteAbstractClassDeclaration extends AbstractClassDeclaration {

    private AbstractClassHeader AbstractClassHeader;
    private ClassFieldList ClassFieldList;
    private AbstractMethodSectionOpt AbstractMethodSectionOpt;

    public CompleteAbstractClassDeclaration (AbstractClassHeader AbstractClassHeader, ClassFieldList ClassFieldList, AbstractMethodSectionOpt AbstractMethodSectionOpt) {
        this.AbstractClassHeader=AbstractClassHeader;
        if(AbstractClassHeader!=null) AbstractClassHeader.setParent(this);
        this.ClassFieldList=ClassFieldList;
        if(ClassFieldList!=null) ClassFieldList.setParent(this);
        this.AbstractMethodSectionOpt=AbstractMethodSectionOpt;
        if(AbstractMethodSectionOpt!=null) AbstractMethodSectionOpt.setParent(this);
    }

    public AbstractClassHeader getAbstractClassHeader() {
        return AbstractClassHeader;
    }

    public void setAbstractClassHeader(AbstractClassHeader AbstractClassHeader) {
        this.AbstractClassHeader=AbstractClassHeader;
    }

    public ClassFieldList getClassFieldList() {
        return ClassFieldList;
    }

    public void setClassFieldList(ClassFieldList ClassFieldList) {
        this.ClassFieldList=ClassFieldList;
    }

    public AbstractMethodSectionOpt getAbstractMethodSectionOpt() {
        return AbstractMethodSectionOpt;
    }

    public void setAbstractMethodSectionOpt(AbstractMethodSectionOpt AbstractMethodSectionOpt) {
        this.AbstractMethodSectionOpt=AbstractMethodSectionOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassHeader!=null) AbstractClassHeader.accept(visitor);
        if(ClassFieldList!=null) ClassFieldList.accept(visitor);
        if(AbstractMethodSectionOpt!=null) AbstractMethodSectionOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassHeader!=null) AbstractClassHeader.traverseTopDown(visitor);
        if(ClassFieldList!=null) ClassFieldList.traverseTopDown(visitor);
        if(AbstractMethodSectionOpt!=null) AbstractMethodSectionOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassHeader!=null) AbstractClassHeader.traverseBottomUp(visitor);
        if(ClassFieldList!=null) ClassFieldList.traverseBottomUp(visitor);
        if(AbstractMethodSectionOpt!=null) AbstractMethodSectionOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CompleteAbstractClassDeclaration(\n");

        if(AbstractClassHeader!=null)
            buffer.append(AbstractClassHeader.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassFieldList!=null)
            buffer.append(ClassFieldList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AbstractMethodSectionOpt!=null)
            buffer.append(AbstractMethodSectionOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CompleteAbstractClassDeclaration]");
        return buffer.toString();
    }
}
