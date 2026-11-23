package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record ReadHeapExp(IExp exp) implements IExp {   // rule E6
    @Override
    public IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        // 1. the expression must evaluate to a reference
        if (!(exp.eval(symTable, heap) instanceof RefValue ref))
            throw new ExpressionException("rH: " + exp + " is not a reference");

        // 2. its address must exist in the heap
        if (!heap.isDefined(ref.address()))
            throw new ExpressionException("rH: address " + ref.address() + " is not in the heap");

        // 3. the value is the content of the heap at that address
        return heap.get(ref.address());
    }

    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }
}