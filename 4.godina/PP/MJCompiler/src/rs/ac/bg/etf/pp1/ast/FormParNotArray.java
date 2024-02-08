// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class FormParNotArray extends FormPar {

    private FormParType FormParType;
    private FormParName FormParName;

    public FormParNotArray (FormParType FormParType, FormParName FormParName) {
        this.FormParType=FormParType;
        if(FormParType!=null) FormParType.setParent(this);
        this.FormParName=FormParName;
        if(FormParName!=null) FormParName.setParent(this);
    }

    public FormParType getFormParType() {
        return FormParType;
    }

    public void setFormParType(FormParType FormParType) {
        this.FormParType=FormParType;
    }

    public FormParName getFormParName() {
        return FormParName;
    }

    public void setFormParName(FormParName FormParName) {
        this.FormParName=FormParName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParType!=null) FormParType.accept(visitor);
        if(FormParName!=null) FormParName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParType!=null) FormParType.traverseTopDown(visitor);
        if(FormParName!=null) FormParName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParType!=null) FormParType.traverseBottomUp(visitor);
        if(FormParName!=null) FormParName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParNotArray(\n");

        if(FormParType!=null)
            buffer.append(FormParType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParName!=null)
            buffer.append(FormParName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParNotArray]");
        return buffer.toString();
    }
}
