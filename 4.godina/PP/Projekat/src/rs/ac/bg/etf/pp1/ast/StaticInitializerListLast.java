// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class StaticInitializerListLast extends StaticInitializerList {

    private StaticInitializer StaticInitializer;

    public StaticInitializerListLast (StaticInitializer StaticInitializer) {
        this.StaticInitializer=StaticInitializer;
        if(StaticInitializer!=null) StaticInitializer.setParent(this);
    }

    public StaticInitializer getStaticInitializer() {
        return StaticInitializer;
    }

    public void setStaticInitializer(StaticInitializer StaticInitializer) {
        this.StaticInitializer=StaticInitializer;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StaticInitializer!=null) StaticInitializer.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StaticInitializer!=null) StaticInitializer.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StaticInitializer!=null) StaticInitializer.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StaticInitializerListLast(\n");

        if(StaticInitializer!=null)
            buffer.append(StaticInitializer.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StaticInitializerListLast]");
        return buffer.toString();
    }
}
