package interpreter.model.type;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record IntType() implements IType {
    @Override public IValue defaultValue() { return new IntValue(0); }
    @Override public String toString() { return "int"; }
}
//it symbolises the int var type, default IntValue is 0