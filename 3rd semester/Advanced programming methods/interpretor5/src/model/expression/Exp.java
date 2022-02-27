package model.expression;

import model.ADT.IDictionary;
import model.ADT.IHeap;
import model.exceptions.ExprException;
import model.exceptions.MyException;
import model.type.Type;
import model.value.Value;

public interface Exp {
    public Value eval(IDictionary<String,Value> tbl, IHeap<Value> heap) throws ExprException;
    Type typecheck(IDictionary<String, Type> typeEnv) throws MyException;
    Exp deepCopy();
}