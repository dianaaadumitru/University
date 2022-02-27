package model.statement;

import model.ADT.IDictionary;
import model.PrgState;
import model.exceptions.MyException;
import model.exceptions.StmtException;
import model.type.Type;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StmtException {
        return null;
    }
    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}