package interpreter.model.adt;
import interpreter.exceptions.ADTException;
import java.util.Map;

public interface IDictionary<K, V> {
    boolean isDefined(K key);
    V lookup(K key) throws ADTException;
    void put(K key, V value);
    void update(K key, V value) throws ADTException;
    void remove(K key) throws ADTException;
    Map<K, V> toMap();
}



//stocks the var names and their current values - a dictionary type

//toMap() returns an immutable copy, so callers like toLogString ,
//can read the content but cannot change the internal map and bypass the rules of the ADT