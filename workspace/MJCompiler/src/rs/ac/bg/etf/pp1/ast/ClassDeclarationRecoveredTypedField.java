// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclarationRecoveredTypedField extends ClassDeclaration {

    private ConcreteClassHeader ConcreteClassHeader;
    private ClassFieldList ClassFieldList;
    private Type Type;
    private ClassFieldToMethodRecovery ClassFieldToMethodRecovery;
    private MethodDeclarationList MethodDeclarationList;

    public ClassDeclarationRecoveredTypedField (ConcreteClassHeader ConcreteClassHeader, ClassFieldList ClassFieldList, Type Type, ClassFieldToMethodRecovery ClassFieldToMethodRecovery, MethodDeclarationList MethodDeclarationList) {
        this.ConcreteClassHeader=ConcreteClassHeader;
        if(ConcreteClassHeader!=null) ConcreteClassHeader.setParent(this);
        this.ClassFieldList=ClassFieldList;
        if(ClassFieldList!=null) ClassFieldList.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ClassFieldToMethodRecovery=ClassFieldToMethodRecovery;
        if(ClassFieldToMethodRecovery!=null) ClassFieldToMethodRecovery.setParent(this);
        this.MethodDeclarationList=MethodDeclarationList;
        if(MethodDeclarationList!=null) MethodDeclarationList.setParent(this);
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

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ClassFieldToMethodRecovery getClassFieldToMethodRecovery() {
        return ClassFieldToMethodRecovery;
    }

    public void setClassFieldToMethodRecovery(ClassFieldToMethodRecovery ClassFieldToMethodRecovery) {
        this.ClassFieldToMethodRecovery=ClassFieldToMethodRecovery;
    }

    public MethodDeclarationList getMethodDeclarationList() {
        return MethodDeclarationList;
    }

    public void setMethodDeclarationList(MethodDeclarationList MethodDeclarationList) {
        this.MethodDeclarationList=MethodDeclarationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConcreteClassHeader!=null) ConcreteClassHeader.accept(visitor);
        if(ClassFieldList!=null) ClassFieldList.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(ClassFieldToMethodRecovery!=null) ClassFieldToMethodRecovery.accept(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConcreteClassHeader!=null) ConcreteClassHeader.traverseTopDown(visitor);
        if(ClassFieldList!=null) ClassFieldList.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ClassFieldToMethodRecovery!=null) ClassFieldToMethodRecovery.traverseTopDown(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConcreteClassHeader!=null) ConcreteClassHeader.traverseBottomUp(visitor);
        if(ClassFieldList!=null) ClassFieldList.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ClassFieldToMethodRecovery!=null) ClassFieldToMethodRecovery.traverseBottomUp(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclarationRecoveredTypedField(\n");

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

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassFieldToMethodRecovery!=null)
            buffer.append(ClassFieldToMethodRecovery.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclarationList!=null)
            buffer.append(MethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclarationRecoveredTypedField]");
        return buffer.toString();
    }
}
