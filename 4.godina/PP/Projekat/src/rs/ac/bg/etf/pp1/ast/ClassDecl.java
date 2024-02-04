// generated with ast extension for cup
// version 0.8
// 4/1/2024 21:11:0


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private ClassName ClassName;
    private ExtendsOrNot ExtendsOrNot;
    private StaticOrNot StaticOrNot;
    private VarDeclOrNot VarDeclOrNot;
    private MethodDeclListClass MethodDeclListClass;

    public ClassDecl (ClassName ClassName, ExtendsOrNot ExtendsOrNot, StaticOrNot StaticOrNot, VarDeclOrNot VarDeclOrNot, MethodDeclListClass MethodDeclListClass) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.ExtendsOrNot=ExtendsOrNot;
        if(ExtendsOrNot!=null) ExtendsOrNot.setParent(this);
        this.StaticOrNot=StaticOrNot;
        if(StaticOrNot!=null) StaticOrNot.setParent(this);
        this.VarDeclOrNot=VarDeclOrNot;
        if(VarDeclOrNot!=null) VarDeclOrNot.setParent(this);
        this.MethodDeclListClass=MethodDeclListClass;
        if(MethodDeclListClass!=null) MethodDeclListClass.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
    }

    public ExtendsOrNot getExtendsOrNot() {
        return ExtendsOrNot;
    }

    public void setExtendsOrNot(ExtendsOrNot ExtendsOrNot) {
        this.ExtendsOrNot=ExtendsOrNot;
    }

    public StaticOrNot getStaticOrNot() {
        return StaticOrNot;
    }

    public void setStaticOrNot(StaticOrNot StaticOrNot) {
        this.StaticOrNot=StaticOrNot;
    }

    public VarDeclOrNot getVarDeclOrNot() {
        return VarDeclOrNot;
    }

    public void setVarDeclOrNot(VarDeclOrNot VarDeclOrNot) {
        this.VarDeclOrNot=VarDeclOrNot;
    }

    public MethodDeclListClass getMethodDeclListClass() {
        return MethodDeclListClass;
    }

    public void setMethodDeclListClass(MethodDeclListClass MethodDeclListClass) {
        this.MethodDeclListClass=MethodDeclListClass;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassName!=null) ClassName.accept(visitor);
        if(ExtendsOrNot!=null) ExtendsOrNot.accept(visitor);
        if(StaticOrNot!=null) StaticOrNot.accept(visitor);
        if(VarDeclOrNot!=null) VarDeclOrNot.accept(visitor);
        if(MethodDeclListClass!=null) MethodDeclListClass.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(ExtendsOrNot!=null) ExtendsOrNot.traverseTopDown(visitor);
        if(StaticOrNot!=null) StaticOrNot.traverseTopDown(visitor);
        if(VarDeclOrNot!=null) VarDeclOrNot.traverseTopDown(visitor);
        if(MethodDeclListClass!=null) MethodDeclListClass.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(ExtendsOrNot!=null) ExtendsOrNot.traverseBottomUp(visitor);
        if(StaticOrNot!=null) StaticOrNot.traverseBottomUp(visitor);
        if(VarDeclOrNot!=null) VarDeclOrNot.traverseBottomUp(visitor);
        if(MethodDeclListClass!=null) MethodDeclListClass.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExtendsOrNot!=null)
            buffer.append(ExtendsOrNot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StaticOrNot!=null)
            buffer.append(StaticOrNot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclOrNot!=null)
            buffer.append(VarDeclOrNot.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclListClass!=null)
            buffer.append(MethodDeclListClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
