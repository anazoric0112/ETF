// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class NumConst extends ConstVar {

    private Integer name;

    public NumConst (Integer name) {
        this.name=name;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name=name;
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
        buffer.append("NumConst(\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumConst]");
        return buffer.toString();
    }
}
