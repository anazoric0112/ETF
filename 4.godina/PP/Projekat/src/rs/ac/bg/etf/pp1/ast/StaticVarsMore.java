// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class StaticVarsMore extends StaticVars {

    private StaticVars StaticVars;
    private StaticVarDecl StaticVarDecl;

    public StaticVarsMore (StaticVars StaticVars, StaticVarDecl StaticVarDecl) {
        this.StaticVars=StaticVars;
        if(StaticVars!=null) StaticVars.setParent(this);
        this.StaticVarDecl=StaticVarDecl;
        if(StaticVarDecl!=null) StaticVarDecl.setParent(this);
    }

    public StaticVars getStaticVars() {
        return StaticVars;
    }

    public void setStaticVars(StaticVars StaticVars) {
        this.StaticVars=StaticVars;
    }

    public StaticVarDecl getStaticVarDecl() {
        return StaticVarDecl;
    }

    public void setStaticVarDecl(StaticVarDecl StaticVarDecl) {
        this.StaticVarDecl=StaticVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StaticVars!=null) StaticVars.accept(visitor);
        if(StaticVarDecl!=null) StaticVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StaticVars!=null) StaticVars.traverseTopDown(visitor);
        if(StaticVarDecl!=null) StaticVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StaticVars!=null) StaticVars.traverseBottomUp(visitor);
        if(StaticVarDecl!=null) StaticVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StaticVarsMore(\n");

        if(StaticVars!=null)
            buffer.append(StaticVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StaticVarDecl!=null)
            buffer.append(StaticVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StaticVarsMore]");
        return buffer.toString();
    }
}
