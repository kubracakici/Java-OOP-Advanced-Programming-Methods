package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.MyDictionary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {
    @Test
    void testUndefinedVariableThrows() {
        IExp e = new VarExp("x");
        assertThrows(ExpressionException.class, () -> e.eval(new MyDictionary<>()));
    }
}