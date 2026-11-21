package interpreter.model.type;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;


class RefTypeTest
{
    @Test
    void defaultValueIsNullReference()
    {
        var type = new RefType(new IntType());
        assertEquals(new RefValue(0, new IntType()), type.defaultValue());

    }

    @Test
    void nestedRefTypesAreEqual()
    {
        var a = new RefType(new RefType(new IntType()));
        var b = new RefType(new RefType(new IntType()));
        assertEquals(a,b);

    }

    @Test
    void differentNestingIsNotEqual()
    {
        var twoLevels = new RefType(new RefType(new IntType()));
        var oneLevel = new RefType(new IntType());
        assertNotEquals(twoLevels, oneLevel);

    }
    @Test
    void refValueHasRefType() {
        // the value (1, int) has the type Ref(int)
        var value = new RefValue(1, new IntType());
        assertEquals(new RefType(new IntType()), value.getType());
    }

    @Test
    void refVariableGetsNullReferenceByDefault() throws MyException {
        // VarDeclStmt is not changed: it only calls type.defaultValue()
        PrgState state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new RefType(new IntType())).execute(state);
        assertEquals(new RefValue(0, new IntType()), state.getSymTable().lookup("x"));
    }


}