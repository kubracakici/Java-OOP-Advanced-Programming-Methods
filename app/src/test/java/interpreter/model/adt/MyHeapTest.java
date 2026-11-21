package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {
    private MyHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
    }

    @Test
    void allocateReturnsConsecutiveAddresses() {
        assertEquals(1, heap.allocate(new IntValue(10)));
        assertEquals(2, heap.allocate(new IntValue(20)));
        assertEquals(3, heap.allocate(new IntValue(30)));
    }

    @Test
    void getReturnsTheAllocatedValue() throws ADTException {
        heap.allocate(new IntValue(20));
        assertEquals(new IntValue(20), heap.get(1));
    }

    @Test
    void getOfMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.get(99));
    }

    @Test
    void updateOfMissingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.update(99, new IntValue(1)));
    }

    @Test
    void updateChangesTheValue() throws ADTException {
        heap.allocate(new IntValue(20));
        heap.update(1, new IntValue(30));
        assertEquals(new IntValue(30), heap.get(1));
    }

    @Test
    void getContentIsReadOnly() {
        heap.allocate(new IntValue(20));
        assertThrows(UnsupportedOperationException.class,
                () -> heap.getContent().put(5, new IntValue(1)));
    }

    @Test
    void setContentReplacesTheHeap() {
        heap.allocate(new IntValue(1));
        heap.allocate(new IntValue(2));
        heap.setContent(Map.of(2, new IntValue(2)));
        assertFalse(heap.isDefined(1));
        assertTrue(heap.isDefined(2));
    }
}