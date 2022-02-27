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

public class countDownStmt implements IStmt {
    Exp var;

    public countDownStmt(Exp var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        ILatchTable latchTable = state.getLatchTable();
        int foundIndex = ((IntValue) symTable.lookup(var.toString())).getValue();
        if (symTable.isDefined(var.toString()) && latchTable.isDefined(foundIndex)){
            int count = state.getLatchTable().getContent().get(foundIndex);
            if (count > 0){
                state.getLatchTable().put(foundIndex, count - 1);
                state.getOutConsole().add(new IntValue(state.getStateID()));
            }
        } else throw new ExprException("Await Stmt - var is not defined or is not in sym Table!");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new countDownStmt(var.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type= var.typecheck(typeEnv);
        if (type.equals(new IntType()))
            return typeEnv;
        else throw new MyException("countDown type checker: var not int type!");
    }

    @Override
    public String toString(){
        return "countDown(" + var + ")";
    }
}
