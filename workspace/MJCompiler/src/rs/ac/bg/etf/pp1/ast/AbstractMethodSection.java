// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class AbstractMethodSection extends AbstractMethodSectionOpt {

    private AbstractClassMemberList AbstractClassMemberList;

    public AbstractMethodSection (AbstractClassMemberList AbstractClassMemberList) {
        this.AbstractClassMemberList=AbstractClassMemberList;
        if(AbstractClassMemberList!=null) AbstractClassMemberList.setParent(this);
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
        if(AbstractClassMemberList!=null) AbstractClassMemberList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractClassMemberList!=null) AbstractClassMemberList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractClassMemberList!=null) AbstractClassMemberList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractMethodSection(\n");

        if(AbstractClassMemberList!=null)
            buffer.append(AbstractClassMemberList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractMethodSection]");
        return buffer.toString();
    }
}
