package rs.ac.bg.etf.pp1.genutils;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;

import java.util.ArrayList;

public class ConditionGen extends BaseGenerator {

    public ConditionGen(CodeGenCommon util, Logger log) {
        super(util, log);
    }

    public void visit (ConditionLast cond){ // CondTerm
        if (cond.getParent() instanceof IfCondOk) fixupIf();
        else fixupAfterIf(false);
    }

    public void visit (ConditionMore cond){ // Conditoin or CondTerm
        if (cond.getParent() instanceof IfCondOk)  fixupIf();
        else fixupAfterIf(false);
    }

    public void visit (CondFactSingle cond){ // Expr
        Code.loadConst(0);

        if (cond.getParent() instanceof  ForCondFactNotEmpty){
            Code.put(Code.dup2);

            util.forCondAdrTrue=Code.pc+1;
            Code.putFalseJump(Code.eq, 0);

            util.forCondAdrFalse.push(Code.pc+1);
            Code.putFalseJump(Code.ne, 0);

            Code.fixup(util.forCondAdrTrue);
            Code.put(Code.pop);
            Code.put(Code.pop);

            util.forCondAdrTrue=Code.pc+1;
            Code.putJump(0);
            util.forAfterAdr.push(Code.pc);
            return;
        }

        if (cond.getParent().getParent() instanceof ConditionMore
                || cond.getParent().getParent() instanceof ConditionLast){
            Code.put(Code.dup2);

            int adrTrue = Code.pc+1;
            Code.putFalseJump(Code.eq, 0);

            int adrFalse = Code.pc+1;
            Code.putFalseJump(Code.ne, 0);
            util.waitingAfterIf.peek().add(adrFalse);

            Code.fixup(adrTrue);
            Code.put(Code.pop);
            Code.put(Code.pop);

            util.waitingIf.add(Code.pc+1);
            Code.putJump(0);

            return;
        }

        int adrFalse = Code.pc+1;
        Code.putFalseJump(Code.ne, 0);
        util.waitingAfterIf.peek().add(adrFalse);
    }

    public void visit (CondFactMultiple cond){ // Expr Relop Expr
        int op=getoOp(cond.getRelop());

        if (cond.getParent() instanceof  ForCondFactNotEmpty){
            Code.put(Code.dup2);

            util.forCondAdrTrue=Code.pc+1;
            Code.putFalseJump(Code.inverse[op], 0);

            util.forCondAdrFalse.push(Code.pc+1);
            Code.putFalseJump(op, 0);

            Code.fixup(util.forCondAdrTrue);
            Code.put(Code.pop);
            Code.put(Code.pop);

            util.forCondAdrTrue=Code.pc+1;
            Code.putJump(0);
            util.forAfterAdr.push(Code.pc);
            return;
        }

        if (cond.getParent().getParent() instanceof ConditionMore
            || cond.getParent().getParent() instanceof ConditionLast){
            Code.put(Code.dup2);

            int adrTrue = Code.pc+1;
            Code.putFalseJump(Code.inverse[op], 0);

            int adrFalse = Code.pc+1;
            Code.putFalseJump(op, 0);
            util.waitingAfterIf.peek().add(adrFalse);

            Code.fixup(adrTrue);
            Code.put(Code.pop);
            Code.put(Code.pop);

            util.waitingIf.add(Code.pc+1);
            Code.putJump(0);

            return;
        }

        int adrFalse = Code.pc+1;
        Code.putFalseJump(op, 0); //videcemo treba li op
        util.waitingAfterIf.peek().add(adrFalse);
    }

    public void visit (IfWord i){
        util.waitingAfterIf.push(new ArrayList<>());
    }
    public void visit (StatementIf stmt){
        fixupAfterIf(true);
    }
    public void visit (ElseBegin e){
        util.lastAdrInIfTrue=Code.pc+1;
        Code.putJump(0);
        fixupAfterIf(true);
    }
    public void visit (StatementIfElse stmt){
        Code.fixup(util.lastAdrInIfTrue);
    }

    public void visit (ForWord f){
        util.breakAddrs.push(new ArrayList<>());
    }

    public void visit (ForAfterNotEmpty f){
        Code.putJump(util.forCondAdr);
        Code.fixup(util.forCondAdrTrue);
    }
    public void visit (ForAfterEmpty f){
        Code.putJump(util.forCondAdr);
        Code.fixup(util.forCondAdrTrue);
    }

    public void visit (ForInitEmpty f){
        util.forCondAdr=Code.pc;
    }
    public void visit (ForInitNotEmpty f){
        util.forCondAdr=Code.pc;
    }

    public void visit (StatementFor f){
        Code.putJump(util.forAfterAdr.pop());
        if (util.forCondAdrFalse.size()>0)
            Code.fixup(util.forCondAdrFalse.pop());

        if (util.breakAddrs.size()>0) {
            for (Integer adr : util.breakAddrs.peek()) {
                Code.fixup(adr);
            }
            util.breakAddrs.pop();
        }
    }

    //ovo jos ne znam radi li nisam testirala, dodala sam naknadno
    public void visit (ForCondFactEmpty f){
        util.forCondAdrTrue=Code.pc+1;
        Code.putJump(0);
        util.forAfterAdr.push(Code.pc);
    }

    public void visit(StatementBreak stmt){
        util.breakAddrs.peek().add(Code.pc+1);
        Code.putJump(0);
    }

    public void visit(StatementContinue stmt){
        Code.putJump(util.forAfterAdr.peek());
    }

    public void fixupIf(){
        for (Integer adr: util.waitingIf) {
            Code.fixup(adr);
        }
        util.waitingIf.clear();
    }
    public void fixupAfterIf(boolean clear){
        if (util.waitingAfterIf.size()==0) return;
        for (Integer adr: util.waitingAfterIf.peek()) {
            Code.fixup(adr);
        }
        util.waitingAfterIf.peek().clear();
        if (clear) util.waitingAfterIf.pop();
    }

    public int getoOp(Relop r){
        if (r instanceof RelopNeq) return Code.ne;
        if (r instanceof RelopEq) return Code.eq;
        if (r instanceof RelopGt) return Code.gt;
        if (r instanceof RelopGte) return Code.ge;
        if (r instanceof RelopLt) return Code.lt;
        if (r instanceof RelopLte) return Code.le;
        return -1;
    }
}