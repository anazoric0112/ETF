package rs.ac.bg.etf.pp1.genutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class FactorGen extends BaseGenerator {

    public FactorGen (CodeGenCommon util, Logger log) {
        super(util, log);
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

        if (!util.invokevirtual && util.currentClassName.equals("")){
            int offset = functionObj.getAdr() - Code.pc;
            Code.put(Code.call);
            Code.put2(offset);
        } else {
            if (!util.currentClassName.equals("")){
                Code.put(Code.load_n);
                Code.put(Code.load_n);
            } else {
                traverseDesObj(factor.getDesignatorCall().getDesignator());
            }
            //Code.load(util.invObj);
            Code.put(Code.getfield);
            Code.put2(0);

            Code.put(Code.invokevirtual);
            String name=functionObj.getName();
            for (int i=0;i<name.length();i++){
                Code.put4(name.charAt(i));
            }
            Code.put4(-1);
            util.invokevirtual=false;
        }

    }

    public void visit (FactorNewAct factor){
        int fields=factor.getConstructorCall().getType().obj.getType().getNumberOfFields()*4;
        Code.put(Code.new_);
        Code.put2(fields);
        util.newClassMade=true;
        for (Obj o: factor.getConstructorCall().getType().obj.getType().getMembers()){
            if (o.getKind()!=Obj.Fld || !o.getName().contains("::TVF")) continue;
            util.tvfAdrForObj=o.getFpPos();
            break;
        }
    }

    public void visit (FactorNewExpr factor){
        Code.put(Code.newarray);
        if (factor.getType().obj.getType().getKind()!= Struct.Char ){
            Code.put(1);
        } else {
            Code.put(0);
        }

        util.newArrayMade=true;
    }
}