// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class FormParsNot extends FormParsOrNot {

    public FormParsNot () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsNot(\n");

        buffer.append(tab);
        buffer.append(") [FormParsNot]");
        return buffer.toString();
    }
}
