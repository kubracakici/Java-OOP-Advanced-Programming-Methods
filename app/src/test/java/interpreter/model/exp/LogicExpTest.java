package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {
    @Test
    void testAndAndOr() throws MyException {
        var dict = new MyDictionary<String, interpreter.model.value.IValue>();
        IExp andExp = new LogicExp(LogicOp.AND, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        IExp orExp = new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));

        assertEquals(new BoolValue(false), andExp.eval(dict));
        assertEquals(new BoolValue(true), orExp.eval(dict));
    }

    @Test
    void testNonBooleanOperandThrows() {
        IExp e = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(1)), new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> e.eval(new MyDictionary<>()));
    }
}