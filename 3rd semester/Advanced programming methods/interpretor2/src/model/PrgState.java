package model;

import model.ADT.*;
import model.statement.IStmt;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, Value> symTable;
    private IList<Value> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IStmt originalProgram; //optional field, but good to have

    public PrgState(IStack<IStmt> stk, IDictionary<String, Value> symtbl, IList<Value> ot, IDictionary<StringValue, BufferedReader> fT, IStmt prg) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = fT;
        originalProgram = prg;
        stk.push(prg);
    }

    public PrgState(IStack<IStmt> stack, MyDictionary<String, Value> stringValueMyDictionary, MyList<Value> valueMyList) {
        exeStack = stack;
        symTable = stringValueMyDictionary;
        out = valueMyList;
    }

    public IStack<IStmt> getStack() {
        return exeStack;
    }

    public IDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public IStmt getOriginalProgram(){
        return originalProgram;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Program state\n");
        str.append("Exe Stack: ").append(exeStack).append(" \n");
        str.append("Sym Table: ").append(symTable).append(" \n");
        str.append("Output Console: ").append(out).append(" \n");
        str.append("File Table: ").append(fileTable).append(" \n");
        return str.toString();
    }

    public void setExeStack(IStack<IStmt> stack) {
        exeStack = stack;
    }

    public void setSymTable(IDictionary<String, Value> table) {
        symTable = table;
    }

    public IList<Value> getOutConsole() {
        return out;
    }

    public void setOutConsole(IList<Value> outConsole) {
        out = outConsole;
    }
}