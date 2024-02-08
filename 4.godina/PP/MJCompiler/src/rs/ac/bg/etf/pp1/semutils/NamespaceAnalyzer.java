package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.NamespaceName;
import rs.ac.bg.etf.pp1.ast.NamespaceSingle;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class NamespaceAnalyzer extends BaseAnalyzer{

    public NamespaceAnalyzer(SemAnalyzerCommon util, Logger log){
        super(util,log);
    }

    public void visit(NamespaceName namespace){
        if (Tab.find(namespace.getNamespaceName())!=Tab.noObj){
            report_error("Namespace sa imenom "+namespace.getNamespaceName()+" vec postoji.",namespace);
            return;
        }
        namespace.obj = Tab.insert(Obj.Prog, namespace.getNamespaceName(), Tab.noType);
        util.currentNamespaceName = namespace.getNamespaceName();
        util.inNamespaceScope=true;
    }

    public void visit(NamespaceSingle namespace){
        namespace.getNamespaceName().obj.setAdr(1); //1-namespace, 0-prog

        util.inNamespaceScope=false;
        util.currentNamespaceName="";
    }

}
