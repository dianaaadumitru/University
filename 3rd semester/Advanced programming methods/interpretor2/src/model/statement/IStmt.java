package model.statement;

import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;

public interface IStmt {
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException; // execution method for a statement

    IStmt deepCopy();
}