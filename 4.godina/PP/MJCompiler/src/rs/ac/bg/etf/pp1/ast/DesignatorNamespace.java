// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class DesignatorNamespace extends Designator {

    private DesignatorNamespaceName DesignatorNamespaceName;
    private DesignatorName DesignatorName;

    public DesignatorNamespace (DesignatorNamespaceName DesignatorNamespaceName, DesignatorName DesignatorName) {
        this.DesignatorNamespaceName=DesignatorNamespaceName;
        if(DesignatorNamespaceName!=null) DesignatorNamespaceName.setParent(this);
        this.DesignatorName=DesignatorName;
        if(DesignatorName!=null) DesignatorName.setParent(this);
    }

    public DesignatorNamespaceName getDesignatorNamespaceName() {
        return DesignatorNamespaceName;
    }

    public void setDesignatorNamespaceName(DesignatorNamespaceName DesignatorNamespaceName) {
        this.DesignatorNamespaceName=DesignatorNamespaceName;
    }

    public DesignatorName getDesignatorName() {
        return DesignatorName;
    }

    public void setDesignatorName(DesignatorName DesignatorName) {
        this.DesignatorName=DesignatorName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorNamespaceName!=null) DesignatorNamespaceName.accept(visitor);
        if(DesignatorName!=null) DesignatorName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorNamespaceName!=null) DesignatorNamespaceName.traverseTopDown(visitor);
        if(DesignatorName!=null) DesignatorName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorNamespaceName!=null) DesignatorNamespaceName.traverseBottomUp(visitor);
        if(DesignatorName!=null) DesignatorName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorNamespace(\n");

        if(DesignatorNamespaceName!=null)
            buffer.append(DesignatorNamespaceName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorName!=null)
            buffer.append(DesignatorName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorNamespace]");
        return buffer.toString();
    }
}
