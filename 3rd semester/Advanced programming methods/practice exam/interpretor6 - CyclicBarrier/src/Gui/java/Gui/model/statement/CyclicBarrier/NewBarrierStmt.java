package Gui.model.statement.CyclicBarrier;

import Gui.model.ADT.IBarrier;
import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.statement.IStmt;
import Gui.model.statement.IfStmt;
import Gui.model.type.IntType;
import Gui.model.type.Type;
import Gui.model.value.IntValue;
import Gui.model.value.Value;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStmt implements IStmt {
    Exp var;
    Exp exp;

    public NewBarrierStmt(Exp var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        IBarrier barrier = state.getBarrierTable();
        Value expValue = exp.eval(symTable, heap);

        if (expValue.getType().equals(new IntType())){
            int actualValue = ((IntValue)expValue).getValue();
            int location = barrier.getLocation();
            barrier.put(location, new Pair<>(actualValue, new ArrayList<>()));
            String varValue = var.toString();
            if (symTable.isDefined(varValue) && var.eval(symTable, heap).getType().equals(new IntType())){
                symTable.update(varValue, new IntValue(location));
            } else {
                throw new ExprException("Create Semaphore : variable is not defined or is not type int!");
            }

            lock.unlock();
        } else throw new ExprException("New barrier: wrong type of exp value!" );

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrierStmt(var.deepCopy(), exp.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = var.typecheck(typeEnv);
        if (typeVar.equals(new IntType())){
            Type typeExp = exp.typecheck(typeEnv);
            if (typeExp.equals(new IntType())){
                return typeEnv;
            } else
                throw new MyException("exp is not type int!");
        } else
            throw new MyException("var is not type int!");
    }

    @Override
    public String toString(){
        return "newBarrier(" + var + ", " + exp + ")";
    }
}
