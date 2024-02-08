package rs.ac.bg.etf.pp1.genutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class MethodGen extends BaseGenerator{

    public MethodGen(CodeGenCommon util, Logger log) {
        super(util, log);
    }

    public void visit(MethodDecl method){

        if (util.currentMethodDecl.getType()!=Tab.noType && !util.returnFound){
            Code.error("Metoda "+method.obj.getName()+" nema return iskaz.");
            Code.put(Code.trap);
        }
        if (util.currentMethodDecl.getType()==Tab.noType && util.lastReturnAdr!=Code.pc){
            Code.put(Code.exit);
            Code.put(Code.return_);
        }
        util.inMethodDecl=false;
        util.currentMethodDecl=Tab.noObj;
        util.methodVarsCnt=0;
        util.methodFormParsCnt=0;
    }

    public void visit(MethodName method){
        if("main".equalsIgnoreCase(method.getName())){
            if (util.mainPc==-1) {
                util.mainPc = Code.pc;
            } else {
                Code.fixup(util.lastPatchAdr);
            }
        }
        method.obj.setAdr(Code.pc);
        util.inMethodDecl = true;
        util.currentMethodDecl = method.obj;
        util.methodVarsCnt=0;
        util.methodFormParsCnt=0;
        util.returnFound=false;

    }

    public void visit (FormParArray formPar){
        util.methodFormParsCnt++;
    }
    public void visit (FormParNotArray formPar){
        util.methodFormParsCnt++;
    }

    public void visit (MethodBegin fp) {
        Code.put(Code.enter);

        if (util.currentClassName.equals("")){
            Code.put(util.methodFormParsCnt);
            Code.put(util.methodFormParsCnt + util.methodVarsCnt);
        }else{
            Code.put(util.methodFormParsCnt+1);
            Code.put(util.methodFormParsCnt+1 + util.methodVarsCnt);
        }
    }

    public void visit (VarDeclSingleArray decl){
        if (util.inMethodDecl)
            util.methodVarsCnt++;
    }
    public void visit (VarDeclSingleNotArray decl){
        if (util.inMethodDecl)
            util.methodVarsCnt++;
    }

    public void visit(StatementReturn stmt){
        if (util.currentMethodDecl.getType()!=Tab.noType){
            Code.error("Metoda "+ util.currentMethodDecl.getName()+" treba da vrati vrednost.");
            Code.put(Code.trap);
        }
        Code.put(Code.exit);
        Code.put(Code.return_);
        util.lastReturnAdr=Code.pc;
    }

    public void visit(StatementReturnExpr stmt){
        if (util.currentMethodDecl.getType()==Tab.noType){
            Code.error("Metoda "+ util.currentMethodDecl.getName()+" ne treba da vrati vrednost.");
            Code.put(Code.trap);
        } else {
            util.returnFound = true;
        }
        Code.put(Code.exit);
        Code.put(Code.return_);
        util.lastReturnAdr=Code.pc;
    }
}
