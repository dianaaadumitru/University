package model.expression;

import model.ADT.IDictionary;
import model.ADT.IHeap;
import model.exceptions.ExprException;
import model.value.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String s) {
        id = s;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        return tbl.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(new String(id));
    }
}