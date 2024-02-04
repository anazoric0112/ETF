// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementListMore extends DesignatorStatementList {

    private DesignatorStatementList DesignatorStatementList;
    private DesignatorStatementListMemberOrNot DesignatorStatementListMemberOrNot;

    public DesignatorStatementListMore (DesignatorStatementList DesignatorStatementList, DesignatorStatementListMemberOrNot DesignatorStatementListMemberOrNot) {
        this.DesignatorStatementList=DesignatorStatementList;
        if(DesignatorStatementList!=null) DesignatorStatementList.setParent(this);
        this.DesignatorStatementListMemberOrNot=DesignatorStatementListMemberOrNot;
        if(DesignatorStatementListMemberOrNot!=null) DesignatorStatementListMemberOrNot.setParent(this);
    }

    public DesignatorStatementList getDesignatorStatementList() {
        return DesignatorStatementList;
    }

    public void setDesignatorStatementList(DesignatorStatementList DesignatorStatementList) {
        this.DesignatorStatementList=DesignatorStatementList;
    }

    public DesignatorStatementListMemberOrNot getDesignatorStatementListMemberOrNot() {
        return DesignatorStatementListMemberOrNot;
    }

    public void setDesignatorStatementListMemberOrNot(DesignatorStatementListMemberOrNot DesignatorStatementListMemberOrNot) {
        this.DesignatorStatementListMemberOrNot=DesignatorStatementListMemberOrNot;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatementList!=null) DesignatorStatementList.accept(visitor);
        if(DesignatorStatementListMemberOrNot!=null) DesignatorStatementListMemberOrNot.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatementList!=null) DesignatorStatementList.traverseTopDown(visitor);
        if(DesignatorStatementListMemberOrNot!=null) DesignatorStatementListMemberOrNot.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatementList!=null) DesignatorStatementList.traverseBottomUp(visitor);
        if(DesignatorStatementListMemberOrNot!=null) DesignatorStatementListMemberOrNot.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementListMore(\n");

        if(DesignatorStatementList!=null)
            buffer.append(DesignatorStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementListMemberOrNot!=null)
            buffer.append(DesignatorStatementListMemberOrNot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementListMore]");
        return buffer.toString();
    }
}
