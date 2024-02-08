package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;
import java.util.Collection;

public class DesignatorAnalyzer extends BaseAnalyzer{

    public DesignatorAnalyzer(SemAnalyzerCommon util, Logger log){
        super(util,log);
    }

    public void visit (DesignatorNamespaceName designator){
        Obj namespaceName = Tab.find(designator.getNamespaceName());
        if (namespaceName==Tab.noObj || !(namespaceName.getKind()==Obj.Prog && namespaceName.getAdr()==1)){
            report_error(designator.getNamespaceName() + " ne oznacava namespace.", designator);
            return;
        }
        util.currentNamespaceNameUsed=namespaceName.getName();
        util.incNamespace = true;
    }

    public void visit (DesignatorName designator){ //dodaj da se proveri je l u klasi
        Obj desObj=Tab.noObj;
        String desname=nameIfNamespaceUsed(designator.getDesignatorName()); //!!!

        if (util.inClassScope && desname.equals(util.currentClassName)){
            if (designator.getParent().getParent() instanceof DesignatorIdentStmt) {
                designator.obj=desObj;
                util.currentDesignatorName=desname;
            } else {
                report_error("Ime klase "+desname+ " pogresno iskorisceno.", designator);
            } return;
        }
        if (util.incNamespace){
            desObj=Tab.find(desname);
            if (desObj==Tab.noObj){
                report_error(desname+" nije definisano u prostoru imena "
                        +util.currentNamespaceNameUsed+".",designator);
                util.currentNamespaceNameUsed="";
                util.incNamespace=false;
                return;
            }
            util.currentNamespaceNameUsed="";
            util.incNamespace=false;
        } else{
            desObj = Tab.find(desname);
            if (desObj==Tab.noObj) desObj = Tab.find(nameIfInNamespace(desname));
            if (desObj==Tab.noObj && !util.staticBlock ){
                report_error(desname+" nije definisano.",designator);
                return;
            }
        }

        int desKind = desObj.getKind();
        if (desKind!=Obj.Var && desKind!=Obj.Con && desKind!=Obj.Meth && desKind!=Obj.Type && desKind!=Obj.Fld){ //upitno valja li ovaj uslov
            report_error(desname+" ne predstavlja promenljivu, konstantu, metodu ili tip podatka.", designator);
            return;
        }
        saveDesignatorData(desObj);
        designator.obj=desObj;
    }

    public void visit (DesignatorExprStmt designator){
        Obj desObj = designator.getDesignator().obj;

        if (desObj.getType().getKind()!= Struct.Array){
            report_error(designator.getDesignator().obj.getName()+ " ne predstavlja niz.", designator);
            return;
        }
        if (designator.getExpr().struct.getKind()!=Struct.Int){
            Struct type = designator.getExpr().struct;
            int temp=util.isArrayElem;
            while (temp>0){
                type=type.getElemType();
                temp--;
            }
            if ((util.isArrayElem>0 && type.getKind()!=Struct.Int)
                || util.isArrayElem==0){
                report_error("Izraz unutar [ ] nije tipa int. ", designator);
                return;
            }
        }

        saveDesignatorData(desObj);
        designator.obj=desObj; //postojala neka ideja da ovde prebacim u Obj.Elem
        util.isArrayElem +=1;
        util.isClassField = false;
    }

    public void visit (DesignatorIdent designator) {
        Obj desIdentObj = Tab.find(designator.getIdent());
        String desIdentName = designator.getIdent();
        Struct desIdentType = getTypeIfArray(util.currentDesignatorStruct);

        //ako smo iskoristili ime klase u trenutnoj klasi to je Klasa.staticko_polje
        if (util.inClassScope && util.currentDesignatorName.equals(util.currentClassName)){
            for (String obj: util.static_fields.get(util.currentClassName)) {
                if (obj.equals(desIdentName)) {
                    desIdentObj = Tab.find(desIdentName);
                    break;
                }
            }
        }else if (util.currentDesignatorKind!=Obj.Type && desIdentType.getKind()==Struct.Class){ //desident je polje klase
            if (util.currentDesignatorName.equals("this")){
                boolean found = false;
                for (Obj obj: util.currentClassMembers) {
                    if (obj.getName().equals(desIdentName)) {
                        desIdentObj = obj;
                        found=true;
                        break;
                    }
                }
                if (!found){
                    report_error(desIdentName + " ne prestavlja polje ili metodu klase " + util.currentDesignatorName, designator);
                    return;
                }
            } else {
                Collection<Obj> class_members = getTypeIfArray(Tab.find(util.currentDesignatorName).getType()).getMembers();
                boolean found = false;
                for (Obj obj: class_members) {
                    if (obj.getName().equals(desIdentName)) {
                        found=true;
                        desIdentObj = obj;
                        break;
                    }
                }
                if (!found) {
                    report_error(desIdentName + " ne prestavlja polje ili metodu klase " + util.currentDesignatorName, designator);
                    return;
                }
            }

        }   //Klasa i staticko polje
        else if (util.currentDesignatorKind==Obj.Type && util.currentDesignatorType==Struct.Class){

            boolean found = false;
            for (String obj: util.static_fields.get(util.currentDesignatorName)) {
                if (!obj.equals(desIdentName))  continue;

                found=true;
                Collection<Obj> members = Tab.find(util.currentDesignatorName).getType().getMembers();
                for(Obj objMem:members){
                    if (objMem.getName().equals(desIdentName)){
                        desIdentObj=objMem;
                        break;
                    }
                }
            }
            if (!found) {
                report_error(desIdentName + " ne prestavlja staticko polje klase " + util.currentDesignatorName, designator);
                return;
            }
        } else {
            report_error(util.currentDesignatorName+" ne predstavlja ime klase i nije tipa klase.", designator);
            return;
        }

        saveDesignatorData(desIdentObj);
        util.isArrayElem = 0;
        util.isClassField = true;
        designator.obj=desIdentObj;
    }

    public void visit (DesignatorNamespace designator){
        designator.obj=designator.getDesignatorName().obj;

        util.isArrayElem = 0;
        util.isClassField = false;
    }

    public void visit (DesignatorNoNamespace designator){
        designator.obj=designator.getDesignatorName().obj;

        util.isArrayElem = 0;
        util.isClassField = false;
    }

    public void visit (DesignatorCall designator){
        util.currentMethodCall = designator.getDesignator().obj;
        util.currentMethodArgStack.push(0);
        designator.obj = designator.getDesignator().obj;
    }

    public void visit (DesignatorIdentStmt designator){
        designator.obj=designator.getDesignatorIdent().obj;
    }

    public void saveDesignatorData(Obj desObj){
        util.currentDesignatorStruct=desObj.getType();
        util.currentDesignatorType=desObj.getType().getKind();
        util.currentDesignatorKind=desObj.getKind();
        util.currentDesignatorName=desObj.getName();
    }

}
