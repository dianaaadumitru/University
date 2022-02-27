package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.expression.ValueExp;
import Gui.model.type.IntType;
import Gui.model.type.Type;
import Gui.model.value.IntValue;

public class WaitStmt implements IStmt{
    Exp exp;

    public WaitStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IntValue value = (IntValue) exp.eval(state.getSymTable(), state.getHeap());
        int time = value.getValue();
        if (time > 0)
            state.getStack().push(new CompStmt(new PrintStmt(new ValueExp(new IntValue(time))), new WaitStmt(new ValueExp(new IntValue(time - 1)))));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WaitStmt(exp.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        if (! exp.typecheck(typeEnv).equals(new IntType()))
            throw new MyException("expression is not Int Type!");
        return typeEnv;
    }

    @Override
    public String toString(){
        return "wait(" + exp + ")";
    }
}
