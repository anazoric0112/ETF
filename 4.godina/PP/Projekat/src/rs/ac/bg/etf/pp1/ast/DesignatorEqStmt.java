// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class DesignatorEqStmt extends DesignatorStatement {

    private DesignatorEqStmtStart DesignatorEqStmtStart;
    private DesignatorStatementList DesignatorStatementList;
    private Designator Designator;
    private DesignatorAssignedArray DesignatorAssignedArray;

    public DesignatorEqStmt (DesignatorEqStmtStart DesignatorEqStmtStart, DesignatorStatementList DesignatorStatementList, Designator Designator, DesignatorAssignedArray DesignatorAssignedArray) {
        this.DesignatorEqStmtStart=DesignatorEqStmtStart;
        if(DesignatorEqStmtStart!=null) DesignatorEqStmtStart.setParent(this);
        this.DesignatorStatementList=DesignatorStatementList;
        if(DesignatorStatementList!=null) DesignatorStatementList.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorAssignedArray=DesignatorAssignedArray;
        if(DesignatorAssignedArray!=null) DesignatorAssignedArray.setParent(this);
    }

    public DesignatorEqStmtStart getDesignatorEqStmtStart() {
        return DesignatorEqStmtStart;
    }

    public void setDesignatorEqStmtStart(DesignatorEqStmtStart DesignatorEqStmtStart) {
        this.DesignatorEqStmtStart=DesignatorEqStmtStart;
    }

    public DesignatorStatementList getDesignatorStatementList() {
        return DesignatorStatementList;
    }

    public void setDesignatorStatementList(DesignatorStatementList DesignatorStatementList) {
        this.DesignatorStatementList=DesignatorStatementList;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorAssignedArray getDesignatorAssignedArray() {
        return DesignatorAssignedArray;
    }

    public void setDesignatorAssignedArray(DesignatorAssignedArray DesignatorAssignedArray) {
        this.DesignatorAssignedArray=DesignatorAssignedArray;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorEqStmtStart!=null) DesignatorEqStmtStart.accept(visitor);
        if(DesignatorStatementList!=null) DesignatorStatementList.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorAssignedArray!=null) DesignatorAssignedArray.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorEqStmtStart!=null) DesignatorEqStmtStart.traverseTopDown(visitor);
        if(DesignatorStatementList!=null) DesignatorStatementList.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorAssignedArray!=null) DesignatorAssignedArray.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorEqStmtStart!=null) DesignatorEqStmtStart.traverseBottomUp(visitor);
        if(DesignatorStatementList!=null) DesignatorStatementList.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorAssignedArray!=null) DesignatorAssignedArray.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorEqStmt(\n");

        if(DesignatorEqStmtStart!=null)
            buffer.append(DesignatorEqStmtStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementList!=null)
            buffer.append(DesignatorStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorAssignedArray!=null)
            buffer.append(DesignatorAssignedArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorEqStmt]");
        return buffer.toString();
    }
}
