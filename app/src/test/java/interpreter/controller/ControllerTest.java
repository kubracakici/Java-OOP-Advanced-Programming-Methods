package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private PrgState run(IStmt program) throws MyException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
        Controller ctrl = new Controller(new Repository(state), false);
        ctrl.allStep();
        return state;
    }

    @Test
    void testExample2() throws MyException {
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }
}