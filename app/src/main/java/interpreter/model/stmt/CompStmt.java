package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public record CompStmt(IStmt first, IStmt second) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getExeStack().push(second);
        state.getExeStack().push(first);
        return state;
    }

    @Override
    public String toString() {
        return "(" + first + "; " + second + ")";
    }
}

//represents s1 and s2
//since the stack is last in first out we push s2 girst then s1
//this ensures that s1 ends up on top of the stack and gets executed first