package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

import java.util.ArrayList;
import java.util.HashSet;

public class ClassAnalyzer extends BaseAnalyzer{

    public ClassAnalyzer(SemAnalyzerCommon util, Logger log) {
        super(util, log);
    }

    public void visit(ClassDecl cls){
        int cnt=1;
        ArrayList<Obj> prevFields=new ArrayList<>();
        for (Obj o:Tab.currentScope().getLocals().symbols()){
            if (o.getKind()==Obj.Fld && !o.getName().equals(util.currentClassName+"::TVF")) prevFields.add(o);
        }
        if (util.extendsClass){ //moraju da mu se dodaju sve metode i polja iz okruzujuce
            Obj parentClass = Tab.find(util.currentParentClass);
            for (Obj objP: parentClass.getType().getMembers()){
                if (objP.getName().contains("::TVF")) continue;
                Obj objC = Tab.currentScope().findSymbol(objP.getName());

                //ako ne postoji nista sa istim imenom u scope-u child-a,
                //treba da se doda iz parent-a
                if (objC==null){
                    Tab.currentScope().addToLocals(objP);
                    if (objP.getKind()==Obj.Fld) objP.setAdr(cnt++);
                    continue;
                }
                //type nije bitan za ovo jer ce se samo sakriti spoljasnje ime nezavisno od toga

                if (objP.getKind()==objC.getKind()
                        && objP.getKind()==Obj.Fld) {
                    continue;
                }
                if (!methodsMatch(objP,objC)){
                    Tab.currentScope().addToLocals(objP);
                    continue;
                }

            }
            util.extendsClass=false;
            util.currentParentClass="";
            for(Obj o: prevFields){
                o.setAdr(cnt++);
            }
        }
        SymbolDataStructure class_members = Tab.currentScope().getLocals();

        Tab.closeScope();

        cls.obj=Tab.insert(Obj.Type, util.currentClassName,util.currentClassStruct);
        cls.obj.getType().setMembers(class_members);

        for (Obj var: util.static_fields_to_add_globally.get(util.currentClassName)){
            String vname=util.currentClassName+".$staticf."+var.getName();
            Tab.insert(Obj.Var, vname, var.getType());
        }

        util.inClassScope=false;
        util.currentClassName="";
        util.thisObjects=new ArrayList<>();
    }

    public void visit(ExtendsYes cls){
        Obj ext = Tab.find(getTypename(cls.getType()));
        if (ext==Tab.noObj){
            ext=Tab.find(nameIfInNamespace(getTypename(cls.getType())));
        }
        if (ext.getKind()!=Obj.Type && ext.getType().getKind()!=Struct.Class){
            report_error(getTypename(cls.getType())+" ne predstavlja ime klase.",cls);
            return;
        }
        util.extendsClass=true;
        util.currentParentClass = ext.getName();

        Obj parentClass = Tab.find(ext.getName());
        util.currentClassStruct=new Struct(Struct.Class,parentClass.getType());
    }

    public void visit(ExtendsNot cls){
        util.currentClassStruct=new Struct(Struct.Class,Tab.noType);
    }

    public void visit(ClassName cls){
        Obj existingClass = Tab.find(cls.getClassName());
        if(existingClass!=Tab.noObj){
            report_error("Klasa sa imenom "+cls.getClassName()+" vec postoji.",cls);
            return;
        }
        util.currentClassName= nameIfInNamespace(cls.getClassName());
        util.currentFldNum=0;
        util.inClassScope=true;
        util.currentClassMembers=new ArrayList<>();
        util.static_fields.put(util.currentClassName,new HashSet<>());
        util.static_fields_to_add_globally.put(util.currentClassName,new HashSet<>());
        Tab.openScope();

        Obj tvf=Tab.insert(Obj.Fld, util.currentClassName+"::TVF", Tab.intType);
        tvf.setAdr(util.currentFldNum++);
    }

    //static stuff
    public void visit(StaticVarDecl staticVar){
        util.staticBlock=false;
    }

    public void visit(StaticInitializer init){
        util.staticBlock=false;
    }

    public void visit(StaticWord stat){
        util.staticBlock=true;
    }
}
