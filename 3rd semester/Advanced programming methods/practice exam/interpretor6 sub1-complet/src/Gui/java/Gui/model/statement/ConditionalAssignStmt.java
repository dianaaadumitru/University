package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.type.Type;

//v=(exp1)?exp2:exp3
public class ConditionalAssignStmt implements IStmt{
    Exp exp1;
    Exp exp2;
    Exp exp3;
    String var;

    public ConditionalAssignStmt(Exp exp1, Exp exp2, Exp exp3, String var) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        if (state.getSymTable().search(var) == null)
            throw new StmtException("variable " + var + "was not declared!");
        IStmt st = new IfStmt(exp1, new AssignStmt(var, exp2), new AssignStmt(var, exp3));
        state.getStack().push(st);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ConditionalAssignStmt(exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), var);
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return exp1 + "?" + exp2 + ":" + exp3;
    }
}
