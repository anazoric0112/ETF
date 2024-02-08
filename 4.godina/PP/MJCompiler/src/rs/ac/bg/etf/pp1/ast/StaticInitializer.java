// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class StaticInitializer implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private StaticWord StaticWord;
    private StaticInitializerStart StaticInitializerStart;
    private StatementList StatementList;
    private StaticInitializerEnd StaticInitializerEnd;

    public StaticInitializer (StaticWord StaticWord, StaticInitializerStart StaticInitializerStart, StatementList StatementList, StaticInitializerEnd StaticInitializerEnd) {
        this.StaticWord=StaticWord;
        if(StaticWord!=null) StaticWord.setParent(this);
        this.StaticInitializerStart=StaticInitializerStart;
        if(StaticInitializerStart!=null) StaticInitializerStart.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
        this.StaticInitializerEnd=StaticInitializerEnd;
        if(StaticInitializerEnd!=null) StaticInitializerEnd.setParent(this);
    }

    public StaticWord getStaticWord() {
        return StaticWord;
    }

    public void setStaticWord(StaticWord StaticWord) {
        this.StaticWord=StaticWord;
    }

    public StaticInitializerStart getStaticInitializerStart() {
        return StaticInitializerStart;
    }

    public void setStaticInitializerStart(StaticInitializerStart StaticInitializerStart) {
        this.StaticInitializerStart=StaticInitializerStart;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public StaticInitializerEnd getStaticInitializerEnd() {
        return StaticInitializerEnd;
    }

    public void setStaticInitializerEnd(StaticInitializerEnd StaticInitializerEnd) {
        this.StaticInitializerEnd=StaticInitializerEnd;
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
        if(StaticWord!=null) StaticWord.accept(visitor);
        if(StaticInitializerStart!=null) StaticInitializerStart.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
        if(StaticInitializerEnd!=null) StaticInitializerEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StaticWord!=null) StaticWord.traverseTopDown(visitor);
        if(StaticInitializerStart!=null) StaticInitializerStart.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
        if(StaticInitializerEnd!=null) StaticInitializerEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StaticWord!=null) StaticWord.traverseBottomUp(visitor);
        if(StaticInitializerStart!=null) StaticInitializerStart.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        if(StaticInitializerEnd!=null) StaticInitializerEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StaticInitializer(\n");

        if(StaticWord!=null)
            buffer.append(StaticWord.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StaticInitializerStart!=null)
            buffer.append(StaticInitializerStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StaticInitializerEnd!=null)
            buffer.append(StaticInitializerEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StaticInitializer]");
        return buffer.toString();
    }
}
