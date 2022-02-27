package model.statement;

import model.ADT.*;
import model.PrgState;
import model.exceptions.StmtException;
import model.type.Type;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.util.Map;

public class ForkStmt implements IStmt{
    private IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
//        IStack<IStmt> stk = state.getStack();
//        IDictionary<String, Value> symTable = state.getSymTable();
//        IHeap<Value> heap = state.getHeap();
//        IList<Value> outList = state.getOutConsole();
//        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
//
//        MyStack<IStmt> newStk = new MyStack<IStmt>();
//        MyDictionary<String, Value> newSymTable = new MyDictionary<String, Value>();
//        for (Map.Entry<String, Value> entry: symTable.getContent().entrySet()) {
//            newSymTable.update(new String(entry.getKey()), entry.getValue().deepCopy());
//        }
//        return new PrgState(newStk, newSymTable, outList, fileTable, heap, statement);
        return new PrgState(new MyStack<>(), state.getSymTable().clone(), state.getOutConsole(), state.getFileTable(), state.getHeap(), statement);
    }


    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}
