// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class CondFactMultiple extends CondFact {

    private CondFactExpr1 CondFactExpr1;
    private Relop Relop;
    private CondFactExpr2 CondFactExpr2;

    public CondFactMultiple (CondFactExpr1 CondFactExpr1, Relop Relop, CondFactExpr2 CondFactExpr2) {
        this.CondFactExpr1=CondFactExpr1;
        if(CondFactExpr1!=null) CondFactExpr1.setParent(this);
        this.Relop=Relop;
        if(Relop!=null) Relop.setParent(this);
        this.CondFactExpr2=CondFactExpr2;
        if(CondFactExpr2!=null) CondFactExpr2.setParent(this);
    }

    public CondFactExpr1 getCondFactExpr1() {
        return CondFactExpr1;
    }

    public void setCondFactExpr1(CondFactExpr1 CondFactExpr1) {
        this.CondFactExpr1=CondFactExpr1;
    }

    public Relop getRelop() {
        return Relop;
    }

    public void setRelop(Relop Relop) {
        this.Relop=Relop;
    }

    public CondFactExpr2 getCondFactExpr2() {
        return CondFactExpr2;
    }

    public void setCondFactExpr2(CondFactExpr2 CondFactExpr2) {
        this.CondFactExpr2=CondFactExpr2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondFactExpr1!=null) CondFactExpr1.accept(visitor);
        if(Relop!=null) Relop.accept(visitor);
        if(CondFactExpr2!=null) CondFactExpr2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondFactExpr1!=null) CondFactExpr1.traverseTopDown(visitor);
        if(Relop!=null) Relop.traverseTopDown(visitor);
        if(CondFactExpr2!=null) CondFactExpr2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondFactExpr1!=null) CondFactExpr1.traverseBottomUp(visitor);
        if(Relop!=null) Relop.traverseBottomUp(visitor);
        if(CondFactExpr2!=null) CondFactExpr2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFactMultiple(\n");

        if(CondFactExpr1!=null)
            buffer.append(CondFactExpr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Relop!=null)
            buffer.append(Relop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondFactExpr2!=null)
            buffer.append(CondFactExpr2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFactMultiple]");
        return buffer.toString();
    }
}
