package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.TermLast;
import rs.ac.bg.etf.pp1.ast.TermMore;
import rs.etf.pp1.symboltable.concepts.Struct;

public class TermAnalyzer extends BaseAnalyzer{

    public TermAnalyzer (SemAnalyzerCommon util, Logger log){
        super(util,log);
    }

    public void visit(TermLast term){

        term.struct=term.getFactor().struct;
        util.termLast=true;
    }

    public void visit(TermMore term){

        if (term.getFactor().struct.getKind()!= Struct.Int || (util.termLast && term.getTerm().struct.getKind()!=Struct.Int)) {
            report_error("Nisu svi cinioci izraza mnozenja tipa int. ", term);
        } else {
            term.struct = term.getFactor().struct;
        }
        util.termLast = false;
    }
}
