package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    @Test
    void testAddAndGet() throws ADTException {
        IList<String> list = new MyList<>();
        list.add("hello");
        assertEquals(1, list.size());
        assertEquals("hello", list.get(0));
    }

    @Test
    void testOutOfBoundsThrows() {
        IList<String> list = new MyList<>();
        assertThrows(ADTException.class, () -> list.get(0));
    }
}