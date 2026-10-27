package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {
    @Test
    void testArithExpPrecedence() throws MyException {
        IExp e = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)),
                new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))));
        assertEquals(new IntValue(17), e.eval(new MyDictionary<>()));
    }

    @Test
    void testDivisionByZero() {
        IExp e = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(0)));
        assertThrows(ExpressionException.class, () -> e.eval(new MyDictionary<>()));
    }
}