// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class ErrorSignComma extends ErrorSign {

    public ErrorSignComma () {
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
        buffer.append("ErrorSignComma(\n");

        buffer.append(tab);
        buffer.append(") [ErrorSignComma]");
        return buffer.toString();
    }
}
