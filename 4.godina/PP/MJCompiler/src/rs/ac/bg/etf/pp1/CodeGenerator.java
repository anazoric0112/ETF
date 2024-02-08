package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.genutils.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;


public class CodeGenerator extends VisitorAdaptor {


    CodeGenCommon util = new CodeGenCommon();
    Logger log = org.apache.log4j.Logger.getLogger(getClass());

    ClassGen classGen;
    ConditionGen condGen;
    DesignatorGen desGen;
    DesignatorStmtGen desStmtGen;
    FactorGen factorGen;
    MethodGen methodGen;
    StatementGen stmtGen;

    public int getMainPc(){
        return util.mainPc;
    }

    CodeGenerator(){
        classGen=new ClassGen(util, log);
        condGen=new ConditionGen(util, log);
        desGen=new DesignatorGen(util, log);
        desStmtGen=new DesignatorStmtGen(util, log);
        factorGen=new FactorGen(util, log);
        methodGen=new MethodGen(util, log);
        stmtGen=new StatementGen(util,log);
    }

    // Class ---------------------
    public void visit(StaticInitializerStart st){
        classGen.visit(st);
    }
    public void visit(StaticInitializerEnd st){
        classGen.visit(st);
    }

    public void visit (ClassName cls){
        classGen.visit(cls);
    }
    public void visit (ClassDecl cls){
        classGen.visit(cls);
    }

    public void visit (ProgramName p) {classGen.visit(p);}

    // Condition ---------------------
    public void visit (ConditionLast cond){
        condGen.visit(cond);
    }
    public void visit (ConditionMore cond){
        condGen.visit(cond);
    }
    public void visit (CondFactSingle cond){
        condGen.visit(cond);
    }
    public void visit (CondFactMultiple cond){
        condGen.visit(cond);
    }

    public void visit (StatementIf stmt){
        condGen.visit(stmt);
    }
    public void visit (StatementIfElse stmt){
        condGen.visit(stmt);
    }
    public void visit (IfWord i){
        condGen.visit(i);
    }
    public void visit (ElseBegin e){
        condGen.visit(e);
    }

    public void visit (ForWord f){
        condGen.visit(f);
    }
    public void visit (ForAfterNotEmpty f){
        condGen.visit(f);
    }
    public void visit (ForAfterEmpty f){
        condGen.visit(f);
    }
    public void visit (ForInitEmpty f){
        condGen.visit(f);
    }
    public void visit (ForInitNotEmpty f){
        condGen.visit(f);
    }
    public void visit (ForCondFactEmpty f){
        condGen.visit(f);
    }

    public void visit (StatementFor f){
        condGen.visit(f);
    }
    public void visit (StatementBreak stmt){
        condGen.visit(stmt);
    }
    public void visit (StatementContinue stmt){
        condGen.visit(stmt);
    }

    // Designator ----------------------
    public void visit (DesignatorNamespace des){
        desGen.visit(des);
    }
    public void visit (DesignatorNoNamespace des){
        desGen.visit(des);
    }

    public void visit (DesignatorExprStmt des){
        desGen.visit(des);
    }
    public void visit (DesignatorIdentStmt des){
        desGen.visit(des);
    }

    // DesignatorStatement ----------------------
    public void visit (DesignatorStmtAssign stmt){
        desStmtGen.visit(stmt);
    }

    public void visit (DesignatorStmtDec stmt){
        desStmtGen.visit(stmt);
    }
    public void visit (DesignatorStmtInc stmt){
        desStmtGen.visit(stmt);
    }

    public void visit (DesignatorStmtAct stmt){
        desStmtGen.visit(stmt);
    }
    public void visit (DesignatorEqStmt stmt){
        desStmtGen.visit(stmt);
    }
    public void visit (DesignatorEqStmtStart stmt){
        desStmtGen.visit(stmt);
    }
    public void visit (DesignatorStatementListMemberYes d){
        desStmtGen.visit(d);
    }
    public void visit (DesignatorStatementListMemberNot d){
        desStmtGen.visit(d);
    }

    // Factor --------------------
    public void visit (ExprLastMinus e){
        factorGen.visit(e);
    }
    public void visit (ExprMore e) {
        factorGen.visit(e);
    }

    public void visit (TermMore t){
        factorGen.visit(t);
    }

    public void visit (FactorNum factor){
        factorGen.visit(factor);
    }
    public void visit (FactorBool factor){
        factorGen.visit(factor);
    }
    public void visit (FactorChar factor){
        factorGen.visit(factor);
    }

    public void visit (FactorDesignatorAct factor){
        factorGen.visit(factor);
    }
    public void visit (FactorNewAct factor){
        factorGen.visit(factor);
    }
    public void visit (FactorNewExpr factor){
        factorGen.visit(factor);
    }

    // Method -----------------------
    public void visit(MethodDecl method){
        methodGen.visit(method);
    }
    public void visit(MethodName method){
        methodGen.visit(method);
    }
    public void visit (MethodBegin meth) {
        methodGen.visit(meth);
    }

    public void visit (FormParArray formPar){
        methodGen.visit(formPar);
    }
    public void visit (FormParNotArray formPar){
        methodGen.visit(formPar);
    }

    public void visit (VarDeclSingleArray decl){
        methodGen.visit(decl);
    }
    public void visit (VarDeclSingleNotArray decl){
        methodGen.visit(decl);
    }

    public void visit (StatementReturn stmt){
        methodGen.visit(stmt);
    }
    public void visit (StatementReturnExpr stmt){
        methodGen.visit(stmt);
    }

    // Statement ----------------------
    public void visit (StatementRead stmt){
        stmtGen.visit(stmt);
    }
    public void visit (StatementPrintNoNum stmt){
        stmtGen.visit(stmt);
    }
    public void visit (StatementPrintNum stmt){
        stmtGen.visit(stmt);
    }
}
