// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class FormalParametersMultiple extends FormalParameters {

    private FormalParametersPrefix FormalParametersPrefix;
    private FormalParameter FormalParameter;

    public FormalParametersMultiple (FormalParametersPrefix FormalParametersPrefix, FormalParameter FormalParameter) {
        this.FormalParametersPrefix=FormalParametersPrefix;
        if(FormalParametersPrefix!=null) FormalParametersPrefix.setParent(this);
        this.FormalParameter=FormalParameter;
        if(FormalParameter!=null) FormalParameter.setParent(this);
    }

    public FormalParametersPrefix getFormalParametersPrefix() {
        return FormalParametersPrefix;
    }

    public void setFormalParametersPrefix(FormalParametersPrefix FormalParametersPrefix) {
        this.FormalParametersPrefix=FormalParametersPrefix;
    }

    public FormalParameter getFormalParameter() {
        return FormalParameter;
    }

    public void setFormalParameter(FormalParameter FormalParameter) {
        this.FormalParameter=FormalParameter;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormalParametersPrefix!=null) FormalParametersPrefix.accept(visitor);
        if(FormalParameter!=null) FormalParameter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalParametersPrefix!=null) FormalParametersPrefix.traverseTopDown(visitor);
        if(FormalParameter!=null) FormalParameter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalParametersPrefix!=null) FormalParametersPrefix.traverseBottomUp(visitor);
        if(FormalParameter!=null) FormalParameter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParametersMultiple(\n");

        if(FormalParametersPrefix!=null)
            buffer.append(FormalParametersPrefix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormalParameter!=null)
            buffer.append(FormalParameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParametersMultiple]");
        return buffer.toString();
    }
}
