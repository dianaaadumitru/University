package repository;

import model.PrgState;
import model.exceptions.MyException;
import model.statement.IStmt;

import java.io.IOException;
import java.util.List;

public interface IRepo {
    public List<PrgState> getPrgList();
    PrgState getCrtPrg();
    IStmt getOriginalProgram();
    void addState(PrgState state);
    void printPrgState(PrgState prgState) throws MyException, IOException;
    void setPrgList(List<PrgState> list);
}