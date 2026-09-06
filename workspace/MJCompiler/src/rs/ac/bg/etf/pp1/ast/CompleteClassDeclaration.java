// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class CompleteClassDeclaration extends ClassDeclaration {

    private ConcreteClassHeader ConcreteClassHeader;
    private ClassFieldList ClassFieldList;
    private ConcreteMethodSectionOpt ConcreteMethodSectionOpt;

    public CompleteClassDeclaration (ConcreteClassHeader ConcreteClassHeader, ClassFieldList ClassFieldList, ConcreteMethodSectionOpt ConcreteMethodSectionOpt) {
        this.ConcreteClassHeader=ConcreteClassHeader;
        if(ConcreteClassHeader!=null) ConcreteClassHeader.setParent(this);
        this.ClassFieldList=ClassFieldList;
        if(ClassFieldList!=null) ClassFieldList.setParent(this);
        this.ConcreteMethodSectionOpt=ConcreteMethodSectionOpt;
        if(ConcreteMethodSectionOpt!=null) ConcreteMethodSectionOpt.setParent(this);
    }

    public ConcreteClassHeader getConcreteClassHeader() {
        return ConcreteClassHeader;
    }

    public void setConcreteClassHeader(ConcreteClassHeader ConcreteClassHeader) {
        this.ConcreteClassHeader=ConcreteClassHeader;
    }

    public ClassFieldList getClassFieldList() {
        return ClassFieldList;
    }

    public void setClassFieldList(ClassFieldList ClassFieldList) {
        this.ClassFieldList=ClassFieldList;
    }

    public ConcreteMethodSectionOpt getConcreteMethodSectionOpt() {
        return ConcreteMethodSectionOpt;
    }

    public void setConcreteMethodSectionOpt(ConcreteMethodSectionOpt ConcreteMethodSectionOpt) {
        this.ConcreteMethodSectionOpt=ConcreteMethodSectionOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConcreteClassHeader!=null) ConcreteClassHeader.accept(visitor);
        if(ClassFieldList!=null) ClassFieldList.accept(visitor);
        if(ConcreteMethodSectionOpt!=null) ConcreteMethodSectionOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConcreteClassHeader!=null) ConcreteClassHeader.traverseTopDown(visitor);
        if(ClassFieldList!=null) ClassFieldList.traverseTopDown(visitor);
        if(ConcreteMethodSectionOpt!=null) ConcreteMethodSectionOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConcreteClassHeader!=null) ConcreteClassHeader.traverseBottomUp(visitor);
        if(ClassFieldList!=null) ClassFieldList.traverseBottomUp(visitor);
        if(ConcreteMethodSectionOpt!=null) ConcreteMethodSectionOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CompleteClassDeclaration(\n");

        if(ConcreteClassHeader!=null)
            buffer.append(ConcreteClassHeader.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassFieldList!=null)
            buffer.append(ClassFieldList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConcreteMethodSectionOpt!=null)
            buffer.append(ConcreteMethodSectionOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CompleteClassDeclaration]");
        return buffer.toString();
    }
}
