// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class StatementFor extends Statement {

    private ForWord ForWord;
    private ForInit ForInit;
    private ForCondFact ForCondFact;
    private ForAfter ForAfter;
    private Statement Statement;

    public StatementFor (ForWord ForWord, ForInit ForInit, ForCondFact ForCondFact, ForAfter ForAfter, Statement Statement) {
        this.ForWord=ForWord;
        if(ForWord!=null) ForWord.setParent(this);
        this.ForInit=ForInit;
        if(ForInit!=null) ForInit.setParent(this);
        this.ForCondFact=ForCondFact;
        if(ForCondFact!=null) ForCondFact.setParent(this);
        this.ForAfter=ForAfter;
        if(ForAfter!=null) ForAfter.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForWord getForWord() {
        return ForWord;
    }

    public void setForWord(ForWord ForWord) {
        this.ForWord=ForWord;
    }

    public ForInit getForInit() {
        return ForInit;
    }

    public void setForInit(ForInit ForInit) {
        this.ForInit=ForInit;
    }

    public ForCondFact getForCondFact() {
        return ForCondFact;
    }

    public void setForCondFact(ForCondFact ForCondFact) {
        this.ForCondFact=ForCondFact;
    }

    public ForAfter getForAfter() {
        return ForAfter;
    }

    public void setForAfter(ForAfter ForAfter) {
        this.ForAfter=ForAfter;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForWord!=null) ForWord.accept(visitor);
        if(ForInit!=null) ForInit.accept(visitor);
        if(ForCondFact!=null) ForCondFact.accept(visitor);
        if(ForAfter!=null) ForAfter.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForWord!=null) ForWord.traverseTopDown(visitor);
        if(ForInit!=null) ForInit.traverseTopDown(visitor);
        if(ForCondFact!=null) ForCondFact.traverseTopDown(visitor);
        if(ForAfter!=null) ForAfter.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForWord!=null) ForWord.traverseBottomUp(visitor);
        if(ForInit!=null) ForInit.traverseBottomUp(visitor);
        if(ForCondFact!=null) ForCondFact.traverseBottomUp(visitor);
        if(ForAfter!=null) ForAfter.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementFor(\n");

        if(ForWord!=null)
            buffer.append(ForWord.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForInit!=null)
            buffer.append(ForInit.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCondFact!=null)
            buffer.append(ForCondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForAfter!=null)
            buffer.append(ForAfter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementFor]");
        return buffer.toString();
    }
}
