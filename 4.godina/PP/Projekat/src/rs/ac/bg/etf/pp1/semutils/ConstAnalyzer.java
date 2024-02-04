package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ConstAnalyzer extends  BaseAnalyzer{

    public ConstAnalyzer(SemAnalyzerCommon util, Logger log){
        super(util,log);
    }

    public void visit (NumConst con){
        if (util.currentConstType.getType().getKind()!= Struct.Int){
            report_error("Vrednost i tip konstante se ne slazu.",con);
            return;
        }
        con.obj = Tab.insert(Obj.Con, util.currentConstName, Tab.intType);
        con.obj.setAdr(con.getName());
    }

    public void visit (BoolConst con){
        if (util.currentConstType.getType().getKind()!=Tab.find("bool").getType().getKind()){
            report_error("Vrednost i tip konstante se ne slazu.",con);
            return;
        }
        con.obj = Tab.insert(Obj.Con, util.currentConstName, Tab.find("bool").getType());
        int adr=0;
        if (con.getName()) adr=1;
        con.obj.setAdr(adr);
    }

    public void visit (CharConst con){
        if (util.currentConstType.getType().getKind()!=Struct.Char){
            report_error("Vrednost i tip konstante se ne slazu.",con);
            return;
        }
        con.obj = Tab.insert(Obj.Con, util.currentConstName, Tab.charType);
        con.obj.setAdr(con.getName());
    }

    public void visit (ConstName con){
        String cname= nameIfInNamespace(con.getName());
        if (Tab.currentScope().findSymbol(cname)!=null){
            report_error("Ime "+cname+" je vec u upotrebi.",con);
        }
        util.currentConstName = cname;
    }

    public void visit (ConstType con){
        Type conType = con.getType();
        Obj type = Tab.noObj ;
        if (conType instanceof TypeNamespaceNo){
            type = Tab.find(nameIfNamespaceUsed(((TypeNamespaceNo) conType).getTypeName().getTypeName()));
        } else if (conType instanceof TypeNamespaceYes){
            type = Tab.find(nameIfNamespaceUsed(((TypeNamespaceYes) conType).getTypeName().getTypeName()));
        }

        if (type==Tab.noObj){
            report_error("Ne postoji tip specificiranog imena.", con);
            return;
        }
        util.currentConstType = type;
    }

    public void visit (ConstDecl con){
        util.currentConstType = null;
    }

}
