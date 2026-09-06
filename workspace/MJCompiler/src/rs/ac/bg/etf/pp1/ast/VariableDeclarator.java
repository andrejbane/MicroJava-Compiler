// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class VariableDeclarator implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String name;
    private ArraySuffix ArraySuffix;

    public VariableDeclarator (String name, ArraySuffix ArraySuffix) {
        this.name=name;
        this.ArraySuffix=ArraySuffix;
        if(ArraySuffix!=null) ArraySuffix.setParent(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public ArraySuffix getArraySuffix() {
        return ArraySuffix;
    }

    public void setArraySuffix(ArraySuffix ArraySuffix) {
        this.ArraySuffix=ArraySuffix;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArraySuffix!=null) ArraySuffix.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArraySuffix!=null) ArraySuffix.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArraySuffix!=null) ArraySuffix.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDeclarator(\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        if(ArraySuffix!=null)
            buffer.append(ArraySuffix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDeclarator]");
        return buffer.toString();
    }
}
