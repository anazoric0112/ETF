// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclMore extends ConstDeclList {

    private ConstDeclList ConstDeclList;
    private ConstName ConstName;
    private ConstVar ConstVar;

    public ConstDeclMore (ConstDeclList ConstDeclList, ConstName ConstName, ConstVar ConstVar) {
        this.ConstDeclList=ConstDeclList;
        if(ConstDeclList!=null) ConstDeclList.setParent(this);
        this.ConstName=ConstName;
        if(ConstName!=null) ConstName.setParent(this);
        this.ConstVar=ConstVar;
        if(ConstVar!=null) ConstVar.setParent(this);
    }

    public ConstDeclList getConstDeclList() {
        return ConstDeclList;
    }

    public void setConstDeclList(ConstDeclList ConstDeclList) {
        this.ConstDeclList=ConstDeclList;
    }

    public ConstName getConstName() {
        return ConstName;
    }

    public void setConstName(ConstName ConstName) {
        this.ConstName=ConstName;
    }

    public ConstVar getConstVar() {
        return ConstVar;
    }

    public void setConstVar(ConstVar ConstVar) {
        this.ConstVar=ConstVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclList!=null) ConstDeclList.accept(visitor);
        if(ConstName!=null) ConstName.accept(visitor);
        if(ConstVar!=null) ConstVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclList!=null) ConstDeclList.traverseTopDown(visitor);
        if(ConstName!=null) ConstName.traverseTopDown(visitor);
        if(ConstVar!=null) ConstVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclList!=null) ConstDeclList.traverseBottomUp(visitor);
        if(ConstName!=null) ConstName.traverseBottomUp(visitor);
        if(ConstVar!=null) ConstVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclMore(\n");

        if(ConstDeclList!=null)
            buffer.append(ConstDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstName!=null)
            buffer.append(ConstName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstVar!=null)
            buffer.append(ConstVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclMore]");
        return buffer.toString();
    }
}
