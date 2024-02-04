package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.semutils.*;

public class SemanticAnalyzer extends VisitorAdaptor {

   ClassAnalyzer classAnal;
   ConditionAnalyzer condAnal;
   ConstAnalyzer constAnal;
   DesignatorAnalyzer desAnal;
   ExprAnalyzer exprAnal;
   FactorAnalyzer factAnal;
   MethodAnalyzer methAnal;
   NamespaceAnalyzer nameAnal;
   ProgramAnalyzer progAnal;
   StatementAnalyzer stmtAnal;
   TermAnalyzer termAnal;
   VarAndTypeAnalyzer varAnal;

   int nVars = 0;


   SemAnalyzerCommon util = new SemAnalyzerCommon();
   Logger log = Logger.getLogger(getClass());

   SemanticAnalyzer(){
       classAnal=new ClassAnalyzer(util,log);
       condAnal=new ConditionAnalyzer(util,log);
       constAnal=new ConstAnalyzer(util,log);
       desAnal=new DesignatorAnalyzer(util,log);
       exprAnal=new ExprAnalyzer(util,log);
       factAnal=new FactorAnalyzer(util,log);
       methAnal=new MethodAnalyzer(util,log);
       nameAnal=new NamespaceAnalyzer(util,log);
       progAnal=new ProgramAnalyzer(util,log);
       stmtAnal=new StatementAnalyzer(util,log);
       termAnal=new TermAnalyzer(util,log);
       varAnal= new VarAndTypeAnalyzer(util,log);
   }

   //Class Analysis---------------------------

   public void visit(ClassDecl cls){
       classAnal.visit(cls);
   }

   public void visit(ExtendsYes cls){
       classAnal.visit(cls);
   }

   public void visit(ExtendsNot cls){
       classAnal.visit(cls);
   }

   public void visit(ClassName cls){
       classAnal.visit(cls);
   }

   //static stuff
   public void visit(StaticVarDecl staticVar){
       classAnal.visit(staticVar);
   }

   public void visit(StaticInitializer init){
       classAnal.visit(init);
   }

   public void visit(StaticWord stat){
       classAnal.visit(stat);
   }

   //Condition Analysis---------------------------
   public void visit(CondFactMultiple condFact){
       condAnal.visit(condFact);
   }

   public void visit(CondFactSingle condFact){
       condAnal.visit(condFact);
   }

   public void visit(ConditionLast cond){
       condAnal.visit(cond);
   }

   public void visit (ConditionMore cond){
       condAnal.visit(cond);
   }

   public void visit (CondTermLast cond){
       condAnal.visit(cond);
   }

   public void visit (CondTermMore cond){
       condAnal.visit(cond);
   }

   public void visit (CondFactExpr1 cond) {
       condAnal.visit(cond);
   }

   public void visit (CondFactExpr2 cond) {
       condAnal.visit(cond);
   }

   //Constants Analysis---------------------------
   public void visit (NumConst con){
       constAnal.visit(con);
   }

   public void visit (BoolConst con){
       constAnal.visit(con);
   }

   public void visit (CharConst con){
       constAnal.visit(con);
   }

   public void visit (ConstName con){
       constAnal.visit(con);
   }

   public void visit (ConstType con){
       constAnal.visit(con);
   }

   public void visit (ConstDecl con){
       constAnal.visit(con);
   }

   //Designator Analysis---------------------------
   public void visit (DesignatorNamespaceName designator){
       desAnal.visit(designator);
   }

   public void visit (DesignatorName designator){
       desAnal.visit(designator);
   }

   public void visit (DesignatorExprStmt designator){
       desAnal.visit(designator);
   }

   public void visit (DesignatorIdent designator) {
       desAnal.visit(designator);
   }

   public void visit (DesignatorNamespace designator){
       desAnal.visit(designator);
   }

   public void visit (DesignatorNoNamespace designator){
       desAnal.visit(designator);
   }

   public void visit (DesignatorCall designator){
       desAnal.visit(designator);
   }

   public void visit (DesignatorIdentStmt designator){
       desAnal.visit(designator);
   }

   //Expression Analysis---------------------------
   public void visit(ExprLastMinus expr){
       exprAnal.visit(expr);
   }

   public void visit(ExprLast expr){
       exprAnal.visit(expr);
   }

   public void visit(ExprMore expr) {
       exprAnal.visit(expr);
   }

   //Factor Analysis---------------------------
   public void visit(FactorNum factor){
       factAnal.visit(factor);
   }

   public void visit(FactorBool factor){
       factAnal.visit(factor);
   }

   public void visit(FactorChar factor){
       factAnal.visit(factor);
   }

   public void visit(FactorExpr factor){
       factAnal.visit(factor);
   }

   public void visit(FactorDesignatorNoAct factor){
       factAnal.visit(factor);
   }

   public void visit(FactorNewExpr factor){
       factAnal.visit(factor);
   }

   public void visit(FactorNewAct factor){
       factAnal.visit(factor);
   }

   public void visit(FactorDesignatorAct factor){
       factAnal.visit(factor);
   }
   public void visit (ConstructorCall call){
       factAnal.visit(call);
   }

   //Method Analysis---------------------------
   public void visit (ActPars actPars){
       methAnal.visit(actPars);
   }

   public void visit (ActParSingle actPar){
       methAnal.visit(actPar);
   }

   public void visit (MethodDecl method){
       methAnal.visit(method);
   }

   public void visit (VoidMethodType methodType){
       methAnal.visit(methodType);
   }

   public void visit (TypeMethodType methodType){
       methAnal.visit(methodType);
   }

   public void visit (MethodName method){
       methAnal.visit(method);
   }

   public void visit (MethodDeclParamsOk method){
       methAnal.visit(method);
   }

   public void visit (FormParArray formPar){
       methAnal.visit(formPar);
   }

   public void visit (FormParNotArray formPar){
       methAnal.visit(formPar);
   }

   public void visit (FormParsYes formPar){
       methAnal.visit(formPar);
   }

   public void visit (FormParsNot formPar){
        methAnal.visit(formPar);
    }

   public void visit (FormParType formPar){
       methAnal.visit(formPar);
   }

   //Namespace Analysis---------------------------
   public void visit(NamespaceName namespace){
       nameAnal.visit(namespace);
   }

   public void visit(NamespaceSingle namespace){
       nameAnal.visit(namespace);
   }

   //Program Analysis---------------------------
   public void visit (ProgramName progName){
       progAnal.visit(progName);
   }

   public void visit (Program program){
       progAnal.visit(program);
       nVars = util.nVars;
   }

   //Statement Analysis---------------------------
   public void visit (StatementBreak stmt){
       stmtAnal.visit(stmt);
   }

   public void visit (StatementContinue stmt){
       stmtAnal.visit(stmt);
   }

   public void visit (StatementReturnExpr stmt){
       stmtAnal.visit(stmt);
   }

   public void visit (StatementReturn stmt){
       stmtAnal.visit(stmt);
   }

   public void visit (StatementRead stmt){
       stmtAnal.visit(stmt);
   }

   public void visit (StatementPrintNoNum stmt){
       stmtAnal.visit(stmt);
   }

   public void visit (StatementPrintNum stmt){
       stmtAnal.visit(stmt);
   }

   public void visit (StatementFor stmt){
       stmtAnal.visit(stmt);
   }

   public void visit (ForCondFactNotEmpty fcf){
       stmtAnal.visit(fcf);
   }

    public void visit (ForCondFactEmpty fcf){
       stmtAnal.visit(fcf);
    }

    public void visit (StatementDesignator stmt){
       stmtAnal.visit(stmt);
   }

   public void visit (DesignatorStmtAssign des){
       stmtAnal.visit(des);
   }

   public void visit (DesignatorStmtAct des){
       stmtAnal.visit(des);
   }

   public void visit (DesignatorStmtInc des){
       stmtAnal.visit(des);
   }

   public void visit (DesignatorStmtDec des){
       stmtAnal.visit(des);
   }

   public void visit (DesignatorEqStmt des){
       stmtAnal.visit(des);
   }

   public void visit (DesignatorStatementListMemberYes des){
       stmtAnal.visit(des);
   }

   //Term Analysis---------------------------
   public void visit(TermLast term){
       termAnal.visit(term);
   }

   public void visit(TermMore term){
       termAnal.visit(term);
   }

   //Var and Type Analysis---------------------------
   public void visit (TypeNamespaceYes type){
       varAnal.visit(type);
   }

   public void visit (TypeNamespaceNo type){
       varAnal.visit(type);
   }

   public void visit (VarType var) {
       varAnal.visit(var);
   }

   public void visit (VarDeclSingleNotArray var){
       varAnal.visit(var);
   }

   public void visit (VarDeclSingleArray var){
       varAnal.visit(var);
   }

   public void visit (VarDecl var){
       varAnal.visit(var);
   }

}
