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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepo repository;
    private ExecutorService executor;

    public IRepo getRepository() {
        return repository;
    }

    public Controller(IRepo repo) {
        this.repository = repo;
    }


    Map<Integer, Value> unsafeGarbageCollector(List<Integer> addresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(elem -> addresses.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException, MyException {


        prgList.forEach(prg-> {
            try {
                repository.printPrgState(prg);
//                System.out.println(prgList);
            } catch (MyException | IOException e) {
                e.printStackTrace();
            }
        });

        List <Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p)->(Callable<PrgState>)(()-> p.oneStep()))
                .collect(Collectors.toList());

        List<PrgState> newProgramStates = executor.invokeAll(callList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(e -> e != null)
                .collect(Collectors.toList());

        prgList.addAll(newProgramStates);
        prgList.forEach(prg -> {
            try {
                repository.printPrgState(prg);
            } catch (MyException | IOException e) {
                e.printStackTrace();
            }
        });


        repository.setPrgList(prgList);
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heap) {
        return Stream.concat(
                        heap.stream()
                                .filter(v -> v instanceof RefValue)
                                .map(v -> {
                                    RefValue v1 = (RefValue) v;
                                    return v1.getAddress();
                                })
                        ,symTableValues.stream()
                                .filter(v -> v instanceof RefValue)
                                .map(v -> {
                                    RefValue v1 = (RefValue) v;
                                    return v1.getAddress();
                                }))
                .collect(Collectors.toList());
    }

    List<PrgState> removeCompletedPrograms(List<PrgState> prgList){
        return prgList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }

    public PrgState oneStep(PrgState state) throws MyException, ADTException, StmtException, ExprException {
        IStack<IStmt> stack = state.getStack();
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void allStep() throws MyException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState>  prgList=removeCompletedPrograms(repository.getPrgList());
        while(prgList.size() > 0){
            callGarbageCollector(prgList);
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrograms(repository.getPrgList());
        }

        executor.shutdownNow();
//        prgList=removeCompletedPrograms(repository.getPrgList());
        repository.setPrgList(prgList);
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> addressesFromSymbolTable, Map<Integer,Value> heap)
    {
        return heap.entrySet()
                .stream()
                .filter(e->addressesFromSymbolTable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void callGarbageCollector(List<PrgState> programStates){
        programStates.forEach(
                p-> {p.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(p.getSymTable().getContent().values(),p.getHeap().getContent().values()),p.getHeap().getContent()));}
        );
    }
}

