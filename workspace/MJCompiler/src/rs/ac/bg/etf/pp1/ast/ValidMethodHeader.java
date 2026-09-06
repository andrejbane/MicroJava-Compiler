// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class ValidMethodHeader extends MethodHeader {

    private ReturnType ReturnType;
    private MethodName MethodName;
    private FormalParametersOpt FormalParametersOpt;

    public ValidMethodHeader (ReturnType ReturnType, MethodName MethodName, FormalParametersOpt FormalParametersOpt) {
        this.ReturnType=ReturnType;
        if(ReturnType!=null) ReturnType.setParent(this);
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.FormalParametersOpt=FormalParametersOpt;
        if(FormalParametersOpt!=null) FormalParametersOpt.setParent(this);
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

    public FormalParametersOpt getFormalParametersOpt() {
        return FormalParametersOpt;
    }

    public void setFormalParametersOpt(FormalParametersOpt FormalParametersOpt) {
        this.FormalParametersOpt=FormalParametersOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ReturnType!=null) ReturnType.accept(visitor);
        if(MethodName!=null) MethodName.accept(visitor);
        if(FormalParametersOpt!=null) FormalParametersOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReturnType!=null) ReturnType.traverseTopDown(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(FormalParametersOpt!=null) FormalParametersOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReturnType!=null) ReturnType.traverseBottomUp(visitor);
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(FormalParametersOpt!=null) FormalParametersOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ValidMethodHeader(\n");

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

        if(FormalParametersOpt!=null)
            buffer.append(FormalParametersOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ValidMethodHeader]");
        return buffer.toString();
    }
}
