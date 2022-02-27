package model.statement;

import model.ADT.IDictionary;
import model.ADT.IHeap;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.expression.VarExp;
import model.type.RefType;
import model.value.RefValue;
import model.value.Value;

import java.sql.Ref;

public class WriteHeapStmt implements IStmt {
    private String varName;
    private Exp exp;

    public WriteHeapStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IDictionary<String, Value> symTable = state.getSymTable();;
        IHeap<Value> heap = state.getHeap();

        if (symTable.isDefined(varName)){
            if (symTable.lookup(varName).getType() instanceof RefType){
                RefValue refValue = (RefValue) symTable.lookup(varName);
                if (heap.contains(refValue.getAddress())){
                    Value val = exp.eval(symTable, heap);
                    if (symTable.lookup(varName).getType().equals(new RefType(val.getType()))){
                        int address = refValue.getAddress();
                        heap.update(address, val);
                    } else {
                        throw  new StmtException("The pointing variable has a different type than the evaluated expression!");
                    }
                } else {
                    throw new StmtException("The address to which " + varName +" points to it's not in the heap!");
                }
            } else {
                throw new StmtException(varName + " is not a reference variable!");
            }
        } else {
            throw new StmtException(varName + " is not defined");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(varName, exp.deepCopy());
    }

    @Override
    public String toString(){
        return "wH(" + varName + ", " + exp +")";
    }
}
