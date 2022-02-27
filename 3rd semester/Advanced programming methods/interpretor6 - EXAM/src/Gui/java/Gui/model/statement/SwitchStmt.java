package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.expression.RelationalExp;
import Gui.model.type.Type;

public class SwitchStmt implements IStmt{
    private Exp exp;
    private Exp exp1;
    private Exp exp2;
    private IStmt stmt1;
    private IStmt stmt2;
    private IStmt stmt3;

    public SwitchStmt(Exp exp, Exp exp1, Exp exp2, IStmt statement1, IStmt statement2, IStmt statement3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = statement1;
        this.stmt2 = statement2;
        this.stmt3 = statement3;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        IStmt st = new IfStmt(new RelationalExp(exp, exp1, "=="), stmt1,
                new IfStmt(new RelationalExp(exp, exp2, "=="), stmt2, stmt3));
        state.getStack().push(st);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(), exp1.deepCopy(), exp2.deepCopy(), stmt1.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        IDictionary<String, Type> newEnv = typeEnv.clone();
        Type type = exp.typecheck(newEnv);
        Type type1 = exp1.typecheck(newEnv);
        Type type2 = exp2.typecheck(newEnv);

        if (type.equals(type1) && type.equals(type2))
            return stmt1.typecheck(stmt2.typecheck(stmt3.typecheck(newEnv)));
        else
            throw new MyException("Switch statement - condition expressions are not the same type");

    }

    @Override
    public String toString(){
        return "switch(" + exp +") (case (" + exp1 + "): " + stmt1 + ") (case (" + exp2 + "): " + stmt2 +
                ") (default: " + stmt3;
    }
}
