package interpreter.exceptions;

// Thrown when the log file cannot be written
public class RepositoryException extends MyException {
    public RepositoryException(String message) {
        super(message);
    }
}