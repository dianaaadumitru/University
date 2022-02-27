package Gui.model.statement;

import Gui.model.ADT.IDictionary;
import Gui.model.PrgState;
import Gui.model.exceptions.ADTException;
import Gui.model.exceptions.ExprException;
import Gui.model.exceptions.MyException;
import Gui.model.exceptions.StmtException;
import Gui.model.expression.Exp;
import Gui.model.expression.RelationalExp;
import Gui.model.expression.VarExp;
import Gui.model.type.IntType;
import Gui.model.type.Type;


public class ForStmt implements IStmt{
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;
    private IStmt statement;
    String var;

    public ForStmt(Exp exp1, Exp exp2, Exp exp3, IStmt statement, String var) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.statement = statement;
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException {
        if (state.getSymTable().search(var) == null)
            throw new StmtException("variable " + var + "was not declared!");

        IStmt st =  new CompStmt(new AssignStmt(var, exp1),new WhileStmt(new RelationalExp(new VarExp(var),exp2,"<"),
                new CompStmt(statement,new AssignStmt(var,exp3)))
        );
        state.getStack().push(st);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(exp2.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), statement.deepCopy(), var);
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws MyException {
        IDictionary<String, Type> newEnv = typeEnv.clone();
        Type type1 = exp1.typecheck(newEnv);
        Type type2 = exp2.typecheck(newEnv);
        Type type3 = exp3.typecheck(newEnv);
        Type type4 = typeEnv.lookup(var);
        if (type1.equals(new IntType()) && type2.equals(new IntType()) && type3.equals(new IntType()) && type4.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("For statement - condition expressions are not int");
    }

    @Override
    public String toString(){
        return "for(" +var + "=" + exp1 + "; "+ var + "<" + exp2 + "; " + var + "=" + exp3 + ") {" + statement + "}";
    }
}
