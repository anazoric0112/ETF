package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.ExprLast;
import rs.ac.bg.etf.pp1.ast.ExprLastMinus;
import rs.ac.bg.etf.pp1.ast.ExprMore;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ExprAnalyzer extends BaseAnalyzer{

    public ExprAnalyzer(SemAnalyzerCommon util, Logger log){
        super(util,log);
    }

    public void visit(ExprLastMinus expr){
        if (expr.getTerm().struct.getKind()!= Struct.Int){
            report_error("Izraz ispred znaka minus nije tipa int.",expr);
            return;
        }
        expr.struct=expr.getTerm().struct;
    }

    public void visit(ExprLast expr){
        expr.struct=expr.getTerm().struct;
    }

    public void visit(ExprMore expr) {
        if (!expr.getExpr().struct.compatibleWith(expr.getTerm().struct)){
            report_error("Tipovi clanova izraza sabiranja nisu kompatibilni. ", expr);
        }

        if (expr.getExpr().struct.getKind()!=Struct.Int
                || expr.getTerm().struct.getKind()!=Struct.Int) {
            report_error("Nisu svi clanovi izraza sabiranja tipa int.", expr);
        }
        expr.struct=expr.getExpr().struct;
    }
}
