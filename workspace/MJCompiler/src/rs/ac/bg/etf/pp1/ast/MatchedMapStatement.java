// generated with ast extension for cup
// version 0.8
// 21/6/2026 14:37:24


package rs.ac.bg.etf.pp1.ast;

public class MatchedMapStatement extends MatchedStatement {

    private MapStatement MapStatement;

    public MatchedMapStatement (MapStatement MapStatement) {
        this.MapStatement=MapStatement;
        if(MapStatement!=null) MapStatement.setParent(this);
    }

    public MapStatement getMapStatement() {
        return MapStatement;
    }

    public void setMapStatement(MapStatement MapStatement) {
        this.MapStatement=MapStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MapStatement!=null) MapStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MapStatement!=null) MapStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MapStatement!=null) MapStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatchedMapStatement(\n");

        if(MapStatement!=null)
            buffer.append(MapStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatchedMapStatement]");
        return buffer.toString();
    }
}
