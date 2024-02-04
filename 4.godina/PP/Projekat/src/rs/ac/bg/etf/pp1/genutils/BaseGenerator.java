package rs.ac.bg.etf.pp1.genutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;


public class BaseGenerator {

    CodeGenCommon util;
    Logger log;

    BaseGenerator(CodeGenCommon util, Logger log){
        this.util=util;
        this.log=log;
    }


    public void report_error(String message, SyntaxNode info) {
        util.errorDetected = true;
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0: info.getLine();
        if (line != 0)
            msg.append (" na liniji ").append(line);
        log.error(msg.toString());
    }

    public void report_info(String message, SyntaxNode info) {
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0: info.getLine();
        if (line != 0)
            msg.append (" na liniji ").append(line);
        log.info(msg.toString());
    }

    public Obj findObjByName(String name){
        for (Obj o: util.members){
            if (o.getName().equals(name)) return o;
        }
        return Tab.noObj;
    }

    public boolean isArray(Obj obj){
        if (obj.getType().getKind()== Struct.Array){
            return true;
        }
        return false;
    }

    public void traverseDesObj(Designator d){
        VisitorDes v = new VisitorDes(util, this);
        if (d instanceof DesignatorIdentStmt){
            d=((DesignatorIdentStmt) d).getDesignator();
            traverseDesObj(d);
        } else if (d instanceof DesignatorNamespace){
            ((DesignatorNamespace) d).traverseBottomUp(v);
        } else if (d instanceof DesignatorNoNamespace){
            ((DesignatorNoNamespace) d).traverseBottomUp(v);
        } else if (d instanceof DesignatorExprStmt){
            ((DesignatorExprStmt) d).traverseBottomUp(v);
        }
    }

}
