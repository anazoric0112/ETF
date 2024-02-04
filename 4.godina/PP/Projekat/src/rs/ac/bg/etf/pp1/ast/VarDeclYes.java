// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class VarDeclYes extends VarDeclOrNot {

    private VarDeclOrNot VarDeclOrNot;
    private VarDecl VarDecl;

    public VarDeclYes (VarDeclOrNot VarDeclOrNot, VarDecl VarDecl) {
        this.VarDeclOrNot=VarDeclOrNot;
        if(VarDeclOrNot!=null) VarDeclOrNot.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public VarDeclOrNot getVarDeclOrNot() {
        return VarDeclOrNot;
    }

    public void setVarDeclOrNot(VarDeclOrNot VarDeclOrNot) {
        this.VarDeclOrNot=VarDeclOrNot;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclOrNot!=null) VarDeclOrNot.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclOrNot!=null) VarDeclOrNot.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclOrNot!=null) VarDeclOrNot.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclYes(\n");

        if(VarDeclOrNot!=null)
            buffer.append(VarDeclOrNot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclYes]");
        return buffer.toString();
    }
}
