package model.expression;

import model.ADT.IDictionary;
import model.ADT.IHeap;
import model.exceptions.ExprException;
import model.value.Value;

public interface Exp {
    public Value eval(IDictionary<String,Value> tbl, IHeap<Value> heap) throws ExprException;

    Exp deepCopy();
}