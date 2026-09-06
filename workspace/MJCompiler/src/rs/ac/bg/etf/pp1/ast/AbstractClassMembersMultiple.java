// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class AbstractClassMembersMultiple extends AbstractClassMemberList {

    private AbstractClassMemberList AbstractClassMemberList;
    private AbstractClassMember AbstractClassMember;

    public AbstractClassMembersMultiple (AbstractClassMemberList AbstractClassMemberList, AbstractClassMember AbstractClassMember) {
        this.AbstractClassMemberList=AbstractClassMemberList;
        if(AbstractClassMemberList!=null) AbstractClassMemberList.setParent(this);
        this.AbstractClassMember=AbstractClassMember;
        if(AbstractClassMember!=null) AbstractClassMember.setParent(this);
    }

    public AbstractClassMemberList getAbstractClassMemberList() {
        return AbstractClassMemberList;
    }

    public void setAbstractClassMemberList(AbstractClassMemberList AbstractClassMemberList) {
        this.AbstractClassMemberList=AbstractClassMemberList;
    }

    public AbstractClassMember getAbstractClassMember() {
        return AbstractClassMember;
    }

    public void setAbstractClassMember(AbstractClassMember AbstractClassMember) {
        this.AbstractClassMember=AbstractClassMember;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractClassMemberList!=null) AbstractClassMemberList.accept(visitor);
        if(AbstractClassMember!=null) AbstractClassMember.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassMemberList!=null) AbstractClassMemberList.traverseTopDown(visitor);
        if(AbstractClassMember!=null) AbstractClassMember.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassMemberList!=null) AbstractClassMemberList.traverseBottomUp(visitor);
        if(AbstractClassMember!=null) AbstractClassMember.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractClassMembersMultiple(\n");

        if(AbstractClassMemberList!=null)
            buffer.append(AbstractClassMemberList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AbstractClassMember!=null)
            buffer.append(AbstractClassMember.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractClassMembersMultiple]");
        return buffer.toString();
    }
}
