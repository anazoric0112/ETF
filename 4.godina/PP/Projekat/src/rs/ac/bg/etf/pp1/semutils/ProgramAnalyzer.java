package rs.ac.bg.etf.pp1.semutils;

import rs.ac.bg.etf.pp1.SemanticAnalyzer;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.ProgramName;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import org.apache.log4j.Logger;

import java.util.Collection;

public class ProgramAnalyzer extends BaseAnalyzer {

    public ProgramAnalyzer(SemAnalyzerCommon util, Logger log){
        super(util,log);
    }

    public void visit (ProgramName progName){
        if (!util.programName.equals("")) {
            report_error("Deklaracija programa vec postoji.", progName);
            return;
        }
        util.currentVarNumProg = 0;
        progName.obj = Tab.insert(Obj.Prog, progName.getProgramName(), Tab.noType);
        util.programName=progName.getProgramName();
        util.inProgramScope = true;
        Tab.openScope();
    }

    public void visit (Program program){
        Tab.chainLocalSymbols(program.getProgramName().obj);
        program.getProgramName().obj.setAdr(0); //1-namespace, 0-prog
        util.nVars = Tab.currentScope().getnVars();
        Tab.closeScope();
        if (!util.mainFound){
            report_error("Metoda void main() nije pronadjena u kodu.",program);
        }
        util.inProgramScope=false;
    }
}
