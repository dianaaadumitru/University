package Gui.model.statement.LatchTable;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.ADT.ILatchTable;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.statement.IStmt;
import Gui.model.type.IntType;
import Gui.model.type.Type;
import Gui.model.value.IntValue;
import Gui.model.value.Value;

import java.util.concurrent.locks.ReentrantLock;

public class newLatchStmt implements IStmt {
    Exp var;
    Exp exp;

    public newLatchStmt(Exp var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        ILatchTable latchTable = state.getLatchTable();
        Value expValue = exp.eval(symTable, heap);

        if (expValue.getType().equals(new IntType())){
            int actualValue = ((IntValue)expValue).getValue();
            int location = state.getLatchTable().getLocation();
            state.getLatchTable().put(location, actualValue);
//            latchTable.put(location, actualValue);
            String varValue = var.toString();

            if (symTable.isDefined(varValue) && var.eval(symTable, heap).getType().equals(new IntType())){
                symTable.update(varValue, new IntValue(location));
            } else throw new ExprException("New Latch: variable is not defined or is not type int!");
            lock.unlock();
        }else throw new ExprException("New Latch: wrong type of exp value!");

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new newLatchStmt(var.deepCopy(), exp.deepCopy());
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
        return "newLatch(" + var + ", " + exp + ")";
    }
}
