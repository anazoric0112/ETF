// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class StaticVarsYes extends StaticOrNot {

    private StaticVarDecl StaticVarDecl;
    private StaticVars StaticVars;
    private StaticInitializerOrNot StaticInitializerOrNot;

    public StaticVarsYes (StaticVarDecl StaticVarDecl, StaticVars StaticVars, StaticInitializerOrNot StaticInitializerOrNot) {
        this.StaticVarDecl=StaticVarDecl;
        if(StaticVarDecl!=null) StaticVarDecl.setParent(this);
        this.StaticVars=StaticVars;
        if(StaticVars!=null) StaticVars.setParent(this);
        this.StaticInitializerOrNot=StaticInitializerOrNot;
        if(StaticInitializerOrNot!=null) StaticInitializerOrNot.setParent(this);
    }

    public StaticVarDecl getStaticVarDecl() {
        return StaticVarDecl;
    }

    public void setStaticVarDecl(StaticVarDecl StaticVarDecl) {
        this.StaticVarDecl=StaticVarDecl;
    }

    public StaticVars getStaticVars() {
        return StaticVars;
    }

    public void setStaticVars(StaticVars StaticVars) {
        this.StaticVars=StaticVars;
    }

    public StaticInitializerOrNot getStaticInitializerOrNot() {
        return StaticInitializerOrNot;
    }

    public void setStaticInitializerOrNot(StaticInitializerOrNot StaticInitializerOrNot) {
        this.StaticInitializerOrNot=StaticInitializerOrNot;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StaticVarDecl!=null) StaticVarDecl.accept(visitor);
        if(StaticVars!=null) StaticVars.accept(visitor);
        if(StaticInitializerOrNot!=null) StaticInitializerOrNot.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StaticVarDecl!=null) StaticVarDecl.traverseTopDown(visitor);
        if(StaticVars!=null) StaticVars.traverseTopDown(visitor);
        if(StaticInitializerOrNot!=null) StaticInitializerOrNot.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StaticVarDecl!=null) StaticVarDecl.traverseBottomUp(visitor);
        if(StaticVars!=null) StaticVars.traverseBottomUp(visitor);
        if(StaticInitializerOrNot!=null) StaticInitializerOrNot.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StaticVarsYes(\n");

        if(StaticVarDecl!=null)
            buffer.append(StaticVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StaticVars!=null)
            buffer.append(StaticVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StaticInitializerOrNot!=null)
            buffer.append(StaticInitializerOrNot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StaticVarsYes]");
        return buffer.toString();
    }
}
