// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private MethodDeclParams MethodDeclParams;
    private VarDeclOrNot VarDeclOrNot;
    private MethodBegin MethodBegin;
    private StatementList StatementList;

    public MethodDecl (MethodDeclParams MethodDeclParams, VarDeclOrNot VarDeclOrNot, MethodBegin MethodBegin, StatementList StatementList) {
        this.MethodDeclParams=MethodDeclParams;
        if(MethodDeclParams!=null) MethodDeclParams.setParent(this);
        this.VarDeclOrNot=VarDeclOrNot;
        if(VarDeclOrNot!=null) VarDeclOrNot.setParent(this);
        this.MethodBegin=MethodBegin;
        if(MethodBegin!=null) MethodBegin.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodDeclParams getMethodDeclParams() {
        return MethodDeclParams;
    }

    public void setMethodDeclParams(MethodDeclParams MethodDeclParams) {
        this.MethodDeclParams=MethodDeclParams;
    }

    public VarDeclOrNot getVarDeclOrNot() {
        return VarDeclOrNot;
    }

    public void setVarDeclOrNot(VarDeclOrNot VarDeclOrNot) {
        this.VarDeclOrNot=VarDeclOrNot;
    }

    public MethodBegin getMethodBegin() {
        return MethodBegin;
    }

    public void setMethodBegin(MethodBegin MethodBegin) {
        this.MethodBegin=MethodBegin;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
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
        if(MethodDeclParams!=null) MethodDeclParams.accept(visitor);
        if(VarDeclOrNot!=null) VarDeclOrNot.accept(visitor);
        if(MethodBegin!=null) MethodBegin.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclParams!=null) MethodDeclParams.traverseTopDown(visitor);
        if(VarDeclOrNot!=null) VarDeclOrNot.traverseTopDown(visitor);
        if(MethodBegin!=null) MethodBegin.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclParams!=null) MethodDeclParams.traverseBottomUp(visitor);
        if(VarDeclOrNot!=null) VarDeclOrNot.traverseBottomUp(visitor);
        if(MethodBegin!=null) MethodBegin.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodDeclParams!=null)
            buffer.append(MethodDeclParams.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclOrNot!=null)
            buffer.append(VarDeclOrNot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodBegin!=null)
            buffer.append(MethodBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
