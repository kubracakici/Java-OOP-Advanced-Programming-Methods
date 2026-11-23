package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record WriteHeapStmt(String varName, IExp exp) implements IStmt {   // rule S12
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();

        // 1. the variable must be declared and must hold a reference
        if (!symTable.isDefined(varName))
            throw new StatementException("wH: " + varName + " is not declared");
        if (!(symTable.lookup(varName) instanceof RefValue ref))
            throw new StatementException("wH: " + varName + " is not a reference");

        // 2. the address must exist in the heap
        if (!heap.isDefined(ref.address()))
            throw new StatementException("wH: address " + ref.address() + " is not in the heap");

        // 3. the new value must have the type stored in the reference
        IValue value = exp.eval(symTable, heap);
        if (!value.getType().equals(ref.locationType()))
            throw new StatementException("wH: " + exp + " has type " + value.getType()
                    + ", expected " + ref.locationType());

        // 4. overwrite the content of the heap location
        heap.update(ref.address(), value);
        return state;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp + ")";
    }
}