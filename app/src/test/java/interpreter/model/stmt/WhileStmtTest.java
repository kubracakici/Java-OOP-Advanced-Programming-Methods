package interpreter.model.stmt;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.IExp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {
    @TempDir
    Path dir;

    private static IExp num(int n) { return new ValueExp(new IntValue(n)); }

    // int v; v = start; (while (v > 0) (print(v); v = v - 1)); print(v)
    private static IStmt countdown(int start) {
        IStmt body = new CompStmt(new PrintStmt(new VarExp("v")),
                new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), num(1))));
        IStmt loop = new WhileStmt(new RelationalExp(RelOp.GT, new VarExp("v"), num(0)), body);
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", num(start)),
                        new CompStmt(loop, new PrintStmt(new VarExp("v")))));
    }

    private List<IntValue> outOf(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep();
        return state.getOut().toList().stream().map(v -> (IntValue) v).toList();
    }

    @Test
    void loopThatRunsZeroTimes() throws MyException {
        assertEquals(List.of(new IntValue(0)), outOf(countdown(0)));   // only the last print
    }

    @Test
    void loopThatRunsOneTime() throws MyException {
        assertEquals(List.of(new IntValue(1), new IntValue(0)), outOf(countdown(1)));
    }

    @Test
    void loopThatRunsSeveralTimes() throws MyException {
        assertEquals(List.of(new IntValue(4), new IntValue(3), new IntValue(2),
                new IntValue(1), new IntValue(0)), outOf(countdown(4)));
    }

    @Test
    void trueConditionPushesTheLoopUnderItsBody() throws MyException {
        var body = new PrintStmt(new ValueExp(new IntValue(1)));
        var loop = new WhileStmt(new ValueExp(new BoolValue(true)), body);
        PrgState state = PrgState.of(new NopStmt());      // the stack starts with the Nop
        loop.execute(state);
        assertEquals(body, state.getExeStack().pop());    // the body runs first
        assertEquals(loop, state.getExeStack().pop());    // then the loop is checked again
        assertEquals(new NopStmt(), state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void falseConditionPushesNothing() throws MyException {
        var loop = new WhileStmt(new ValueExp(new BoolValue(false)), new NopStmt());
        PrgState state = PrgState.of(new NopStmt());
        loop.execute(state);
        state.getExeStack().pop();                        // only the original Nop is there
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void nonBooleanConditionThrows() {
        var loop = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());
        PrgState state = PrgState.of(new NopStmt());
        assertThrows(StatementException.class, () -> loop.execute(state));
    }
}