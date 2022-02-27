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

public class awaitStmt implements IStmt {
    Exp var;

    public awaitStmt(Exp var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        int foundIndex = ((IntValue) symTable.lookup(var.toString())).getValue();

        if (symTable.isDefined(var.toString()) && state.getLatchTable().isDefined(foundIndex)){
            state.getStack().push(new awaitStmt(var));
        } else throw new ExprException("Await Stmt - var is not defined or is not in sym Table!");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new awaitStmt(var.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type= var.typecheck(typeEnv);
        if (type.equals(new IntType()))
            return typeEnv;
        else throw new MyException("await type checker: var not int type!");
    }

    @Override
    public String toString(){
        return "await(" + var + ")";
    }
}
