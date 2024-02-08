// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class ForDesignatorStatementListMore extends ForDesignatorStatementList {

    private ForDesignatorStatementList ForDesignatorStatementList;
    private DesignatorStatement DesignatorStatement;

    public ForDesignatorStatementListMore (ForDesignatorStatementList ForDesignatorStatementList, DesignatorStatement DesignatorStatement) {
        this.ForDesignatorStatementList=ForDesignatorStatementList;
        if(ForDesignatorStatementList!=null) ForDesignatorStatementList.setParent(this);
        this.DesignatorStatement=DesignatorStatement;
        if(DesignatorStatement!=null) DesignatorStatement.setParent(this);
    }

    public ForDesignatorStatementList getForDesignatorStatementList() {
        return ForDesignatorStatementList;
    }

    public void setForDesignatorStatementList(ForDesignatorStatementList ForDesignatorStatementList) {
        this.ForDesignatorStatementList=ForDesignatorStatementList;
    }

    public DesignatorStatement getDesignatorStatement() {
        return DesignatorStatement;
    }

    public void setDesignatorStatement(DesignatorStatement DesignatorStatement) {
        this.DesignatorStatement=DesignatorStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForDesignatorStatementList!=null) ForDesignatorStatementList.accept(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForDesignatorStatementList!=null) ForDesignatorStatementList.traverseTopDown(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForDesignatorStatementList!=null) ForDesignatorStatementList.traverseBottomUp(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForDesignatorStatementListMore(\n");

        if(ForDesignatorStatementList!=null)
            buffer.append(ForDesignatorStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatement!=null)
            buffer.append(DesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForDesignatorStatementListMore]");
        return buffer.toString();
    }
}
