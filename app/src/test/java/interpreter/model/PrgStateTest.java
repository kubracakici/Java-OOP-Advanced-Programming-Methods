package interpreter.model;

import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrgStateTest {
    @Test
    void logHasTheHeapSectionAfterTheFileTable() {
        PrgState state = PrgState.of(new NopStmt());
        String log = state.toLogString();
        int fileTable = log.indexOf("FileTable:");
        int heap = log.indexOf("Heap:");
        assertTrue(0 <= fileTable && fileTable < heap);
    }

    @Test
    void logListsTheHeapLocationsInOrder() {
        PrgState state = PrgState.of(new NopStmt());
        state.getHeap().allocate(new IntValue(20));
        state.getHeap().allocate(new IntValue(30));
        String log = state.toLogString();
        assertTrue(log.contains("1 --> 20\n2 --> 30\n"));
    }
}