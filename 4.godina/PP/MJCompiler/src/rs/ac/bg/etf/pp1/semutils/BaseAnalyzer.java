package rs.ac.bg.etf.pp1.semutils;

//Volim te Ana <3

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.TypeNamespaceNo;
import rs.ac.bg.etf.pp1.ast.TypeNamespaceYes;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class BaseAnalyzer {

    SemAnalyzerCommon util;
    Logger log;

    BaseAnalyzer(SemAnalyzerCommon util, Logger log){
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

    public boolean assignmentCompatible(Struct s, Obj o){

        return s.compatibleWith(o.getType())
                || s.equals(o.getType())
                || (s == Tab.nullType && o.getType().isRefType())
                || classCompatible(s,o);
    }

    public boolean assignmentCompatible(Struct s, Struct s2){
        //this one is for arrays
        return s.compatibleWith(s2)
                || s.equals(s2)
                || (s == Tab.nullType && s2.isRefType())
                || classCompatible(s,s2);
    }

    public boolean classCompatible(Struct src, Obj dst){
        Struct srcClass = src;
        Struct dstClass = dst.getType();
        while (srcClass!=null){
            if (srcClass.getElemType()==dstClass.getElemType()){
                return true;
            }
            srcClass=srcClass.getElemType();
        }
        return false;
    }

    public boolean classCompatible(Struct src, Struct dst){
        Struct srcClass = src;
        Struct dstClass = dst;
        while (srcClass!=null){
            if (srcClass.getElemType()==dstClass.getElemType()){
                return true;
            }
            srcClass=srcClass.getElemType();
        }
        return false;
    }

    public boolean methodsMatch(Obj m1, Obj m2){
        //ako je metoda u pitanju nije bitna povratna vrednost (type)
        //ako nije isti potpis onda ne postoji konflikt
        //ako jeste postoji
        if (!(m1.getKind()==m2.getKind()
                && m1.getName().equals(m2.getName())
                && m1.getKind()==Obj.Meth
                && m1.getLevel()==m2.getLevel()))
            return false;
        int n=m1.getLevel();
        Object[] members1=m1.getLocalSymbols().toArray();
        Object[] members2=m2.getLocalSymbols().toArray();

        int i1=0,i2=0;
        //videcemo jel idu redom po rednim brojevima svaki put
        //ili moram duplu petlju i da proveravam adr
        while (i1<n && i2<n){
            Obj arg1=(Obj)members1[i1], arg2=(Obj)members2[i2];
            if (arg1.getKind()!=Obj.Fld){
                i1++;
                continue;
            }
            if (arg2.getKind()!=Obj.Fld){
                i2++;
                continue;
            }
            if (arg1.getType()!=arg2.getType()){
                return false;
            }
            i1++; i2++;
        }
        return true;
    }

    public int getBoolType(){
        return Tab.find("bool").getType().getKind();
    }

    public boolean checkStaticFieldOfClass (Obj obj){
        if (obj==Tab.find("eol")) return true;
        if (obj.getKind()!=Obj.Fld || !util.static_fields.get(util.currentClassName).contains(obj.getName())){
            report_error(obj.getName()+" nije staticko polje klase u kojoj se nalazi. ", null);
            return false;
        }
        return true;
    }

    public String getTypename(Type type){
        if (type instanceof TypeNamespaceYes) {
            return ((TypeNamespaceYes)type).getTypeName().getTypeName();
        } else {
            return ((TypeNamespaceNo)type).getTypeName().getTypeName();
        }
    }

    public Struct getTypeIfArray(Struct type) {
        int temp = util.isArrayElem;
        while (temp > 0) {
            type = type.getElemType();
            temp--;
        }
        return type;
    }

    public String nameIfInNamespace(String name){
        if (util.inNamespaceScope){
            return util.currentNamespaceName+"::"+name;
        } return name;
    }

    public String nameIfNamespaceUsed(String name){
        if (util.incNamespace){
            return util.currentNamespaceNameUsed+"::"+name;
        } return name;
    }
}
