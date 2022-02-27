package Gui.model.statement.ToySemaphore;

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

public class NewSemaphore implements IStmt {
    Exp var;
    Exp exp1;
    Exp exp2;

    public NewSemaphore(Exp var, Exp exp1, Exp exp2) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        IDictionary<String, Value> symTable = state.getSymTable();
        IHeap<Value> heap = state.getHeap();
        ISemaphore semaphoreTable = state.getSemaphoreTable();
        Value expValue1 = exp1.eval(symTable, heap);
        Value expValue2 = exp2.eval(symTable, heap);

        if (expValue1.getType().equals(new IntType()) && expValue2.getType().equals(new IntType())){
            int actualValue = ((IntValue)expValue1).getValue();
            int location = semaphoreTable.getLocation();
            semaphoreTable.put(location, new Pair<>(actualValue, new ArrayList<>()));
            String varValue = var.toString();

            if (symTable.isDefined(varValue) && var.eval(symTable, heap).getType().equals(new IntType())){
                symTable.update(varValue, new IntValue(location));
            } else {
                throw new ExprException("Create Semaphore : variable is not defined or is not type int!");
            }
            lock.unlock();
        } else throw new ExprException("Create semaphore: wrong type of exp value!");


        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewSemaphore(var.deepCopy(), exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = var.typecheck(typeEnv);
        if (typeVar.equals(new IntType())){
            Type typeExp1 = exp1.typecheck(typeEnv);
            Type typeExp2 = exp2.typecheck(typeEnv);
            if (typeExp1.equals(new IntType()) && typeExp2.equals(new IntType())){
                return typeEnv;
            } else
                throw new MyException("exp1 or exp2 is not type int!");
        } else
            throw new MyException("var is not type int!");
    }

    @Override
    public String toString(){
        return "newSemaphore(" + var + "," + exp1 +"," + exp2 + ")";
    }
}
