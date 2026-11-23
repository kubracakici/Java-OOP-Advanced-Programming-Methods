package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NewStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

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

    // ---------- A2 ----------
    @Test
    void testExample2() throws MyException {
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    // ---------- A3: log file ----------
    @Test
    void logFileContainsTheFiveSectionsInOrder() throws Exception {
        run(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:");
        int b = log.indexOf("SymTable:");
        int c = log.indexOf("Out:");
        int d = log.indexOf("FileTable:");
        int e = log.indexOf("Heap:");
        // indexOf returns -1 when missing, so check 0 <= a first
        assertTrue(0 <= a && a < b && b < c && c < d && d < e);
    }

    @Test
    void logIsAppendedAtEveryStep() throws Exception {
        run(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));
        int count = log.split("ExeStack:", -1).length - 1;
        // EX1 needs 5 oneStep calls: 1 initial entry + 2 per step (before and after the collection) = 11
        assertEquals(11, count);
    }

    @Test
    void unwritableLogFileThrowsRepositoryException() {
        // the folder "missing" does not exist, so the FileWriter fails
        String badPath = dir.resolve("missing").resolve("log.txt").toString();
        PrgState state = PrgState.of(Examples.EX1);
        var ctrl = new Controller(new Repository(state, badPath), false);
        assertThrows(RepositoryException.class, ctrl::allStep);
    }

    // ---------- A3: files and relational expressions ----------
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

    // ---------- A4: heap, garbage collector, while ----------
    @Test
    void example6PutsTwoReferencesInTheHeap() throws MyException {
        PrgState s = run(Examples.EX6);
        assertEquals(new RefValue(1, new IntType()), s.getOut().get(0));
        assertEquals(new RefValue(2, new RefType(new IntType())), s.getOut().get(1));
        assertEquals(Map.of(1, new IntValue(20), 2, new RefValue(1, new IntType())),
                s.getHeap().getContent());
    }

    @Test
    void example7ReadsTheHeapAndPrintsTwentyAndTwentyFive() throws MyException {
        PrgState s = run(Examples.EX7);
        assertEquals(new IntValue(20), s.getOut().get(0));
        assertEquals(new IntValue(25), s.getOut().get(1));
    }

    @Test
    void example8WritesTheHeap() throws MyException {
        PrgState s = run(Examples.EX8);
        assertEquals(new IntValue(20), s.getOut().get(0));
        assertEquals(new IntValue(35), s.getOut().get(1));
        assertEquals(Map.of(1, new IntValue(30)), s.getHeap().getContent());
    }

    @Test
    void example9PrintsTwentyWithTheSafeCollector() throws MyException {
        PrgState s = run(Examples.EX9);
        assertEquals(new IntValue(20), s.getOut().get(0));
    }

    @Test
    void example10CountsDownWithTheWhileLoop() throws MyException {
        PrgState s = run(Examples.EX10);
        for (int i = 0; i < 5; i++) {
            assertEquals(new IntValue(4 - i), s.getOut().get(i));   // 4, 3, 2, 1, 0
        }
    }

    @Test
    void collectorRemovesALocationThatNothingCanReachAnymore() throws MyException {
        // Ref int v; new(v,20); new(v,30): after the second new, location 1 is garbage
        IStmt p = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new NewStmt("v", new ValueExp(new IntValue(30)))));
        PrgState s = run(p);
        assertFalse(s.getHeap().isDefined(1));
        assertTrue(s.getHeap().isDefined(2));
    }
}