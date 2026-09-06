// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class ConcreteClassHeaderRecoveredExtends extends ConcreteClassHeader {

    private String name;
    private ExtendsRecovery ExtendsRecovery;

    public ConcreteClassHeaderRecoveredExtends (String name, ExtendsRecovery ExtendsRecovery) {
        this.name=name;
        this.ExtendsRecovery=ExtendsRecovery;
        if(ExtendsRecovery!=null) ExtendsRecovery.setParent(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public ExtendsRecovery getExtendsRecovery() {
        return ExtendsRecovery;
    }

    public void setExtendsRecovery(ExtendsRecovery ExtendsRecovery) {
        this.ExtendsRecovery=ExtendsRecovery;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtendsRecovery!=null) ExtendsRecovery.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendsRecovery!=null) ExtendsRecovery.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendsRecovery!=null) ExtendsRecovery.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConcreteClassHeaderRecoveredExtends(\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        if(ExtendsRecovery!=null)
            buffer.append(ExtendsRecovery.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConcreteClassHeaderRecoveredExtends]");
        return buffer.toString();
    }
}
