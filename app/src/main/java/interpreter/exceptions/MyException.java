package interpreter.exceptions;

public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}

//this is main error class
//the other exception classes - inheritance of this class