package interpreter.exceptions;

public class ADTException extends MyException {
    public ADTException(String message) {
        super(message);
    }
}

//for invalid operations on data structrues like popping from an empty stack or missing dict. key