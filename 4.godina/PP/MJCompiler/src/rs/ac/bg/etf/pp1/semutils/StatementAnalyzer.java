package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;
import java.util.Collection;

public class StatementAnalyzer extends BaseAnalyzer{

    public StatementAnalyzer(SemAnalyzerCommon util, Logger log) {
        super(util, log);
    }

    public void visit (StatementBreak stmt){
        if (!util.inFor){
            report_error("Break se moze koristiti samo unutar for petlje.",null);
        }
    }

    public void visit (StatementContinue stmt){
        if (!util.inFor){
            report_error("Continue se moze koristiti samo unutar for petlje.",stmt);
        }
    }

    public void visit (StatementReturnExpr stmt){
        if (!util.inMethodScope){
            report_error("Return iskaz ne sme postojati van tela metoda.",stmt);
        }
        else if (!stmt.getExpr().struct.equals(util.currentMethodType)){
            report_error("Tip izraza uz return se ne slaze sa povratnim tipom metode "+util.currentMethodName+".",stmt);
        }
    }

    public void visit (StatementReturn stmt){
        if (!util.inMethodScope){
            report_error("Return iskaz ne sme postojati van tela metoda.",stmt);
        }
        else if (util.currentMethodType!= Tab.noType){
            report_error("Nedostaje return iskaz za metodu "+util.currentMethodName+".",stmt);
        }
    }

    public void visit (StatementRead stmt){
        Obj desObj = stmt.getDesignator().obj;

        if (desObj.getKind()!=Obj.Var && desObj.getKind()!=Obj.Fld && util.isArrayElem==0){
            report_error(desObj.getName() +" nije promenljiva, polje klase ili element niza.",stmt);
            return;
        }
        Struct type = desObj.getType();
        int temp = util.isArrayElem;
        while (temp>0){
            type=type.getElemType();
            temp--;
        }
        if (type.getKind()!= Struct.Int
                && type.getKind()!=Struct.Char
                && type.getKind()!=getBoolType()){
            report_error("Promenljiva nije tipa bool, int ili char.",stmt);
        }
    }

    public void visit (StatementPrintNoNum stmt){
        int exprKind = stmt.getExpr().struct.getKind();
        if (exprKind!=Struct.Int && exprKind!=Struct.Char && exprKind!=getBoolType()){
            report_error("Izraz za ispis mora biti tipa int, char ili bool.", stmt);
        }
    }

    public void visit (StatementPrintNum stmt){
        int exprKind = stmt.getExpr().struct.getKind();
        if (exprKind!=Struct.Int && exprKind!=Struct.Char && exprKind!=getBoolType()){
            report_error("Izraz za ispis mora biti tipa int, char ili bool.", stmt);
        }
    }

    public void visit (StatementFor stmt){
        util.inFor=false;
    }

    public void visit (ForCondFactNotEmpty fcf){
        util.inFor=true;
        if (fcf.getCondFact().struct.getKind()!=getBoolType()){
            report_error("Izraz za prekid for petlje nije tipa bool.",fcf);
        }
    }

    public void visit (ForCondFactEmpty fcf){
        util.inFor=true;
    }

    public void visit (StatementDesignator stmt){
        //to be done
    }

    public void visit (DesignatorStmtAssign des){
        Obj desObj = des.getDesignator().obj;

        if (desObj.getKind()!=Obj.Var && desObj.getKind()!=Obj.Fld && util.isArrayElem==0){
            report_error(desObj.getName() +" nije promenljiva, polje klase ili element niza.",des);
        }
        else if (desObj.getType().getKind()==Struct.Array && !assignmentCompatible(des.getExpr().struct, desObj.getType().getElemType())){
            report_error("Tip izraza sa desne strane jednakosti nije kompatibilan sa tipom elementa "+desObj.getName()+".",des);
        } else if (desObj.getType().getKind()!=Struct.Array && !assignmentCompatible(des.getExpr().struct, desObj)){
            report_error("Tip izraza sa desne strane jednakosti nije kompatibilan sa tipom "+desObj.getName()+".",des);
        }
    }

    public void visit (DesignatorStmtAct des){
        Obj desObj = des.getDesignatorCall().obj;

        if (desObj.getKind()!=Obj.Meth){
            report_error(desObj.getName()+" ne predstavlja globalnu funkciju glavnog programa ili nestaticku metodu klase.",des);
            return;
        }
        util.currentMethodArgStack.pop();
    }

    public void visit (DesignatorStmtInc des){
        Obj desObj = des.getDesignator().obj;

        if (desObj.getKind()!=Obj.Var && desObj.getKind()!=Obj.Fld && util.isArrayElem==0){
            report_error(desObj.getName() +" nije promenljiva, polje klase ili element niza.",des);
        } else if (desObj.getType().getKind()!=Struct.Int && desObj.getType().getElemType().getKind()!=Struct.Int){
            report_error("Tip "+desObj.getName()+" mora biti int.",des);
        }
    }

    public void visit (DesignatorStmtDec des){
        Obj desObj = des.getDesignator().obj;

        if (desObj.getKind()!=Obj.Var && desObj.getKind()!=Obj.Fld && util.isArrayElem==0){
            report_error(desObj.getName() +" nije promenljiva, polje klase ili element niza.",des);
        } else if (desObj.getType().getKind()!=Struct.Int && desObj.getType().getElemType().getKind()!=Struct.Int){
            report_error("Tip "+desObj.getName()+" mora biti int.",des);
        }
    }

    public void visit (DesignatorEqStmt des){
        Obj desArray = des.getDesignatorAssignedArray().getDesignator().obj;
        des.getDesignatorAssignedArray().obj=desArray;
        if (desArray.getType().getKind()!=Struct.Array){
            report_error("Izraz sa desne strane znaka jednakosti nije niz.",des);
            return;
        }
        Obj desLast = des.getDesignator().obj;
        if (desLast.getType().getKind()!=Struct.Array){
            report_error("Poslednji izraz sa leve strane znaka jednakosti nije niz.",des);
            return;
        }
        for (int i=0;i<util.arrayAssignmentObjs.size();i++){
            if (!assignmentCompatible(desArray.getType(),util.arrayAssignmentObjs.get(i))){
                report_error("Tip podataka niza nije kompatibilan pri dodeli sa tipom "+(i+1)+
                        ". podatka sa leve strane znaka jednakosti.",des);
                return;
            }
        }
        util.arrayAssignmentObjs = new ArrayList<>();
    }

    public void visit (DesignatorStatementListMemberYes des){
        Obj desObj = des.getDesignator().obj;

        if (desObj.getKind()!=Obj.Var && desObj.getKind()!=Obj.Fld && util.isArrayElem==0){
            report_error("Svi izrazi sa leve strane znaka jednakosti osim poslednjeg " +
                    "moraju biti promenljive, elementi niza ili polja objekta klase.",des);
            return;
        }
        util.arrayAssignmentObjs.add(desObj);
    }
}
