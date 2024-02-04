package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.Collection;

public class VarAndTypeAnalyzer extends BaseAnalyzer{

    public VarAndTypeAnalyzer(SemAnalyzerCommon util, Logger log) {
        super(util, log);
    }

    public void visit (TypeNamespaceYes type){
        Obj namespace = Tab.find(type.getTypeNamespace().getNamespace());

        if (namespace==Tab.noObj){
            report_error(type.getTypeNamespace().getNamespace()+" nije definisan.",type);
            return;
        }
        if (!(namespace.getKind()==Obj.Prog && namespace.getAdr()==1)){
            report_error(namespace.getName() + " ne oznacava namespace.", type);
            return;
        }

        Obj typeObj=Tab.find(namespace.getName()+"::"+type.getTypeName().getTypeName());
        if (typeObj==Tab.noObj){
            report_error("Ime " +typeObj.getName()+ " nije definisano u " + namespace.getName() + " prostoru imena.", type);
            return;
        }
        if (typeObj.getKind()!=Obj.Type){
            report_error(typeObj.getName()+" ne oznacava tip podatka.", type);
            return;
        }
        type.obj=typeObj;
    }

    public void visit (TypeNamespaceNo type){
        Obj typeObj = Tab.find(type.getTypeName().getTypeName());
        String tname=nameIfInNamespace(type.getTypeName().getTypeName());
        if (typeObj==Tab.noObj) {
            typeObj=Tab.find(tname);
        }

        if (typeObj==Tab.noObj){
            report_error(type.getTypeName().getTypeName()+" nije definisano.",type);
            return;
        }
        if (typeObj.getKind()!=Obj.Type) {
            report_error(typeObj + " ne oznacava tip podatka.", type);
            return;
        }
        type.obj=typeObj;
    }

    public void visit (VarType var) {
        util.currentVarType = var.getType().obj;
    }

    public void visit (VarDeclSingleNotArray var){
        String vname=nameIfInNamespace(var.getVarName());
        if (Tab.currentScope().findSymbol(vname)!=null){
            report_error("Promenljiva sa imenom "+vname+" je vec deklarisana u datom okruzujucem opsegu.",var);
            return;
        }

        if (util.staticBlock){
            var.obj = Tab.insert(Obj.Var, var.getVarName(), util.currentVarType.getType());
            var.obj.setFpPos(-1); //oznacavace da je staticka varijabla
            var.obj.setAdr(util.currentVarNumProg);
            util.currentVarNumProg++;
            util.static_fields.get(util.currentClassName).add(var.getVarName());
            util.static_fields_to_add_globally.get(util.currentClassName).add(var.obj);
            return;
        }

        if (util.inMethodScope){
            var.obj = Tab.insert(Obj.Var, var.getVarName(), util.currentVarType.getType());
            var.obj.setAdr(util.currentVarNumMethod);
            util.currentVarNumMethod++;
        }
        else if (util.inClassScope){
            var.obj = Tab.insert(Obj.Fld, var.getVarName(), util.currentVarType.getType());
            var.obj.setAdr(util.currentFldNum);
            util.currentFldNum++;
            util.currentClassMembers.add(var.obj);
        }
        else if (util.inNamespaceScope){
            var.obj = Tab.insert(Obj.Var, nameIfInNamespace(var.getVarName()), util.currentVarType.getType());
            var.obj.setAdr(util.currentVarNumProg);
            util.currentVarNumProg++;
        } else {
            var.obj = Tab.insert(Obj.Var, var.getVarName(), util.currentVarType.getType());
            var.obj.setAdr(util.currentVarNumProg);
            util.currentVarNumProg++;
        }

    }

    public void visit (VarDeclSingleArray var){
        String vname=nameIfInNamespace(var.getVarName());
        if (Tab.currentScope().findSymbol(vname)!=null){
            report_error("Promenljiva sa imenom "+vname+" je vec deklarisana u datom okruzujucem opsegu.",var);
            return;
        }

        Struct varType = new Struct(Struct.Array, util.currentVarType.getType());

        if (util.staticBlock){
            var.obj = Tab.insert(Obj.Var, var.getVarName(), varType);
            var.obj.setFpPos(-1); //oznacavace da je staticka varijabla
            var.obj.setAdr(util.currentVarNumProg);
            util.currentVarNumProg++;
            util.static_fields.get(util.currentClassName).add(var.getVarName());
            util.static_fields_to_add_globally.get(util.currentClassName).add(var.obj);
            return;
        }

        if (util.inMethodScope){
            var.obj = Tab.insert(Obj.Var, var.getVarName(), varType);
            var.obj.setAdr(util.currentVarNumMethod);
            util.currentVarNumMethod++;
        }
        else if (util.inClassScope){
            var.obj = Tab.insert(Obj.Fld, var.getVarName(), varType);
            var.obj.setAdr(util.currentFldNum);
            util.currentFldNum++;
            util.currentClassMembers.add(var.obj);
        }
        else if (util.inNamespaceScope){
            var.obj = Tab.insert(Obj.Var, nameIfInNamespace(var.getVarName()), varType);
            var.obj.setAdr(util.currentVarNumProg);
            util.currentVarNumProg++;
        } else {
            var.obj = Tab.insert(Obj.Var, var.getVarName(), varType);
            var.obj.setAdr(util.currentVarNumProg);
            util.currentVarNumProg++;
        }

    }

    public void visit (VarDecl var){
        util.currentVarType=null;
    }

}
