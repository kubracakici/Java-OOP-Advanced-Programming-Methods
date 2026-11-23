package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadHeapExpTest {
    private MyDictionary<String, IValue> symTable;
    private MyHeap heap;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
        heap = new MyHeap();
        int a1 = heap.allocate(new IntValue(20));                      // 1 -> 20
        int a2 = heap.allocate(new RefValue(a1, new IntType()));       // 2 -> (1, int)
        symTable.put("v", new RefValue(a1, new IntType()));
        symTable.put("a", new RefValue(a2, new RefType(new IntType())));
    }

    @Test
    void readsTheValueFromTheHeap() throws MyException {
        var e = new ReadHeapExp(new VarExp("v"));
        assertEquals(new IntValue(20), e.eval(symTable, heap));
    }

    @Test
    void readsThroughTwoLevels() throws MyException {
        var e = new ReadHeapExp(new ReadHeapExp(new VarExp("a")));
        assertEquals(new IntValue(20), e.eval(symTable, heap));
    }

    @Test
    void nonReferenceExpressionThrows() {
        var e = new ReadHeapExp(new ValueExp(new IntValue(1)));
        assertThrows(ExpressionException.class, () -> e.eval(symTable, heap));
    }

    @Test
    void addressNotInTheHeapThrows() {
        symTable.put("n", new RefValue(0, new IntType()));   // null reference
        var e = new ReadHeapExp(new VarExp("n"));
        assertThrows(ExpressionException.class, () -> e.eval(symTable, heap));
    }
}