// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class StaticInitializerYes extends StaticInitializerOrNot {

    private StaticInitializerList StaticInitializerList;

    public StaticInitializerYes (StaticInitializerList StaticInitializerList) {
        this.StaticInitializerList=StaticInitializerList;
        if(StaticInitializerList!=null) StaticInitializerList.setParent(this);
    }

    public StaticInitializerList getStaticInitializerList() {
        return StaticInitializerList;
    }

    public void setStaticInitializerList(StaticInitializerList StaticInitializerList) {
        this.StaticInitializerList=StaticInitializerList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StaticInitializerList!=null) StaticInitializerList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StaticInitializerList!=null) StaticInitializerList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StaticInitializerList!=null) StaticInitializerList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StaticInitializerYes(\n");

        if(StaticInitializerList!=null)
            buffer.append(StaticInitializerList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StaticInitializerYes]");
        return buffer.toString();
    }
}
