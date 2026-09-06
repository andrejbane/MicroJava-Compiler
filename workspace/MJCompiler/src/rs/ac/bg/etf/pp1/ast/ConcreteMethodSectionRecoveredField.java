// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class ConcreteMethodSectionRecoveredField extends ConcreteMethodSectionOpt {

    private ClassFieldRecovery ClassFieldRecovery;
    private MethodDeclarationList MethodDeclarationList;

    public ConcreteMethodSectionRecoveredField (ClassFieldRecovery ClassFieldRecovery, MethodDeclarationList MethodDeclarationList) {
        this.ClassFieldRecovery=ClassFieldRecovery;
        if(ClassFieldRecovery!=null) ClassFieldRecovery.setParent(this);
        this.MethodDeclarationList=MethodDeclarationList;
        if(MethodDeclarationList!=null) MethodDeclarationList.setParent(this);
    }

    public ClassFieldRecovery getClassFieldRecovery() {
        return ClassFieldRecovery;
    }

    public void setClassFieldRecovery(ClassFieldRecovery ClassFieldRecovery) {
        this.ClassFieldRecovery=ClassFieldRecovery;
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
        if(ClassFieldRecovery!=null) ClassFieldRecovery.accept(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFieldRecovery!=null) ClassFieldRecovery.traverseTopDown(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFieldRecovery!=null) ClassFieldRecovery.traverseBottomUp(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConcreteMethodSectionRecoveredField(\n");

        if(ClassFieldRecovery!=null)
            buffer.append(ClassFieldRecovery.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclarationList!=null)
            buffer.append(MethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConcreteMethodSectionRecoveredField]");
        return buffer.toString();
    }
}
