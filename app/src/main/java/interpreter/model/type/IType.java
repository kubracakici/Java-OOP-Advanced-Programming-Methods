package interpreter.model.type;
import interpreter.model.value.IValue;

public interface IType {
    IValue defaultValue();
}

//interface of the all data types
//record -> records are immutable data carriers they automatically provide constructors getters etc
//which simplifies type checking and structural equality comparison