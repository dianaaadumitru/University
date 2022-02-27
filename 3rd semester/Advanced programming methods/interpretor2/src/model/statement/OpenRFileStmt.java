package model.statement;

import model.ADT.IDictionary;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.MyException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.type.StringType;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class OpenRFileStmt implements IStmt {
    private Exp exp;

    public OpenRFileStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IDictionary<String, Value> symTable = state.getSymTable();
        Value val = exp.eval(symTable);
        if (val.getType().equals(new StringType())) {
            IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue stringVal = (StringValue) val;
            if (!fileTable.isDefined(stringVal)) {
                try {
                    Reader reader = new FileReader(stringVal.getValue());
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    fileTable.update(stringVal, bufferedReader);
                }
                catch (FileNotFoundException e) {
                    throw new StmtException(e.getMessage());
                }
            }
            else {
                throw new StmtException("The file is already in use!");
            }
        }
        else {
            throw new StmtException("Expression couldn't be evaluated to a string value in File Open!");
        }
        return null;
    }

    @Override
    public String toString() {
        return "Open(" + exp + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }
}