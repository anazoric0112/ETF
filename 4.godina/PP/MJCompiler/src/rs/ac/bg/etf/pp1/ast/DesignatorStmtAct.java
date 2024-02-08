// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStmtAct extends DesignatorStatement {

    private DesignatorCall DesignatorCall;
    private ActPars ActPars;

    public DesignatorStmtAct (DesignatorCall DesignatorCall, ActPars ActPars) {
        this.DesignatorCall=DesignatorCall;
        if(DesignatorCall!=null) DesignatorCall.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public DesignatorCall getDesignatorCall() {
        return DesignatorCall;
    }

    public void setDesignatorCall(DesignatorCall DesignatorCall) {
        this.DesignatorCall=DesignatorCall;
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
        if(DesignatorCall!=null) DesignatorCall.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorCall!=null) DesignatorCall.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorCall!=null) DesignatorCall.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStmtAct(\n");

        if(DesignatorCall!=null)
            buffer.append(DesignatorCall.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStmtAct]");
        return buffer.toString();
    }
}
