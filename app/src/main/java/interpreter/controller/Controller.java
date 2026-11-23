package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {
    private final IRepository repo;
    private final boolean displayFlag;

    public Controller(IRepository repo, boolean displayFlag) {
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    // Pops the top statement and executes it
    public PrgState oneStep(PrgState state) throws MyException {
        var stack = state.getExeStack();
        if (stack.isEmpty()) {
            throw new StatementException("program state stack is empty");
        }
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    // Runs the whole program; after every step the garbage collector cleans the heap
    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();
        repo.logPrgStateExec();                 // initial state
        if (displayFlag) {
            System.out.println(prg);
        }
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            repo.logPrgStateExec();             // state before the collection
            prg.getHeap().setContent(GarbageCollector.safeGarbageCollector(
                    GarbageCollector.getAddrFromSymTable(prg.getSymTable().toMap().values()),
                    prg.getHeap().getContent()));
            repo.logPrgStateExec();             // state after the collection
            if (displayFlag) {
                System.out.println(prg);
            }
        }
    }
}