package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyDictionaryTest
{
    @Test
    void testPutAndLookUp() throws ADTException
    {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("k", 5);
        assertTrue(dict.isDefined("k"));
        assertEquals(5, dict.lookup("k"));
    }

    @Test
    void testUpdate() throws ADTException {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("a", 10);
        dict.update("a", 20);
        assertEquals(20, dict.lookup("a"));
    }

    @Test
    void testLookupMissingKeyThrows() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        assertThrows(ADTException.class, () -> dict.lookup("missing"));
    }
}
