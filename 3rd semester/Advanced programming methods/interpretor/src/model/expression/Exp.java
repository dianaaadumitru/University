package model.expression;

import model.ADT.IDictionary;
import model.exceptions.ExprException;
import model.value.Value;

public interface Exp {
    public Value eval(IDictionary<String,Value> tbl) throws ExprException;

    Exp deepCopy();
}