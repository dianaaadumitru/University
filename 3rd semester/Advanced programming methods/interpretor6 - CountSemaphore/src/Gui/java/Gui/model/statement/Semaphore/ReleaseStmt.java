package Gui.model.statement.Semaphore;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.ISemaphore;
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
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStmt implements IStmt {
    Exp var;

    public ReleaseStmt(Exp var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        ISemaphore semaphoreTable = state.getSemaphoreTable();
        if (symTable.isDefined(var.toString())){
            int foundIndex = ((IntValue) symTable.lookup(var.toString())).getValue();
            if (semaphoreTable.isDefined(foundIndex)){
                Pair pair = (Pair) semaphoreTable.getSemaphore().get(foundIndex);
                List<Integer> list = (List<Integer>) pair.getValue();
                int nr = (int) pair.getKey();
                if (list.contains(state.getStateID())){
                    list.remove(Integer.valueOf(state.getStateID()));
                }
            } else throw new ExprException("Release Stmt -index not in sem table");

        } else throw new ExprException("Release Stmt - var is not defined!");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReleaseStmt(var.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type= var.typecheck(typeEnv);
        if (type.equals(new IntType()))
            return typeEnv;
        else throw new MyException("Release type checker: var not int type!");
    }

    @Override
    public String toString(){
        return "Release(" + var + ")";
    }
}
