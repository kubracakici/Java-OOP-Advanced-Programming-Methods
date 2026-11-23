package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);   // Ref int v
    }

    // ---------- new ----------
    @Test
    void newAllocatesAtAddressOneAndUpdatesTheVariable() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void newTwiceUsesANewAddressEachTime() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new NewStmt("v", new ValueExp(new IntValue(30))).execute(state);
        assertEquals(new RefValue(2, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));   // the old location stays
    }

    @Test
    void newOfAnUndeclaredVariableThrows() {
        assertThrows(StatementException.class,
                () -> new NewStmt("zzz", new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void newOfANonReferenceVariableThrows() throws MyException {
        new VarDeclStmt("i", new IntType()).execute(state);
        assertThrows(StatementException.class,
                () -> new NewStmt("i", new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void newWithAValueOfTheWrongTypeThrows() {
        var exp = new ValueExp(new BoolValue(true));
        assertThrows(StatementException.class, () -> new NewStmt("v", exp).execute(state));
    }

    // ---------- wH ----------
    @Test
    void writeHeapChangesTheHeapLocation() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);
        assertEquals(new IntValue(30), state.getHeap().get(1));
    }

    @Test
    void writeHeapOfAnUndeclaredVariableThrows() {
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("zzz", new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void writeHeapOfANonReferenceVariableThrows() throws MyException {
        new VarDeclStmt("i", new IntType()).execute(state);
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("i", new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void writeHeapToAddressNotInTheHeapThrows() {
        // v is still (0, int): the null reference
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void writeHeapWithAValueOfTheWrongTypeThrows() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        var exp = new ValueExp(new BoolValue(true));
        assertThrows(StatementException.class, () -> new WriteHeapStmt("v", exp).execute(state));
    }
}