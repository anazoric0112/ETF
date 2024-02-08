package rs.ac.bg.etf.pp1.genutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class ClassGen extends BaseGenerator{

    public ClassGen(CodeGenCommon util, Logger log) {
        super(util, log);
    }

    public void visit(StaticInitializerStart st){
        if (util.mainPc==-1) util.mainPc=Code.pc;

        if (util.lastPatchAdr==-1) return;
        Code.fixup(util.lastPatchAdr);
    }

    public void visit(StaticInitializerEnd st){
        util.lastPatchAdr=Code.pc+1;
        Code.putJump(0);
    }

    public void visit (ClassName cls) {
        util.currentClass = Tab.find(cls.getClassName());
        util.currentClassName = cls.getClassName();
    }
    public void visit (ClassDecl cls){
        util.currentClass= null;
        util.currentClassName = "";

        Struct c = cls.obj.getType();
        for (Obj meth: c.getMembers()){
            if (meth.getKind()!=Obj.Meth) continue;
        }

        //make a virtual table
        Object[] methods=cls.obj.getType().getMembers().toArray();
        Obj tvf=(Obj)methods[0];
        tvf.setFpPos(util.staticVarNum);

        if (util.mainPc==-1) util.mainPc=Code.pc;
        if (util.lastPatchAdr!=-1) Code.fixup(util.lastPatchAdr);

        for (int i=1;i<methods.length;i++){
            Obj method=(Obj)methods[i];
            if (method.getKind()!=Obj.Meth) continue;

            String name=method.getName();
            int adr=method.getAdr();
            for (int j=0;j<name.length();j++){
                Code.loadConst(name.charAt(j));
                Code.put(Code.putstatic);
                Code.put2(util.staticVarNum++);
            }
            Code.loadConst(-1);
            Code.put(Code.putstatic);
            Code.put2(util.staticVarNum++);

            Code.loadConst(adr);
            Code.put(Code.putstatic);
            Code.put2(util.staticVarNum++);
        }
        Code.loadConst(-2);
        Code.put(Code.putstatic);
        Code.put2(util.staticVarNum++);

        util.lastPatchAdr=Code.pc+1;
        Code.putJump(0);
    }

    public void visit (ProgramName p){
        util.members=Tab.find(p.getProgramName()).getLocalSymbols();
        Obj ord=Tab.find("ord");
        Obj chr=Tab.find("chr");
        Obj len=Tab.find("len");
        ord.setAdr(Code.pc);
        putOrdChr();
        chr.setAdr(Code.pc);
        putOrdChr();
        len.setAdr(Code.pc);
        putLen();
    }

    public void putOrdChr(){
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    public void putLen(){
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.arraylength);
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

}
