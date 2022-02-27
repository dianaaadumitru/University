package model.statement;

import model.ADT.IStack;
import model.PrgState;
import model.exceptions.StmtException;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt deepCopy, IStmt deepCopy1) {
        first = deepCopy;
        snd = deepCopy1;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        IStack<IStmt> stack = state.getStack();
        stack.push(snd);
        stack.push(first);
//        state.setExeStack(stack);
        return null;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), snd.deepCopy());
    }
}