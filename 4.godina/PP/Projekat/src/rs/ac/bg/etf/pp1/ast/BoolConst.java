// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class BoolConst extends ConstVar {

    private Boolean name;

    public BoolConst (Boolean name) {
        this.name=name;
    }

    public Boolean getName() {
        return name;
    }

    public void setName(Boolean name) {
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
        buffer.append("BoolConst(\n");

        buffer.append(" "+tab+name);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BoolConst]");
        return buffer.toString();
    }
}
