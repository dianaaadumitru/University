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

public class LockStmt implements IStmt {
    Exp var;

    public LockStmt(Exp var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        ILock lockMech = state.getLockTable();

        if (symTable.isDefined(var.toString())){
            if (var.eval(symTable, heap).getType().equals(new IntType())){
                int foundIndex = ((IntValue) symTable.lookup(var.toString())).getValue();
                if (lockMech.containsKey(foundIndex)){
                    if (lockMech.get(foundIndex) == -1)
                        lockMech.update(foundIndex, state.getStateID());
                    else
                        state.getStack().push(this);
                } else throw new ExprException("Lock Stmt - Index not in LockTable!");
            } else throw new ExprException("Lock Stmt - var is not int type!");
        } else throw new ExprException("Lock Stmt - var is not defined!");
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new LockStmt(var.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type= var.typecheck(typeEnv);
        if (type.equals(new IntType()))
            return typeEnv;
        else throw new MyException("Lock type checker: var not int type!");
    }

    @Override
    public String toString(){
        return "lock(" + var + ")";
    }
}
