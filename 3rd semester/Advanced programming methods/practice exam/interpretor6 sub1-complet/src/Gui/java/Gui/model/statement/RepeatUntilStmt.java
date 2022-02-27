package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.expression.NotExp;
import Gui.model.type.BoolType;
import Gui.model.type.Type;

public class RepeatUntilStmt implements IStmt{
    Exp exp2;
    IStmt stmt1;

    public RepeatUntilStmt(Exp exp2, IStmt stmt1) {
        this.exp2 = exp2;
        this.stmt1 = stmt1;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStmt st = new CompStmt(stmt1, new WhileStmt(new NotExp(exp2), stmt1));
        state.getStack().push(st);

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(exp2.deepCopy(), stmt1.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        IDictionary<String, Type> newEnv = typeEnv.clone();
        Type type = exp2.typecheck(newEnv);
        if (type.equals(new BoolType()))
            return stmt1.typecheck(typeEnv);
        else
            throw new MyException("Repeat until statement - condition expression is not bool type!");
    }

    @Override
    public String toString(){
        return "repeat " + stmt1 + " until " + exp2;
    }
}
