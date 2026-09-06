// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassDeclarationRecoveredTypedField extends AbstractClassDeclaration {

    private AbstractClassHeader AbstractClassHeader;
    private ClassFieldList ClassFieldList;
    private Type Type;
    private ClassFieldToMethodRecovery ClassFieldToMethodRecovery;
    private AbstractClassMemberList AbstractClassMemberList;

    public AbstractClassDeclarationRecoveredTypedField (AbstractClassHeader AbstractClassHeader, ClassFieldList ClassFieldList, Type Type, ClassFieldToMethodRecovery ClassFieldToMethodRecovery, AbstractClassMemberList AbstractClassMemberList) {
        this.AbstractClassHeader=AbstractClassHeader;
        if(AbstractClassHeader!=null) AbstractClassHeader.setParent(this);
        this.ClassFieldList=ClassFieldList;
        if(ClassFieldList!=null) ClassFieldList.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ClassFieldToMethodRecovery=ClassFieldToMethodRecovery;
        if(ClassFieldToMethodRecovery!=null) ClassFieldToMethodRecovery.setParent(this);
        this.AbstractClassMemberList=AbstractClassMemberList;
        if(AbstractClassMemberList!=null) AbstractClassMemberList.setParent(this);
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

    public AbstractClassMemberList getAbstractClassMemberList() {
        return AbstractClassMemberList;
    }

    public void setAbstractClassMemberList(AbstractClassMemberList AbstractClassMemberList) {
        this.AbstractClassMemberList=AbstractClassMemberList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassHeader!=null) AbstractClassHeader.accept(visitor);
        if(ClassFieldList!=null) ClassFieldList.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(ClassFieldToMethodRecovery!=null) ClassFieldToMethodRecovery.accept(visitor);
        if(AbstractClassMemberList!=null) AbstractClassMemberList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassHeader!=null) AbstractClassHeader.traverseTopDown(visitor);
        if(ClassFieldList!=null) ClassFieldList.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ClassFieldToMethodRecovery!=null) ClassFieldToMethodRecovery.traverseTopDown(visitor);
        if(AbstractClassMemberList!=null) AbstractClassMemberList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassHeader!=null) AbstractClassHeader.traverseBottomUp(visitor);
        if(ClassFieldList!=null) ClassFieldList.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ClassFieldToMethodRecovery!=null) ClassFieldToMethodRecovery.traverseBottomUp(visitor);
        if(AbstractClassMemberList!=null) AbstractClassMemberList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassDeclarationRecoveredTypedField(\n");

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

        if(AbstractClassMemberList!=null)
            buffer.append(AbstractClassMemberList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassDeclarationRecoveredTypedField]");
        return buffer.toString();
    }
}
