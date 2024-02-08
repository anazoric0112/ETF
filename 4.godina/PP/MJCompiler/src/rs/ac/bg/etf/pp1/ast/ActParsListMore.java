// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class ActParsListMore extends ActParsList {

    private ActParsList ActParsList;
    private ActParSingle ActParSingle;

    public ActParsListMore (ActParsList ActParsList, ActParSingle ActParSingle) {
        this.ActParsList=ActParsList;
        if(ActParsList!=null) ActParsList.setParent(this);
        this.ActParSingle=ActParSingle;
        if(ActParSingle!=null) ActParSingle.setParent(this);
    }

    public ActParsList getActParsList() {
        return ActParsList;
    }

    public void setActParsList(ActParsList ActParsList) {
        this.ActParsList=ActParsList;
    }

    public ActParSingle getActParSingle() {
        return ActParSingle;
    }

    public void setActParSingle(ActParSingle ActParSingle) {
        this.ActParSingle=ActParSingle;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParsList!=null) ActParsList.accept(visitor);
        if(ActParSingle!=null) ActParSingle.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParsList!=null) ActParsList.traverseTopDown(visitor);
        if(ActParSingle!=null) ActParSingle.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParsList!=null) ActParsList.traverseBottomUp(visitor);
        if(ActParSingle!=null) ActParSingle.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParsListMore(\n");

        if(ActParsList!=null)
            buffer.append(ActParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParSingle!=null)
            buffer.append(ActParSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParsListMore]");
        return buffer.toString();
    }
}
