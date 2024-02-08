package rs.ac.bg.etf.pp1.semutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodName;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class SemAnalyzerCommon {

    public boolean errorDetected = false;
    public boolean mainFound = false;
    public String programName = "";
    public int nVars = 0;

    public String currentNamespaceName = "";
    public String currentNamespaceNameUsed = "";
    public boolean incNamespace = false;

    public String currentClassName = "";
    public boolean extendsClass = false;
    public String currentParentClass = "";
    public Struct currentClassStruct = null;
    public boolean staticBlock = false;
    public ArrayList<Obj> currentClassMembers = new ArrayList<>();
    public Obj thisobj= Tab.noObj;
    public ArrayList<Obj> thisObjects = new ArrayList<>();

    public String currentDesignatorName = ""; //ima onaj stacking problem sa ovim ..MOZDA
    public int currentDesignatorKind = -1; //i sa ovim ..MOZDA
    public int currentDesignatorType = -1; //i sa ovim ..MOZDA
    public Struct currentDesignatorStruct=null;
    public int isArrayElem = 0;
    public boolean isClassField = false;


    public  Obj currentVarType = null;
    public int currentVarNumProg = 0;
    public int currentVarNumMethod = 0;
    public int currentFldNum = 0;

    public HashMap<String, Set<String>> static_fields = new HashMap<>();
    public HashMap<String, Set<Obj>> static_fields_to_add_globally=new HashMap<>();

    public Stack<Integer> currentMethodArgStack = new Stack<>();
    public Obj currentMethodCall = null;
    public int methodElemNum=0;

    public String currentConstName = "";
    public Obj currentConstType = null;

    public Struct currentMethodType = null;
    public String currentMethodName = "";
    public MethodName methodName = null;

    public boolean inProgramScope = false;
    public boolean inMethodScope = false;
    public boolean inNamespaceScope = false;
    public boolean inClassScope = false;

    public boolean inFor = false;

    public ArrayList<Obj> arrayAssignmentObjs = new ArrayList<>();

    public boolean termLast = false;
}
