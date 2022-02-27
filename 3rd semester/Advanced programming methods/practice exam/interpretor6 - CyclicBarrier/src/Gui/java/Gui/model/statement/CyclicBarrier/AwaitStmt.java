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
import Gui.model.type.IntType;
import Gui.model.type.Type;
import Gui.model.value.IntValue;
import Gui.model.value.Value;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt {
    Exp var;

    public AwaitStmt(Exp var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        IBarrier semaphoreTable = state.getBarrierTable();

        if (symTable.isDefined(var.toString())) {
            int foundIndex = ((IntValue) symTable.lookup(var.toString())).getValue();
            if (semaphoreTable.isDefined(foundIndex)) {
                Pair pair = (Pair) semaphoreTable.getBarrier().get(foundIndex);
                List<Integer> list = (List<Integer>) pair.getValue();
                int nr = (int) pair.getKey();
                int length = list.size();
                if (nr > length){
                    if (list.contains(state.getStateID())){
                        state.getStack().push(new AwaitStmt(var));
                    } else {
                        list.add(state.getStateID());
                        state.getBarrierTable().put(foundIndex, new Pair<>(nr, list));
                    }

                }

            } else throw new ExprException("Await - index not in sym table");

        } else throw new ExprException("Await Stmt - var is not defined!");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type= var.typecheck(typeEnv);
        if (type.equals(new IntType()))
            return typeEnv;
        else throw new MyException("Await type checker: var not int type!");
    }

    @Override
    public String toString(){
        return "await(" + var + ")";
    }
}
