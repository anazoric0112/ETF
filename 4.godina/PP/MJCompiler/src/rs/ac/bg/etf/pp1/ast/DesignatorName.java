// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class DesignatorName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String DesignatorName;

    public DesignatorName (String DesignatorName) {
        this.DesignatorName=DesignatorName;
    }

    public String getDesignatorName() {
        return DesignatorName;
    }

    public void setDesignatorName(String DesignatorName) {
        this.DesignatorName=DesignatorName;
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
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorName(\n");

        buffer.append(" "+tab+DesignatorName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorName]");
        return buffer.toString();
    }
}
