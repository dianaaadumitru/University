package repository;

import model.PrgState;
import model.exceptions.MyException;
import model.statement.IStmt;

import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepo {
    private List<PrgState> states;
    private IStmt originalProgram;

    public Repository(PrgState prgState) throws MyException {
        this.originalProgram = prgState.getOriginalProgram();
        states = new LinkedList<>();
    }

    public Repository() {
        states = new LinkedList<>();
    }

    @Override
    public List<PrgState> getPrgList() {
        return states;
    }


    @Override
    public PrgState getCrtPrg() {
        PrgState state = states.get(0);
        return state;
    }

    @Override
    public IStmt getOriginalProgram() {
        return originalProgram;
    }


    @Override
    public void addState(PrgState state) {
        states.add(state);
    }
}