package controller;

import model.ADT.IStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.MyException;
import model.exceptions.StmtException;
import model.statement.*;
import repository.IRepo;

public class Controller {
    private IRepo repository;

    public Controller(IRepo repo) {
        this.repository = repo;
    }

    public PrgState oneStep(PrgState state) throws MyException, ADTException, StmtException, ExprException {
        IStack<IStmt> stack = state.getStack();
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repository.getCrtPrg();
        System.out.println(prg);

        while (!prg.getStack().isEmpty()) {
            try {
                oneStep(prg);
                System.out.println(prg);
            } catch (MyException | ADTException | StmtException | ExprException exception) {
                throw new MyException(exception.getMessage());
            }
        }
    }
}