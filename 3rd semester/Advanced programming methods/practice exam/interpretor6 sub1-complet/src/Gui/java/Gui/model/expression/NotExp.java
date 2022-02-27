package Gui.model.expression;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.type.BoolType;
import Gui.model.type.Type;
import Gui.model.value.BoolValue;
import Gui.model.value.IntValue;
import Gui.model.value.Value;

public class NotExp implements Exp{
    Exp exp;

    public NotExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        Value val = exp.eval(tbl, heap);
        if (val.getType().equals(new BoolType())) {
            BoolValue i1 = (BoolValue)val;
            boolean x = i1.getValue();
            return new BoolValue(!x);
        } else {
            throw new ExprException("Operand is not a boolean");
        }
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        if (exp.equals(new BoolType()))
            return new BoolType();
        else
            throw new MyException("Operand is not a boolean");
    }

    @Override
    public Exp deepCopy() {
        return new NotExp(exp.deepCopy());
    }

    @Override
    public String toString(){
        return "!" + exp;
    }
}
