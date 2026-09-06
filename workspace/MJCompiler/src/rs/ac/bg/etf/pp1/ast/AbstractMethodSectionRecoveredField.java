// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class AbstractMethodSectionRecoveredField extends AbstractMethodSectionOpt {

    private ClassFieldRecovery ClassFieldRecovery;
    private AbstractClassMemberList AbstractClassMemberList;

    public AbstractMethodSectionRecoveredField (ClassFieldRecovery ClassFieldRecovery, AbstractClassMemberList AbstractClassMemberList) {
        this.ClassFieldRecovery=ClassFieldRecovery;
        if(ClassFieldRecovery!=null) ClassFieldRecovery.setParent(this);
        this.AbstractClassMemberList=AbstractClassMemberList;
        if(AbstractClassMemberList!=null) AbstractClassMemberList.setParent(this);
    }

    public ClassFieldRecovery getClassFieldRecovery() {
        return ClassFieldRecovery;
    }

    public void setClassFieldRecovery(ClassFieldRecovery ClassFieldRecovery) {
        this.ClassFieldRecovery=ClassFieldRecovery;
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
        if(ClassFieldRecovery!=null) ClassFieldRecovery.accept(visitor);
        if(AbstractClassMemberList!=null) AbstractClassMemberList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFieldRecovery!=null) ClassFieldRecovery.traverseTopDown(visitor);
        if(AbstractClassMemberList!=null) AbstractClassMemberList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFieldRecovery!=null) ClassFieldRecovery.traverseBottomUp(visitor);
        if(AbstractClassMemberList!=null) AbstractClassMemberList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractMethodSectionRecoveredField(\n");

        if(ClassFieldRecovery!=null)
            buffer.append(ClassFieldRecovery.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AbstractClassMemberList!=null)
            buffer.append(AbstractClassMemberList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractMethodSectionRecoveredField]");
        return buffer.toString();
    }
}
