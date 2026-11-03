package interpreter.model.adt;
import interpreter.exceptions.ADTException;

public interface IStack<T> {
    void push(T v);
    T pop() throws ADTException;
    boolean isEmpty();
}

//this serves as the execution stack with my stack
//holds the commands that are waiting to be executed LIFO stuff