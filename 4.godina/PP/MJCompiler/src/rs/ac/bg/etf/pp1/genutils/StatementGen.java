package rs.ac.bg.etf.pp1.genutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class StatementGen extends BaseGenerator{

    public StatementGen(CodeGenCommon util, Logger log) {
        super(util, log);
    }

    public void visit(StatementRead stmt){
        Obj desObj = stmt.getDesignator().obj;

        if (isArray(desObj)){
            if (desObj.getType().getElemType().getKind() == Struct.Char){
                Code.put(Code.bread);
                Code.put(Code.bastore);
            } else {
                Code.put(Code.read);
                Code.put(Code.astore);
            }
        } else {
            if (desObj.getType().getKind()== Struct.Int){
                Code.put(Code.read);
                Code.store(desObj);
            } else {
                Code.put(Code.bread);
                Code.store(desObj);
            }
        }
    }

    public void visit(StatementPrintNoNum stmt){
        if (stmt.getExpr().struct.getKind()== Struct.Int){
            Code.loadConst(5);
            Code.put(Code.print);
        } else {
            Code.loadConst(1);
            Code.put(Code.bprint);
        }
    }

    public void visit(StatementPrintNum stmt){
        int n=stmt.getN2();
        for (int i=0;i<n;i++){
            if (i!=n-1) Code.put(Code.dup);

            if (stmt.getExpr().struct.getKind()== Struct.Int){
                Code.loadConst(5);
                Code.put(Code.print);
            } else {
                Code.loadConst(1);
                Code.put(Code.bprint);
            }
        }
    }

}
