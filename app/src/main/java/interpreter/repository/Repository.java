package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> states = new ArrayList<>();
    private final String logFilePath;

    public Repository(PrgState state, String logFilePath) {
        this.states.add(state);
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCrtPrg() {
        return states.get(0);
    }

    @Override
    public void logPrgStateExec() throws MyException {
        // try-with-resources: the writer is closed automatically, so nothing is lost
        // "true" = append mode, otherwise every call would overwrite the file
        try (PrintWriter logFile = new PrintWriter(
                new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(getCrtPrg().toLogString());
        } catch (IOException e) {
            // Java exception stops here, the rest of the app only sees MyException
            throw new RepositoryException("cannot write the log file " + logFilePath + ": " + e.getMessage());
        }
    }
}

//the log is written after every step and each time the file is opened
//written, flushed and closed. so by the time next statement runs everything
//before it is already saved on disk

//We open, write and close the log file on every step, in append mode, so every state is already saved on disk.
// If a later statement throws an exception, nothing that was logged before it is lost