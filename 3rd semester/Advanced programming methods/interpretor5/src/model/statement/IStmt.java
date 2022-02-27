package model.statement;

import model.ADT.IDictionary;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.MyException;
import model.exceptions.StmtException;
import model.type.Type;

public interface IStmt {
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException; // execution method for a statement

    IStmt deepCopy();
    IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException;
}