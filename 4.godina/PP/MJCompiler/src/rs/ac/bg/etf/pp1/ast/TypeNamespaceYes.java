// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class TypeNamespaceYes extends Type {

    private TypeNamespace TypeNamespace;
    private TypeName TypeName;

    public TypeNamespaceYes (TypeNamespace TypeNamespace, TypeName TypeName) {
        this.TypeNamespace=TypeNamespace;
        if(TypeNamespace!=null) TypeNamespace.setParent(this);
        this.TypeName=TypeName;
        if(TypeName!=null) TypeName.setParent(this);
    }

    public TypeNamespace getTypeNamespace() {
        return TypeNamespace;
    }

    public void setTypeNamespace(TypeNamespace TypeNamespace) {
        this.TypeNamespace=TypeNamespace;
    }

    public TypeName getTypeName() {
        return TypeName;
    }

    public void setTypeName(TypeName TypeName) {
        this.TypeName=TypeName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TypeNamespace!=null) TypeNamespace.accept(visitor);
        if(TypeName!=null) TypeName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TypeNamespace!=null) TypeNamespace.traverseTopDown(visitor);
        if(TypeName!=null) TypeName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TypeNamespace!=null) TypeNamespace.traverseBottomUp(visitor);
        if(TypeName!=null) TypeName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TypeNamespaceYes(\n");

        if(TypeNamespace!=null)
            buffer.append(TypeNamespace.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TypeName!=null)
            buffer.append(TypeName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypeNamespaceYes]");
        return buffer.toString();
    }
}
