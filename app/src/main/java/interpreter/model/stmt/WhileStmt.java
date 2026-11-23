package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record WhileStmt(IExp cond, IStmt body) implements IStmt {   // rule S13
    @Override
    public PrgState execute(PrgState state) throws MyException {
        // 1. the condition must be a boolean
        IValue value = cond.eval(state.getSymTable(), state.getHeap());
        if (!(value instanceof BoolValue(boolean b)))
            throw new StatementException("while: condition " + cond + " is not a boolean");

        // 2. true: push the loop back first, then the body (the body is on top, so it runs first)
        //    false: push nothing, the loop ends
        if (b) {
            state.getExeStack().push(this);
            state.getExeStack().push(body);
        }
        return state;
    }

    @Override
    public String toString() {
        return "(while (" + cond + ") " + body + ")";
    }
}