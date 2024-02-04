// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class StatementIfElse extends Statement {

    private IfWord IfWord;
    private IfCond IfCond;
    private Statement Statement;
    private ElseBegin ElseBegin;
    private Statement Statement1;

    public StatementIfElse (IfWord IfWord, IfCond IfCond, Statement Statement, ElseBegin ElseBegin, Statement Statement1) {
        this.IfWord=IfWord;
        if(IfWord!=null) IfWord.setParent(this);
        this.IfCond=IfCond;
        if(IfCond!=null) IfCond.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ElseBegin=ElseBegin;
        if(ElseBegin!=null) ElseBegin.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
    }

    public IfWord getIfWord() {
        return IfWord;
    }

    public void setIfWord(IfWord IfWord) {
        this.IfWord=IfWord;
    }

    public IfCond getIfCond() {
        return IfCond;
    }

    public void setIfCond(IfCond IfCond) {
        this.IfCond=IfCond;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ElseBegin getElseBegin() {
        return ElseBegin;
    }

    public void setElseBegin(ElseBegin ElseBegin) {
        this.ElseBegin=ElseBegin;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfWord!=null) IfWord.accept(visitor);
        if(IfCond!=null) IfCond.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ElseBegin!=null) ElseBegin.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfWord!=null) IfWord.traverseTopDown(visitor);
        if(IfCond!=null) IfCond.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ElseBegin!=null) ElseBegin.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfWord!=null) IfWord.traverseBottomUp(visitor);
        if(IfCond!=null) IfCond.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ElseBegin!=null) ElseBegin.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementIfElse(\n");

        if(IfWord!=null)
            buffer.append(IfWord.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfCond!=null)
            buffer.append(IfCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseBegin!=null)
            buffer.append(ElseBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementIfElse]");
        return buffer.toString();
    }
}
