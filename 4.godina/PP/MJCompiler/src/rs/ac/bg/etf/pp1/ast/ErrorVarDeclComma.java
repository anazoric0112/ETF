// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class ErrorVarDeclComma extends VarDecl {

    private ErrorSign ErrorSign;

    public ErrorVarDeclComma (ErrorSign ErrorSign) {
        this.ErrorSign=ErrorSign;
        if(ErrorSign!=null) ErrorSign.setParent(this);
    }

    public ErrorSign getErrorSign() {
        return ErrorSign;
    }

    public void setErrorSign(ErrorSign ErrorSign) {
        this.ErrorSign=ErrorSign;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ErrorSign!=null) ErrorSign.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ErrorSign!=null) ErrorSign.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ErrorSign!=null) ErrorSign.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ErrorVarDeclComma(\n");

        if(ErrorSign!=null)
            buffer.append(ErrorSign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ErrorVarDeclComma]");
        return buffer.toString();
    }
}
