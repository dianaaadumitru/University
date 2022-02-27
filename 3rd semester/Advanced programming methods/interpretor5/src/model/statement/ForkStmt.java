package model.statement;

import model.ADT.IDictionary;
import model.ADT.MyStack;
import model.PrgState;
import model.exceptions.MyException;
import model.exceptions.StmtException;
import model.type.Type;

public class ForkStmt implements IStmt{
    private IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        return new PrgState(new MyStack<>(), state.getSymTable().clone(), state.getOutConsole(), state.getFileTable(),
                state.getHeap(), statement);
    }


    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}
