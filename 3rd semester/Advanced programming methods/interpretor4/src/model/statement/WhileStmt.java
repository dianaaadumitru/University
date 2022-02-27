package model.statement;

import model.ADT.IDictionary;
import model.ADT.IStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.StmtException;
import model.expression.Exp;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.Value;

public class WhileStmt implements IStmt{
    private Exp exp;
    private IStmt statement;

    public WhileStmt(Exp exp, IStmt statement) {
        this.exp = exp;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStack<IStmt> stk = state.getStack();
        IDictionary<String, Value> symTable = state.getSymTable();
        Value val = exp.eval(symTable, state.getHeap());
        if (val.getType().equals(new BoolType())){
            BoolValue boolValue = (BoolValue) val;
            if (boolValue.getValue()){
//                stk.push(this.deepCopy());
//                stk.push(statement);
                state.getStack().push(new WhileStmt(exp, statement));
                state.getStack().push(statement);
            }
        } else {
            throw new StmtException("While condition is not boolean!");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "while (" + exp + ") {" + statement + "}";
    }
}
