// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class FactorNewAct extends Factor {

    private ConstructorCall ConstructorCall;
    private ActPars ActPars;

    public FactorNewAct (ConstructorCall ConstructorCall, ActPars ActPars) {
        this.ConstructorCall=ConstructorCall;
        if(ConstructorCall!=null) ConstructorCall.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public ConstructorCall getConstructorCall() {
        return ConstructorCall;
    }

    public void setConstructorCall(ConstructorCall ConstructorCall) {
        this.ConstructorCall=ConstructorCall;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstructorCall!=null) ConstructorCall.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorCall!=null) ConstructorCall.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorCall!=null) ConstructorCall.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorNewAct(\n");

        if(ConstructorCall!=null)
            buffer.append(ConstructorCall.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorNewAct]");
        return buffer.toString();
    }
}
