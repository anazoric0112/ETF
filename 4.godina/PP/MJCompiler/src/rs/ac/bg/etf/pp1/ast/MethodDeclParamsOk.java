// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclParamsOk extends MethodDeclParams {

    private MethodType MethodType;
    private MethodName MethodName;
    private FormParsOrNot FormParsOrNot;

    public MethodDeclParamsOk (MethodType MethodType, MethodName MethodName, FormParsOrNot FormParsOrNot) {
        this.MethodType=MethodType;
        if(MethodType!=null) MethodType.setParent(this);
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.FormParsOrNot=FormParsOrNot;
        if(FormParsOrNot!=null) FormParsOrNot.setParent(this);
    }

    public MethodType getMethodType() {
        return MethodType;
    }

    public void setMethodType(MethodType MethodType) {
        this.MethodType=MethodType;
    }

    public MethodName getMethodName() {
        return MethodName;
    }

    public void setMethodName(MethodName MethodName) {
        this.MethodName=MethodName;
    }

    public FormParsOrNot getFormParsOrNot() {
        return FormParsOrNot;
    }

    public void setFormParsOrNot(FormParsOrNot FormParsOrNot) {
        this.FormParsOrNot=FormParsOrNot;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodType!=null) MethodType.accept(visitor);
        if(MethodName!=null) MethodName.accept(visitor);
        if(FormParsOrNot!=null) FormParsOrNot.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodType!=null) MethodType.traverseTopDown(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(FormParsOrNot!=null) FormParsOrNot.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodType!=null) MethodType.traverseBottomUp(visitor);
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(FormParsOrNot!=null) FormParsOrNot.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclParamsOk(\n");

        if(MethodType!=null)
            buffer.append(MethodType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodName!=null)
            buffer.append(MethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsOrNot!=null)
            buffer.append(FormParsOrNot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclParamsOk]");
        return buffer.toString();
    }
}
