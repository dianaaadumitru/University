package model.statement;

import model.ADT.IList;
import model.ADT.IStack;
import model.PrgState;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.value.Value;

public class PrintStmt implements IStmt{
    Exp exp;

    public PrintStmt(Exp deepCopy) {
        exp = deepCopy;
    }

    @Override
    public String toString(){
        return "print(" + exp.toString() +")";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException {
        IStack<IStmt> stack = state.getStack();
        IList<Value> outConsole = state.getOutConsole();
        outConsole.add(exp.eval(state.getSymTable()));
        state.setExeStack(stack);
        state.setOutConsole(outConsole);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

}