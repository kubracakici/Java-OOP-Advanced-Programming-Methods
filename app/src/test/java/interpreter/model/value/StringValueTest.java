package interpreter.model.value;

import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {
    @Test
    void equalStringsAreEqualAndHaveSameHashCode() {
        var a = new StringValue("test.in");
        var b = new StringValue("test.in");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void stringTypeDefaultValueIsEmptyString() {
        assertEquals(new StringValue(""), new StringType().defaultValue());
    }
}