package model.expression;

import model.ADT.IDictionary;
import model.exceptions.ExprException;
import model.value.Value;

public class ValueExp implements Exp {
    Value e;

    public ValueExp(Value deepCopy) {
        e = deepCopy;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl) throws ExprException {
        return e;
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e.deepCopy());
    }
}