package rs.ac.bg.etf.pp1.genutils;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class CodeGenCommon {

    public int mainPc=-1;
    public boolean errorDetected = false;
    public int staticVarNum = 0;

    public ArrayList<Integer> waitingIf=new ArrayList<>();
    public Stack<ArrayList<Integer>> waitingAfterIf = new Stack<>();
    public int forCondAdrTrue = -1;
    public Stack<Integer> forCondAdrFalse = new Stack<>();
    public int forCondAdr = -1;
    public Stack<Integer> forAfterAdr = new Stack<>();
    public int lastAdrInIfTrue = -1;
    public Stack<ArrayList<Integer>> breakAddrs = new Stack<>();

    public int methodFormParsCnt = 0 ;
    public int methodVarsCnt = 0;
    public boolean inMethodDecl = false;
    public Obj currentMethodDecl = Tab.noObj;
    public boolean returnFound = false;
    public int lastReturnAdr = -1;

    public ArrayList<Obj> desObjects=new ArrayList<>();
    public ArrayList<SyntaxNode> syntaxNodes=new ArrayList<>();
    public boolean inDesEq = false;
    public boolean newArrayMade = false;
    public boolean newClassMade = false;
    public int lastDesPut = -1; //for array size

    public Obj currentClass = null;
    public String currentClassName = "";
    public boolean desIdent = false;
    public Obj lastDesIdent = Tab.noObj;
    public boolean lastArrayStmt = false; //!!!
    public boolean invokevirtual=false;
    public Obj invObj=Tab.noObj;
    public int tvfAdrForObj = -1;
    public int lastPatchAdr = -1; //for static initializers

    public Collection<Obj> members = null;


}
