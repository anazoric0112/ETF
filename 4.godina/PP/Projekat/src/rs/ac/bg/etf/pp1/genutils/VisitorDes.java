package rs.ac.bg.etf.pp1.genutils;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class VisitorDes extends VisitorAdaptor {

    CodeGenCommon util;
    BaseGenerator gen;

    public VisitorDes(CodeGenCommon util, BaseGenerator gen) {
        this.util=util;
        this.gen=gen;
    }
    public void visit (DesignatorNamespace des){
        Code.load(des.obj);
    }

    public void visit (DesignatorNoNamespace des){
        Code.load(des.obj);
    }

    public void visit (DesignatorExprStmt des){
        if (des.obj.getType().getElemType().getKind() == Struct.Char) Code.put(Code.baload);
        else Code.put(Code.aload);
    }

    public void visit (ExprLastMinus e){
        Code.put(Code.neg);
    }

    public void visit (ExprMore e) {
        if (e.getAddop() instanceof AddopPlus) Code.put(Code.add);
        else if (e.getAddop() instanceof AddopMinus) Code.put(Code.sub);
    }

    public void visit (TermMore t){
        if (t.getMulop() instanceof MulopMul) Code.put(Code.mul);
        else if (t.getMulop() instanceof MulopDiv) Code.put(Code.div);
        else if (t.getMulop() instanceof MulopMod) Code.put(Code.rem);
    }

    public void visit (FactorNum factor){
        Code.loadConst(factor.getN1());
    }
    public void visit (FactorBool factor){
        if (factor.getB1()) Code.loadConst(1);
        else Code.loadConst(0);

    }
    public void visit (FactorChar factor){
        Code.loadConst(factor.getC1());
    }

    public void visit (FactorDesignatorAct factor){
        Obj functionObj = factor.getDesignatorCall().getDesignator().obj;

        int offset = functionObj.getAdr() - Code.pc;
        Code.put(Code.call);
        Code.put2(offset);
    }

    public void visit (FactorNewAct factor){
        int fields=factor.getConstructorCall().getType().obj.getType().getNumberOfFields();
        Code.put(Code.new_);
        Code.put2(fields);
        util.newClassMade=true;
    }

    public void visit (FactorNewExpr factor){
        Code.put(Code.newarray);
        if (factor.getType().obj.getType().getKind()== Struct.Int){
            Code.put(1);
        } else {
            Code.put(0);
        }
        util.newArrayMade=true;
    }

    public boolean checkDirect(SyntaxNode parent){
        if (parent instanceof DesignatorStatementListMemberYes
                || parent instanceof DesignatorStatementListMemberNot){
            return false;
        }
        return true;
    }

}