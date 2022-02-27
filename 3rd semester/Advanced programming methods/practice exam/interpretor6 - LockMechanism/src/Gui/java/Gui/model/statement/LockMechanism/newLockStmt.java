package Gui.model.statement.LockMechanism;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.ADT.ILock;
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

public class newLockStmt implements IStmt {
    Exp var;

    public newLockStmt(Exp var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        ILock lockMech = state.getLockTable();

        int f = lockMech.getFreeAddress();
        lockMech.put(f, -1);
        String varValue = var.toString();
        if (symTable.isDefined(varValue) && var.eval(symTable, heap).getType().equals(new IntType())){
                symTable.update(varValue, new IntValue(f));
        } else throw new ExprException("New Lock : variable is not defined or is not type int!");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new newLockStmt(var.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type= var.typecheck(typeEnv);
        if (type.equals(new IntType()))
            return typeEnv;
        else throw new MyException("newLock type checker: var not int type!");
    }

    @Override
    public String toString(){
        return "newLock(" + var + ")";
    }
}
