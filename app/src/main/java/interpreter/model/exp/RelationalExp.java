package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

//represents a rational comparison expression in the interpreter
public record RelationalExp(RelOp op, IExp left, IExp right) implements IExp {
    //evaluates the relational expression within the provided symbol table context
    //both left and right operands must evaluate to integer values


    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        //we evaluate left operand and ensure it is an integer
        IValue v1 = left.eval(symTable);
        if (!(v1 instanceof IntValue(int n1))) {
            throw new ExpressionException("First operand is not an integer");
        }
        //we evaluate right operand and ensure it is an integer
        IValue v2 = right.eval(symTable);
        if (!(v2 instanceof IntValue(int n2))) {
            throw new ExpressionException("Second operand is not an integer");
        }
        //relational evaluation based on the operator
        //and we wrap the results in the bool value
        return new BoolValue(switch (op) {
            case LT -> n1 < n2;
            case LE -> n1 <= n2;
            case EQ -> n1 == n2;
            case NE -> n1 != n2;
            case GT -> n1 > n2;
            case GE -> n1 >= n2;
        });
    }
    //it returns a stirng representation of the relational expression.
    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}