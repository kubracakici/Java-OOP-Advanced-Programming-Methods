package interpreter.model.adt;
import interpreter.exceptions.ADTException;

public interface IDictionary<K, V> {
    boolean isDefined(K key);
    V lookup(K key) throws ADTException;
    void put(K key, V value);
    void update(K key, V value) throws ADTException;
}

//stocks the var names and their currnt values - a dictionary type
