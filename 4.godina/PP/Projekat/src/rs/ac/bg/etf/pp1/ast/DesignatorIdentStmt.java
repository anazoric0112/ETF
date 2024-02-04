// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class DesignatorIdentStmt extends Designator {

    private Designator Designator;
    private DesignatorIdent DesignatorIdent;

    public DesignatorIdentStmt (Designator Designator, DesignatorIdent DesignatorIdent) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorIdent=DesignatorIdent;
        if(DesignatorIdent!=null) DesignatorIdent.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorIdent getDesignatorIdent() {
        return DesignatorIdent;
    }

    public void setDesignatorIdent(DesignatorIdent DesignatorIdent) {
        this.DesignatorIdent=DesignatorIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorIdent!=null) DesignatorIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorIdent!=null) DesignatorIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorIdent!=null) DesignatorIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorIdentStmt(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorIdent!=null)
            buffer.append(DesignatorIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorIdentStmt]");
        return buffer.toString();
    }
}
