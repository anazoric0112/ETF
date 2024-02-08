// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:48:15


package rs.ac.bg.etf.pp1.ast;

public class StaticDeclListMore extends StaticDeclList {

    private StaticDeclList StaticDeclList;
    private StaticWord StaticWord;
    private VarDecl VarDecl;

    public StaticDeclListMore (StaticDeclList StaticDeclList, StaticWord StaticWord, VarDecl VarDecl) {
        this.StaticDeclList=StaticDeclList;
        if(StaticDeclList!=null) StaticDeclList.setParent(this);
        this.StaticWord=StaticWord;
        if(StaticWord!=null) StaticWord.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public StaticDeclList getStaticDeclList() {
        return StaticDeclList;
    }

    public void setStaticDeclList(StaticDeclList StaticDeclList) {
        this.StaticDeclList=StaticDeclList;
    }

    public StaticWord getStaticWord() {
        return StaticWord;
    }

    public void setStaticWord(StaticWord StaticWord) {
        this.StaticWord=StaticWord;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StaticDeclList!=null) StaticDeclList.accept(visitor);
        if(StaticWord!=null) StaticWord.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StaticDeclList!=null) StaticDeclList.traverseTopDown(visitor);
        if(StaticWord!=null) StaticWord.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StaticDeclList!=null) StaticDeclList.traverseBottomUp(visitor);
        if(StaticWord!=null) StaticWord.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StaticDeclListMore(\n");

        if(StaticDeclList!=null)
            buffer.append(StaticDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StaticWord!=null)
            buffer.append(StaticWord.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StaticDeclListMore]");
        return buffer.toString();
    }
}
