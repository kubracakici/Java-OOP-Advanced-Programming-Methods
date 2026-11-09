package interpreter.model.adt;
import interpreter.exceptions.ADTException;
import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<>();

    @Override public void add(T v) { list.add(v); }

    @Override
    public T get(int index) throws ADTException {
        if (index < 0 || index >= list.size()) throw new ADTException("Index out of bounds");
        return list.get(index);
    }

    @Override public int size() { return list.size(); }
    @Override public String toString() { return list.toString(); }
    @Override public List<T> toList() {return List.copyOf(list);}
}

//as far as the program continues to execute, this saves the print command outputs
//every output goes to the end of the list with add