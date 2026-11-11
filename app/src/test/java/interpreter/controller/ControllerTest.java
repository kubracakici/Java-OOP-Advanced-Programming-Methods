package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path dir; // fresh temporary folder for every test

    // Runs a program with display off and the log in the temp folder
    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep();
        return state;
    }

    @Test
    void testExample2() throws MyException {
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:");
        int b = log.indexOf("SymTable:");
        int c = log.indexOf("Out:");
        int d = log.indexOf("FileTable:");
        // indexOf returns -1 when missing, so check 0 <= a first
        assertTrue(0 <= a && a < b && b < c && c < d);
    }

    @Test
    void logIsAppendedAtEveryStep() throws Exception {
        run(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));
        int count = log.split("ExeStack:", -1).length - 1;
        // EX1 needs 5 oneStep calls, so 5 + 1 (initial state) = 6 log entries
        assertEquals(6, count);
    }

    @Test
    void unwritableLogFileThrowsRepositoryException() {
        // the folder "missing" does not exist, so the FileWriter fails
        String badPath = dir.resolve("missing").resolve("log.txt").toString();
        PrgState state = PrgState.of(Examples.EX1);
        var ctrl = new Controller(new Repository(state, badPath), false);
        assertThrows(RepositoryException.class, ctrl::allStep);
    }

    @Test
    void fileExampleReadsBothNumbersAndClosesTheFile() throws Exception {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(15), s.getOut().get(0));
        assertEquals(new IntValue(50), s.getOut().get(1));
        // closeRFile removed the file from the FileTable
        assertTrue(s.getFileTable().toMap().isEmpty());
    }

    @Test
    void fileExampleWithEmptyFilePrintsZeros() throws Exception {
        Path in = dir.resolve("empty.in");
        Files.writeString(in, "");
        PrgState s = run(Examples.fileExample(in.toString()));
        // readLine() returns null at the end of the file, readFile turns that into 0
        assertEquals(new IntValue(0), s.getOut().get(0));
        assertEquals(new IntValue(0), s.getOut().get(1));
    }

    @Test
    void example5PrintsFive() throws MyException {
        PrgState s = run(Examples.EX5);
        assertEquals(new IntValue(5), s.getOut().get(0));
    }
}