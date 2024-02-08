// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class DesignatorNamespaceName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String NamespaceName;

    public DesignatorNamespaceName (String NamespaceName) {
        this.NamespaceName=NamespaceName;
    }

    public String getNamespaceName() {
        return NamespaceName;
    }

    public void setNamespaceName(String NamespaceName) {
        this.NamespaceName=NamespaceName;
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
        buffer.append("DesignatorNamespaceName(\n");

        buffer.append(" "+tab+NamespaceName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorNamespaceName]");
        return buffer.toString();
    }
}
