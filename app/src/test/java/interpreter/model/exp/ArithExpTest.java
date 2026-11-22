package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {
    @Test
    void testAllOperators() throws MyException {
        var dict = new MyDictionary<String, interpreter.model.value.IValue>();
        IExp add = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)));
        IExp sub = new ArithExp(ArithOp.SUB, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)));
        IExp mul = new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)));
        IExp div = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)));

        assertEquals(new IntValue(15), add.eval(dict, new MyHeap()));
        assertEquals(new IntValue(5), sub.eval(dict, new MyHeap()));
        assertEquals(new IntValue(50), mul.eval(dict, new MyHeap()));
        assertEquals(new IntValue(2), div.eval(dict, new MyHeap()));
    }

    @Test
    void testDivisionByZero() {
        IExp e = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(0)));
        assertThrows(ExpressionException.class, () -> e.eval(new MyDictionary<>(), new MyHeap()));
    }

    @Test
    void testNonIntegerOperandThrows() {
        IExp e = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(5)));
        assertThrows(ExpressionException.class, () -> e.eval(new MyDictionary<>(), new MyHeap()));
    }
}