package rs.ac.bg.etf.pp1.semutils;

import javafx.beans.binding.ObjectExpression;
import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

import java.util.Collection;

public class MethodAnalyzer extends BaseAnalyzer{

    public MethodAnalyzer(SemAnalyzerCommon util, Logger log) {
        super(util, log);
    }

    public void visit (ActPars actPars){
        if (util.currentMethodCall.getLevel()!=util.currentMethodArgStack.lastElement()+1){
            report_error("Broj argumenata se ne slaze sa brojem argumenata metode "+util.currentMethodCall.getName(),actPars);
        }
        util.currentMethodCall = null;
        util.currentMethodArgStack.pop();
    }

    public void visit (ActParSingle actPar){
        Collection<Obj> args = util.currentMethodCall.getLocalSymbols();
        Object args_list[] = args.stream().toArray();
        Struct actType = actPar.getExpr().struct;
        actPar.struct=actType;

        if (util.currentMethodArgStack.lastElement()>args_list.length){
            report_error("Broj argumenata se ne slaze sa brojem argumenata metode "+util.currentMethodCall.getName()+".",actPar);
            return;
        }
        Obj arg=Tab.noObj;
        for (int i=0;i<args_list.length;i++){
            if (((Obj)args_list[i]).getFpPos()==util.currentMethodArgStack.lastElement()){
                arg=(Obj)args_list[i];
                break;
            }
        }
        if (arg==Tab.noObj){
            report_error("Broj argumenata se ne slaze sa brojem argumenata metode "+util.currentMethodCall.getName()+".",actPar);
            return;
        }

        if (!actType.compatibleWith(arg.getType())
                && !(actType == Tab.nullType && arg.getType().isRefType())
                && !classCompatible(actType,arg)
                && !(arg.getType().getKind()==Struct.Array)){

            report_error("Argument na poziciji "
                    +(util.currentMethodArgStack.lastElement()+1)
                    +" nije kompatibilan sa tipom argumenta funkcije. ",actPar);
            return;
        }
        int argnum = util.currentMethodArgStack.pop();
        util.currentMethodArgStack.push(argnum+1);
    }

    public void visit (MethodDeclParamsOk method){
        if (util.inClassScope && util.methodElemNum==0)  util.currentVarNumMethod=1;
        else util.currentVarNumMethod = util.methodElemNum;
    }

    public void visit (MethodDecl method){

        if (Tab.currentScope().getLocals()!=null){
            Obj existingMethod = Tab.currentScope().getLocals().searchKey(util.currentMethodName);
            if (existingMethod!=null && methodsMatch(method.obj,existingMethod)){
                report_error("Metoda sa imenom " + util.currentMethodName + " i istim potpisom vec postoji u datom opsegu.", method);
                util.currentMethodName = "";
                util.currentMethodType = null;
                util.inMethodScope=false;
                return;
            }
        }

        SymbolDataStructure methodArgs = Tab.currentScope().getLocals();
        Tab.closeScope();

        if (util.inClassScope)
            util.thisobj.setFpPos(util.methodElemNum++); //zbog this
        method.obj = Tab.insert(Obj.Meth, util.currentMethodName, util.currentMethodType);
        method.obj.setLevel(util.methodElemNum);
        method.obj.setLocals(methodArgs);
        if (util.inClassScope) util.currentClassMembers.add(method.obj);

        if (util.currentMethodName.equals("main")
                && !util.inClassScope
                && !util.inNamespaceScope
                && util.currentMethodType==Tab.noType
                && util.currentMethodArgStack.lastElement()==0){
            util.mainFound=true;
        }

        util.currentMethodName = "";
        util.currentMethodType = null;
        util.inMethodScope=false;
        util.methodName.obj=method.obj;
        util.currentVarNumMethod = 0;
        util.methodElemNum=0;
    }

    public void visit (VoidMethodType methodType){
        util.currentMethodType = Tab.noType;
    }

    public void visit (TypeMethodType methodType){
        String typename = getTypename(methodType.getType());

        Obj type = Tab.find(typename);
        if (type.getKind()!=Obj.Type){
            report_error(typename+" ne predstavlja tip podatka.",methodType);
        }
        util.currentMethodType = type.getType();
    }

    public void visit (MethodName method){
        Tab.openScope();
        if (!util.inClassScope){
            util.currentMethodName=nameIfInNamespace(method.getName());
        } else{
            util.currentMethodName=method.getName();
        }
        util.currentMethodArgStack.push(0);
        util.inMethodScope=true;
        util.methodName = method;

        if (util.inClassScope){
            util.thisobj=Tab.insert(Obj.Var,"this",util.currentClassStruct);
        }
    }

    public void visit (FormParArray formPar){
        Obj formParType = formPar.getFormParType().obj;

        if (formParType.getKind()!=Obj.Type){
            report_error(formParType.getName()+" ne definise tip podataka.", formPar);
            return;
        }
        String formParName = formPar.getFormParName().getName();
        Collection<Obj> curMethodScope = Tab.currentScope().values();
        for (Obj obj: curMethodScope) {
            if (obj.getName()==formParName) {
                report_error("Argument sa imenom "+formParName+" je vec deklarisan u potpisu metode.",formPar);
                return;
            }
        }
        formPar.obj = Tab.insert(Obj.Var, formParName, new Struct(Struct.Array, formParType.getType()));
        formPar.obj.setFpPos(util.currentMethodArgStack.lastElement());
        int argnum = util.currentMethodArgStack.pop();
        util.currentMethodArgStack.push(argnum+1);
    }

    public void visit (FormParNotArray formPar){
        Obj formParType = formPar.getFormParType().obj;

        if (formParType.getKind()!=Obj.Type){
            report_error(formParType.getName()+" ne definise tip podataka.", formPar);
            return;
        }
        String formParName = formPar.getFormParName().getName();
        Collection<Obj> curMethodScope = Tab.currentScope().values();
        for (Obj obj: curMethodScope) {
            if (obj.getName()==formParName) {
                report_error("Argument sa imenom "+formParName+" je vec deklarisan u potpisu metode.",formPar);
                return;
            }
        }
        formPar.obj = Tab.insert(Obj.Var, formParName, formParType.getType());
        formPar.obj.setFpPos(util.currentMethodArgStack.lastElement());

        int argnum = util.currentMethodArgStack.pop();
        util.currentMethodArgStack.push(argnum+1);
    }

    public void visit (FormParsYes formPar){
        util.methodElemNum=util.currentMethodArgStack.pop();
    }

    public void visit (FormParsNot formPar) {
//        if (util.inClassScope) util.methodElemNum=1;
    }

    public void visit (FormParType formPar){
        formPar.obj=formPar.getType().obj;
    }
}
