// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class MethodHeaderRecoveredFormalParameter extends MethodHeader {

    private ReturnType ReturnType;
    private MethodName MethodName;
    private FormalParameterEndRecovery FormalParameterEndRecovery;

    public MethodHeaderRecoveredFormalParameter (ReturnType ReturnType, MethodName MethodName, FormalParameterEndRecovery FormalParameterEndRecovery) {
        this.ReturnType=ReturnType;
        if(ReturnType!=null) ReturnType.setParent(this);
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.FormalParameterEndRecovery=FormalParameterEndRecovery;
        if(FormalParameterEndRecovery!=null) FormalParameterEndRecovery.setParent(this);
    }

    public ReturnType getReturnType() {
        return ReturnType;
    }

    public void setReturnType(ReturnType ReturnType) {
        this.ReturnType=ReturnType;
    }

    public MethodName getMethodName() {
        return MethodName;
    }

    public void setMethodName(MethodName MethodName) {
        this.MethodName=MethodName;
    }

    public FormalParameterEndRecovery getFormalParameterEndRecovery() {
        return FormalParameterEndRecovery;
    }

    public void setFormalParameterEndRecovery(FormalParameterEndRecovery FormalParameterEndRecovery) {
        this.FormalParameterEndRecovery=FormalParameterEndRecovery;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ReturnType!=null) ReturnType.accept(visitor);
        if(MethodName!=null) MethodName.accept(visitor);
        if(FormalParameterEndRecovery!=null) FormalParameterEndRecovery.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReturnType!=null) ReturnType.traverseTopDown(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(FormalParameterEndRecovery!=null) FormalParameterEndRecovery.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReturnType!=null) ReturnType.traverseBottomUp(visitor);
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(FormalParameterEndRecovery!=null) FormalParameterEndRecovery.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodHeaderRecoveredFormalParameter(\n");

        if(ReturnType!=null)
            buffer.append(ReturnType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodName!=null)
            buffer.append(MethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormalParameterEndRecovery!=null)
            buffer.append(FormalParameterEndRecovery.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodHeaderRecoveredFormalParameter]");
        return buffer.toString();
    }
}
