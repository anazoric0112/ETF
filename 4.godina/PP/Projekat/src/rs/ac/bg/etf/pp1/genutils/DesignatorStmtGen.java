package rs.ac.bg.etf.pp1.genutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;

public class DesignatorStmtGen extends BaseGenerator {

    public DesignatorStmtGen(CodeGenCommon util, Logger log) {
        super(util, log);
    }

    public void visit (DesignatorStmtAssign stmt){
        if (util.newClassMade){
            Code.store(stmt.getDesignator().obj);
            util.newClassMade=false;

            //namestanje adrese tabele virtuelnih funkcija
            Code.load(stmt.getDesignator().obj);
            Code.loadConst(util.tvfAdrForObj);
            Code.put(Code.putfield);
            Code.put2(0);
        }
        else if (util.newArrayMade){
            Code.store(stmt.getDesignator().obj);
            util.newArrayMade = false;
        } else if (isArray(stmt.getDesignator().obj)){
            if (stmt.getDesignator().obj.getType().getElemType().getKind() == Struct.Char) Code.put(Code.bastore);
            else Code.put(Code.astore);
        } else if (stmt.getDesignator().obj!=Tab.noObj){
            Code.store(stmt.getDesignator().obj);
        }
    }

    public void visit (DesignatorStmtDec stmt){
        Code.loadConst(1);
        Code.put(Code.sub);
        if (isArray(stmt.getDesignator().obj)){
            if (stmt.getDesignator().obj.getType().getElemType().getKind() == Struct.Char) Code.put(Code.bastore);
            else Code.put(Code.astore);
        } else {
            Code.store(stmt.getDesignator().obj);
        }
    }

    public void visit (DesignatorStmtInc stmt){
        Code.loadConst(1);
        Code.put(Code.add);
        if (isArray(stmt.getDesignator().obj)){
            if (stmt.getDesignator().obj.getType().getElemType().getKind() == Struct.Char) Code.put(Code.bastore);
            else Code.put(Code.astore);
        } else {
            Code.store(stmt.getDesignator().obj);
        }
    }

    public void visit (DesignatorStmtAct stmt){
        Obj functionObj = stmt.getDesignatorCall().getDesignator().obj;


        if (!util.invokevirtual && util.currentClassName.equals("")){
            int offset = functionObj.getAdr() - Code.pc;
            Code.put(Code.call);
            Code.put2(offset);
        } else {
            if (!util.currentClassName.equals("")){
                Code.put(Code.load_n);
                Code.put(Code.load_n);
            } else {
                traverseDesObj(stmt.getDesignatorCall().getDesignator());
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

        if(functionObj.getType() != Tab.noType){
            Code.put(Code.pop);
        }
    }

    public void visit (DesignatorEqStmt stmt){

        Code.loadConst(util.desObjects.size());
        Code.load(stmt.getDesignator().obj);
        Code.put(Code.arraylength);
        Code.put(Code.add);
        Code.load(stmt.getDesignatorAssignedArray().obj);
        Code.put(Code.arraylength);
        int adrToFix=Code.pc+1;
        Code.putFalseJump(Code.gt, 0);

        Code.put(Code.trap);
        Code.fixup(adrToFix);

        loadDesRightArray(stmt.getDesignatorAssignedArray().obj, stmt.getDesignator().obj);
        loadLeftArray(stmt.getDesignator().obj);

        VisitorDes v=new VisitorDes(util,this);

        for (int i=util.desObjects.size()-1; i>=0; i--){
            if (util.desObjects.get(i)!=null){

                if (util.syntaxNodes.get(i) instanceof DesignatorExprStmt){
                    //da adresa niza dodje pre val
                    Code.load(util.desObjects.get(i));
                    Code.put(Code.dup_x1);
                    Code.put(Code.pop);

                    //da indeks dodje pre val
                    ((DesignatorExprStmt)(util.syntaxNodes.get(i))).getExpr().traverseBottomUp(v);
                    Code.put(Code.dup_x1);
                    Code.put(Code.pop);

                    if (util.desObjects.get(i).getType().getElemType().getKind()==Struct.Int) Code.put(Code.astore);
                    else Code.put(Code.bastore);
                } else
                    Code.store(util.desObjects.get(i));
            } else {
                Code.put(Code.pop);
            }
        }
        util.desObjects.clear();
        util.syntaxNodes.clear();
        util.inDesEq=false;
    }

    public void visit (DesignatorEqStmtStart stmt){
        util.inDesEq=true;
    }
    public void visit (DesignatorStatementListMemberYes d){
        util.syntaxNodes.add(d.getDesignator());
        util.desObjects.add(d.getDesignator().obj);
    }
    public void visit (DesignatorStatementListMemberNot d){
        util.syntaxNodes.add(null);
        util.desObjects.add(null);
    }

    public void loadDesRightArray(Obj desArray, Obj desLeft){
        //stavljanje broja elemenata kolko ih treba
        Code.loadConst(util.desObjects.size());
        Code.load(desLeft);
        Code.put(Code.arraylength);
        Code.put(Code.add);

        Code.loadConst(0);
        int cmpadr=Code.pc;
        Code.put(Code.dup2);
        int falseAdr=Code.pc+1;
        Code.putFalseJump(Code.gt, 0);
        Code.put(Code.dup);

        Code.load(desArray);
        Code.put(Code.dup_x1);
        Code.put(Code.pop);

        if (desArray.getType().getKind()==Struct.Char){
            Code.put(Code.baload);
        } else {
            Code.put(Code.aload);
        }
        Code.put(Code.dup_x2);
        Code.put(Code.pop);

        Code.loadConst(1);
        Code.put(Code.add);

        Code.putJump(cmpadr);

        Code.fixup(falseAdr);
        Code.put(Code.pop);
        Code.put(Code.pop);
    }

    public void loadLeftArray(Obj desArray){

        Code.load(desArray);
        Code.put(Code.arraylength);

        int cmpadr=Code.pc;
        Code.loadConst(1);
        Code.put(Code.sub); //0 len-1
        Code.put(Code.dup);
        Code.loadConst((0));

        int falseAdr=Code.pc+1;
        Code.putFalseJump(Code.ge, 0);

        Code.put(Code.dup_x1);
        Code.load(desArray);
        Code.put(Code.dup_x2);
        Code.put(Code.pop);
        Code.put(Code.dup_x1);
        Code.put(Code.pop);

        if (desArray.getType().getKind()==Struct.Char){
            Code.put(Code.bastore);
        } else {
            Code.put(Code.astore);
        }
        Code.putJump(cmpadr);

        Code.fixup(falseAdr);
        Code.put(Code.pop);

    }

}
