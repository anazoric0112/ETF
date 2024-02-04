package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.Collection;

public class FactorAnalyzer extends BaseAnalyzer {

    public FactorAnalyzer(SemAnalyzerCommon util, Logger log){
        super(util, log);
    }

    public void visit(FactorNum factor){
        factor.struct= Tab.intType;
    }

    public void visit(FactorBool factor){
        factor.struct=Tab.find("bool").getType();
    }

    public void visit(FactorChar factor){
        factor.struct=Tab.charType;
    }

    public void visit(FactorExpr factor){
        factor.struct=factor.getExpr().struct;
    }

    public void visit(FactorDesignatorNoAct factor){
        Struct type=null;
        try{
            type = factor.getDesignator().obj.getType();
        } catch(Exception e){
            e.getMessage();
        }
        if (util.isArrayElem>0) type=type.getElemType();
        factor.struct=type;
    }

    public void visit(FactorNewExpr factor){
        if (factor.getExpr().struct.getKind()!= Struct.Int){
            report_error("Izraz izmedju [ ] nije tipa int. ",factor);
            return;
        }
        factor.struct=new Struct(Struct.Array, factor.getType().obj.getType());
    }

    public void visit(FactorNewAct factor){
        if (factor.getConstructorCall().getType().obj.getType().getKind()!=Struct.Class){
            report_error("Tip ne predstavlja klasu. ", factor);
            return;
        }
        factor.struct = factor.getConstructorCall().obj.getType();
        util.currentMethodArgStack.pop();
    }

    public void visit (ConstructorCall call){
        util.currentMethodArgStack.push(0);
        util.currentMethodCall=Tab.find(call.getType().obj.getName());
        call.obj = util.currentMethodCall;
    }

    public void visit(FactorDesignatorAct factor){
        Obj desObj = factor.getDesignatorCall().getDesignator().obj;

        if (desObj.getKind()!=Obj.Meth){
            report_error(desObj.getName()+" ne predstavlja  globalnu funkciju ili metodu.",factor);
            return;
        }

        factor.struct = factor.getDesignatorCall().obj.getType();

        util.currentMethodArgStack.pop();
    }
}
