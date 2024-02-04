// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class NamespaceListMore extends NamespaceList {

    private NamespaceList NamespaceList;
    private NamespaceSingle NamespaceSingle;

    public NamespaceListMore (NamespaceList NamespaceList, NamespaceSingle NamespaceSingle) {
        this.NamespaceList=NamespaceList;
        if(NamespaceList!=null) NamespaceList.setParent(this);
        this.NamespaceSingle=NamespaceSingle;
        if(NamespaceSingle!=null) NamespaceSingle.setParent(this);
    }

    public NamespaceList getNamespaceList() {
        return NamespaceList;
    }

    public void setNamespaceList(NamespaceList NamespaceList) {
        this.NamespaceList=NamespaceList;
    }

    public NamespaceSingle getNamespaceSingle() {
        return NamespaceSingle;
    }

    public void setNamespaceSingle(NamespaceSingle NamespaceSingle) {
        this.NamespaceSingle=NamespaceSingle;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NamespaceList!=null) NamespaceList.accept(visitor);
        if(NamespaceSingle!=null) NamespaceSingle.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NamespaceList!=null) NamespaceList.traverseTopDown(visitor);
        if(NamespaceSingle!=null) NamespaceSingle.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NamespaceList!=null) NamespaceList.traverseBottomUp(visitor);
        if(NamespaceSingle!=null) NamespaceSingle.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NamespaceListMore(\n");

        if(NamespaceList!=null)
            buffer.append(NamespaceList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NamespaceSingle!=null)
            buffer.append(NamespaceSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NamespaceListMore]");
        return buffer.toString();
    }
}
