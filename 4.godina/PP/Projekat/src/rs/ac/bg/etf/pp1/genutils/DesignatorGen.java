package rs.ac.bg.etf.pp1.genutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class DesignatorGen extends BaseGenerator {

    public DesignatorGen(CodeGenCommon util, Logger log) {
        super(util, log);
    }


    public void visit (DesignatorNamespace des){
        util.desIdent=false;
        util.lastArrayStmt=false;
        if (des.obj.getFpPos()==-1){
            String desName= util.currentClassName+".$staticf."+des.obj.getName();
            des.obj=findObjByName(desName);
        } else if (!util.currentClassName.equals("") && des.obj.getKind()==Obj.Fld){
            Code.put(Code.load_n);
        }
        if (!checkParent(des.getParent())) return;

        Code.load(des.obj);
    }

    public void visit (DesignatorNoNamespace des){
        util.desIdent=false;
        util.lastArrayStmt=false;
        if (des.obj.getFpPos()==-1){
            String desName= util.currentClassName+".$staticf."+des.obj.getName();
            des.obj=findObjByName(desName);
        }
        if (!checkParent(des.getParent())) return;

        if (des.obj==Tab.noObj){ //ovo znaci da je ime klase dok smo jos u klasi
            des.obj=findObjByName(des.getDesignatorName().getDesignatorName());
            Code.load(des.obj);
        }else if (!util.currentClassName.equals("") && des.obj.getKind()==Obj.Fld){
            Code.put(Code.load_n);
            Code.load(des.obj);
        } else {
            Code.load(des.obj);
        }

    }

    public void visit (DesignatorExprStmt des){
        util.desIdent=false;
        util.lastArrayStmt=true;
        if (!checkParent(des.getParent())) return;

        if (des.getParent() instanceof DesignatorStmtDec
            || des.getParent() instanceof DesignatorStmtInc){
            Code.put(Code.dup2);
        }

        if (des.obj.getType().getElemType().getKind() == Struct.Char) Code.put(Code.baload);
        else Code.put(Code.aload);
    }

    public void visit (DesignatorIdentStmt des){
        util.lastArrayStmt=false;
        if (des.obj.getFpPos()==-1){
            String classname = util.currentClassName;
            if (classname.equals("")) classname=des.getDesignator().obj.getName();
            String desName= classname+".$staticf."+des.obj.getName();
            des.obj=findObjByName(desName);
        }
        if (des.getDesignatorIdent().obj.getKind()==Obj.Meth){
            util.invokevirtual=true;
            util.invObj=des.getDesignator().obj;
        }
        util.desIdent=true;
        util.lastDesIdent=des.obj;
        if (!checkParent(des.getParent())) return;

        Code.load(des.obj);
    }

    public boolean checkParent(SyntaxNode parent){
        if (parent instanceof DesignatorCall ||
                parent instanceof DesignatorStmtAssign ||
                parent instanceof DesignatorStatementListMemberYes ||
                parent instanceof StatementRead ||
                parent instanceof DesignatorEqStmt ||
                util.inDesEq){
            return false;
        }
        return true;
    }


}
