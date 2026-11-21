package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = cond.eval(state.getSymTable());
        if (!(val instanceof BoolValue(boolean b))) {
            throw new StatementException("conditional expression is not a boolean");
        }
        if (b) {
            state.getExeStack().push(thenS);
        } else {
            state.getExeStack().push(elseS);
        }
        return state;
    }

    @Override
    public String toString() {
        return "(if " + cond + " then " + thenS + " else " + elseS + ")";
    }
}
//exestack holds everything that needs to ne executed and controller oneStep
//always pops a statement and executes it,
//ifstmt follows this model so it only evaluates the condition and decides
//which branch is the next then pushes the branch ontp the stack
//the branch runs later in its own oneStep

//one step -> one statement if this file is called thenSçexecute(state)
//directly, a single one step would run both the if and the chosen branch
//the state after the if would never appear in log/screen
