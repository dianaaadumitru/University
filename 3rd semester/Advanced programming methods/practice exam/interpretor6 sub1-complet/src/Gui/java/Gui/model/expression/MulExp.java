package Gui.model.expression;

import Gui.model.ADT.IDictionary;
import Gui.model.ADT.IHeap;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.type.IntType;
import Gui.model.type.Type;
import Gui.model.value.Value;

public class MulExp implements Exp{
    Exp exp1;
    Exp exp2;

    public MulExp(Exp exp1, Exp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Value> heap) throws ExprException {
        //((exp1*exp2)-(exp1+exp2))
        ArithExp result = new ArithExp(new ArithExp(exp1, exp2, '*'), new ArithExp(exp1, exp2, '+'), '-');
        return result.eval(tbl, heap);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typ1 = exp1.typecheck(typeEnv), typ2 = exp2.typecheck(typeEnv);
        if (typ1.equals(new IntType()))
            if (typ2.equals(new IntType()))
                return new IntType();
            else
                throw new MyException("Second operand is not an integer");
        else
            throw new MyException("First operand is not an integer");
    }

    @Override
    public Exp deepCopy() {
        return new MulExp(exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public String toString(){
        return "MUL(" + exp1 + "," + exp2 + ")";
    }
}
