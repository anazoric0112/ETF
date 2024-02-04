// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class ForInitNotEmpty extends ForInit {

    private ForDesignatorStatementList ForDesignatorStatementList;

    public ForInitNotEmpty (ForDesignatorStatementList ForDesignatorStatementList) {
        this.ForDesignatorStatementList=ForDesignatorStatementList;
        if(ForDesignatorStatementList!=null) ForDesignatorStatementList.setParent(this);
    }

    public ForDesignatorStatementList getForDesignatorStatementList() {
        return ForDesignatorStatementList;
    }

    public void setForDesignatorStatementList(ForDesignatorStatementList ForDesignatorStatementList) {
        this.ForDesignatorStatementList=ForDesignatorStatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForDesignatorStatementList!=null) ForDesignatorStatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForDesignatorStatementList!=null) ForDesignatorStatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForDesignatorStatementList!=null) ForDesignatorStatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForInitNotEmpty(\n");

        if(ForDesignatorStatementList!=null)
            buffer.append(ForDesignatorStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForInitNotEmpty]");
        return buffer.toString();
    }
}
