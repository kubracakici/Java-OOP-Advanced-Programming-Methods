package interpreter.exceptions;

public class ExpressionException extends MyException {
    public ExpressionException(String message) {
        super(message);
    }
}

//for things like division by zero, non integer inputs and undefined variables etc.