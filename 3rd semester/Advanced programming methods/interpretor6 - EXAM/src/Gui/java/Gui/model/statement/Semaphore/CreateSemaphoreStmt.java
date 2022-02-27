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

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStmt implements IStmt {
    Exp var;
    Exp exp1;

    public CreateSemaphoreStmt(Exp var, Exp exp1) {
        this.var = var;
        this.exp1 = exp1;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        ISemaphore semaphoreTable = state.getSemaphoreTable();
        //evaluate exp1
        Value expValue = exp1.eval(symTable, heap);

        if (expValue.getType().equals(new IntType())){
            //downcast to get the actual value
            int actualValue = ((IntValue)expValue).getValue();
            int location = semaphoreTable.getLocation();
            //add an empty list to the first free location
            semaphoreTable.put(location, new Pair<>(actualValue, new ArrayList<>()));
            String varValue = var.toString();

            if (symTable.isDefined(varValue) && var.eval(symTable, heap).getType().equals(new IntType())){
                //update symTable with the new free location
                symTable.update(varValue, new IntValue(location));
            } else {
                throw new ExprException("Create Semaphore : variable is not defined or is not type int!");
            }
            lock.unlock();
        } else throw new ExprException("Create semaphore: wrong type of exp value!" + expValue.getType().toString());

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStmt(var.deepCopy(), exp1.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = var.typecheck(typeEnv);
        if (typeVar.equals(new IntType())){
            Type typeExp = exp1.typecheck(typeEnv);
            if (typeExp.equals(new IntType())){
                return typeEnv;
            } else
                throw new MyException("Create Semaphore type checker: exp is not type int!");
        } else
            throw new MyException("Create Semaphore type checker: var is not type int!");
    }

    @Override
    public String toString(){
        return "createSemaphore(" + var + ", " + exp1 + ")";
    }
}
