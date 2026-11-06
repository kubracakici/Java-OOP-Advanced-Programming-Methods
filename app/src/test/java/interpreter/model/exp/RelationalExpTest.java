package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//unit tests for validating the functionality and error handling
class RelationalExpTest
{
    private static IExp num(int n)
    {//helper factory method to wrap a primitive integer into an integer val exp
        return new ValueExp(new IntValue(n));
    }

    private static boolean eval(RelOp op, int a, int b) throws MyException
    {
        var result = new RelationalExp(op, num(a), num(b)).eval(new MyDictionary<>());
        return ((BoolValue) result).val();
    }

    @Test
    void lessThan() throws MyException
    {
        assertTrue(eval(RelOp.LT, 3, 5));
        assertFalse(eval(RelOp.LT, 5, 3));
    }

    @Test
    void lessOrEqual() throws MyException
    {
        assertTrue(eval(RelOp.LE, 5, 5));
        assertFalse(eval(RelOp.LE, 6, 5));
    }

    @Test
    void equal() throws MyException
    {
        assertTrue(eval(RelOp.EQ, 4, 4));
        assertFalse(eval(RelOp.EQ, 4, 5));
    }

    @Test
    void notEqual() throws MyException
    {
        assertTrue(eval(RelOp.NE, 4, 5));
        assertFalse(eval(RelOp.NE, 4, 4));
    }

    @Test
    void greaterThan() throws MyException
    {
        assertTrue(eval(RelOp.GT, 5, 3));
        assertFalse(eval(RelOp.GT, 3, 5));
    }

    @Test
    void greaterOrEqual() throws MyException
    {
        assertTrue(eval(RelOp.GE, 5, 5));
        assertFalse(eval(RelOp.GE, 4, 5));
    }

    @Test
    void nonIntegerFirstOperandThrows() {
        var e = new RelationalExp(RelOp.LT, new ValueExp(new BoolValue(true)), num(1));
        assertThrows(ExpressionException.class, () -> e.eval(new MyDictionary<>()));
    }

    @Test
    void nonIntegerSecondOperandThrows() {
        var e = new RelationalExp(RelOp.LT, num(1), new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> e.eval(new MyDictionary<>()));
    }

    @Test
    void toStringIsInfix() {
        assertEquals("(1 < 2)", new RelationalExp(RelOp.LT, num(1), num(2)).toString());
    }
}