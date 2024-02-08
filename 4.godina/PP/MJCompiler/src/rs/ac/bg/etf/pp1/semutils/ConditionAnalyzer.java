package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ConditionAnalyzer extends BaseAnalyzer{

    public ConditionAnalyzer(SemAnalyzerCommon util, Logger log){
        super(util,log);
    }

    public void visit(CondFactMultiple condFact){
        if (!condFact.getCondFactExpr1().struct.compatibleWith(condFact.getCondFactExpr2().struct)){
            report_error("Tipovi u relacionom izrazu nisu kompatibilni.", condFact);
            return;
        }
        int exprType = condFact.getCondFactExpr1().struct.getKind();
        if (exprType== Struct.Class || exprType==Struct.Array){
            if (!(condFact.getRelop() instanceof RelopEq || condFact.getRelop() instanceof RelopNeq)){
                report_error("Uz klase i nizove se mogu koristiti samo == i != relacioni operatori.",condFact);
                return;
            }
        }
        condFact.struct= Tab.find("bool").getType();
    }

    public void visit(CondFactSingle condFact){
        condFact.struct=condFact.getExpr().struct;
    }

    public void visit(ConditionLast cond){
        cond.struct = cond.getCondTerm().struct;
        if (cond.struct.getKind()!=getBoolType()){
            report_error("Tip izraza u if-u nije bool.",cond);
        }
    }

    public void visit (ConditionMore cond){
        cond.struct = cond.getCondition().struct;
        if (cond.struct.getKind()!=getBoolType()){
            report_error("Tip izraza u if-u nije bool.",cond);
        }
    }

    public void visit (CondTermLast cond){
        cond.struct = cond.getCondFact().struct;
    }

    public void visit (CondTermMore cond){
        cond.struct = cond.getCondTerm().struct;
    }

    public void visit (CondFactExpr1 cond){
        cond.struct = cond.getExpr().struct;
    }

    public void visit (CondFactExpr2 cond){
        cond.struct = cond.getExpr().struct;
    }
}
