package model.expression;

import model.ADT.IDictionary;
import model.exceptions.ExprException;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.Value;

public class RelationalExp implements Exp {
    private Exp exp1;
    private Exp exp2;
    private int op; // 1 <, 2 <=, 3 ==, 4 !=, 5 >, 6 >=

    public RelationalExp(Exp exp1, Exp exp2, String op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        if(op == "<")
            this.op = 1;
        else if(op == "<=")
            this.op = 2;
        else if(op == "==")
            this.op = 3;
        else if(op == "!=")
            this.op = 4;
        else if(op == ">")
            this.op = 5;
        else if(op == ">=")
            this.op = 6;
    }

    @Override
    public Value eval(IDictionary<String, Value> symTable) throws ExprException {
        Value val1, val2;
        val1 = exp1.eval(symTable);
        val2 = exp2.eval(symTable);
        if (val1.getType().equals(new IntType()) && val2.getType().equals(new IntType())) {
            IntValue intVal1, intVal2;
            intVal1 = (IntValue) val1;
            intVal2 = (IntValue) val2;
            int x = intVal1.getValue();
            int y = intVal2.getValue();
            switch (op) {
                case 1:
                    return new BoolValue(x < y);
                case 2:
                    return new BoolValue(x <= y);
                case 3:
                    return new BoolValue(x == y);
                case 4:
                    return new BoolValue(x != y);
                case 5:
                    return new BoolValue(x > y);
                case 6:
                    return new BoolValue(x >= y);
            }
        }
        else {
            throw new ExprException("At least one operand is not an integer");
        }

        return new BoolValue(false);
    }

    @Override
    public String toString() {
        String s = "";
        switch (op) {
            case 1:
                s = "<";
                break;
            case 2:
                s = "<=";
                break;
            case 3:
                s = "==";
                break;
            case 4:
                s = "!=";
                break;
            case 5:
                s = ">";
                break;
            default:
                s = ">=";
        }
        return exp1 + s + exp2;
    }

    @Override
    public Exp deepCopy() {
        return switch (op) {
            case 1 -> new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), "<");
            case 2 -> new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), "<=");
            case 3 -> new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), "==");
            case 4 -> new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), "!=");
            case 5 -> new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), ">");
            case 6 -> new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), ">=");
            default -> new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), "<");
        };
    }
}