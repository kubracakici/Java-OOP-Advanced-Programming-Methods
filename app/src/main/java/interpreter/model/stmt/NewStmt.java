package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record NewStmt(String varName, IExp exp) implements IStmt {   // rule S11
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();

        // 1. the variable must be declared and must hold a reference
        if (!symTable.isDefined(varName))
            throw new StatementException("new: " + varName + " is not declared");
        if (!(symTable.lookup(varName) instanceof RefValue ref))
            throw new StatementException("new: " + varName + " is not a reference");

        // 2. the value must have the type stored in the reference
        IValue value = exp.eval(symTable, state.getHeap());
        if (!value.getType().equals(ref.locationType()))
            throw new StatementException("new: " + exp + " has type " + value.getType()
                    + ", expected " + ref.locationType());

        // 3. the heap chooses the new address, the variable now points to it
        int address = state.getHeap().allocate(value);
        symTable.update(varName, new RefValue(address, ref.locationType()));
        return state;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + exp + ")";
    }
}