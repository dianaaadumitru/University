package controller;

import model.ADT.IStack;
import model.PrgState;
import model.exceptions.ADTException;
import model.exceptions.ExprException;
import model.exceptions.MyException;
import model.exceptions.StmtException;
import model.statement.IStmt;
import model.value.RefValue;
import model.value.Value;
import repository.IRepo;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.*;

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

    public void allStep() throws MyException, IOException {
        PrgState prg = repository.getCrtPrg();
        System.out.println(prg);
        repository.printPrgState(prg);

        while (!prg.getStack().isEmpty()) {
            try {
                oneStep(prg);
                System.out.println(prg);
                prg.getHeap().setContent(unsafeGarbageCollector(
                        getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap().getContent().values()),
                        prg.getHeap().getContent()));
                repository.printPrgState(prg);
            } catch (MyException | ADTException | StmtException | ExprException exception) {
                throw new MyException(exception.getMessage());
            }
        }
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heapTableValues){
//        return symTableValues.stream()
//                .filter(v-> v instanceof RefValue)
//                .map(v-> {
//                    RefValue v1 = (RefValue)v;
//                    return v1.getAddress();
//                })
//                .collect(Collectors.toList());
        List<Integer> symTableAddr = symTableValues.stream().filter(v -> v instanceof RefValue).map(value -> {
            RefValue value2 = (RefValue) value;
            return value2.getAddress();}).collect(Collectors.toList());

        List<Integer> heapTableAddr = heapTableValues.stream().filter(v -> v instanceof RefValue).map(value -> {
            RefValue value2 = (RefValue) value;
            return value2.getAddress();
        }).collect(Collectors.toList());

        symTableAddr.addAll(heapTableAddr);
        return symTableAddr;
    }
}

