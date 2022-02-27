package Gui.model.statement.Semaphore;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
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

public class AcquireStmt implements IStmt {
    Exp var;

    public AcquireStmt(Exp var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        ISemaphore semaphoreTable = state.getSemaphoreTable();

        if (symTable.isDefined(var.toString())&& var.eval(symTable, heap).getType().equals(new IntType())){
            //get value from symTable
            int foundIndex = ((IntValue) symTable.lookup(var.toString())).getValue();
            if (semaphoreTable.isDefined(foundIndex)){
                //get the pair having the key foundIndex
                Pair pair = (Pair) semaphoreTable.getSemaphore().get(foundIndex);
                //get the list of that pair
                List<Integer> list = (List<Integer>) pair.getValue();
                //get key of the pair
                int nr = (int) pair.getKey();
                int length = list.size();
                if (nr > length){
                    if (!list.contains(state.getStateID()))
                        //add id of current PrgState(nr) to the list
                        list.add(state.getStateID());
                    //put everything in the semaphore
                    state.getSemaphoreTable().put(foundIndex, new Pair<>(nr, list));
                } else
                    //push the statement on the stack
                    state.getStack().push(new AcquireStmt(var));
            } else throw new ExprException("Acquire-index not in semaphore table");

        } else throw new ExprException("Acquire Stmt - var is not in symTable or doesn't have type int!");

        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireStmt(var.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type type= var.typecheck(typeEnv);
        if (type.equals(new IntType()))
            return typeEnv;
        else throw new MyException("Acquire type checker: var not int type!");
    }

    @Override
    public String toString(){
        return "acquire(" + var + ")";
    }
}
