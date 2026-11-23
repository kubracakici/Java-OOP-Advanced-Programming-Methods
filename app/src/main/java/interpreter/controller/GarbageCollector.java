package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class GarbageCollector {
    private GarbageCollector() {}

    // The addresses that occur in the RefValues of the SymTable (the "roots")
    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .collect(Collectors.toSet());
    }

    // UNSAFE: keeps only the heap entries referred to directly from the SymTable
    public static Map<Integer, IValue> unsafeGarbageCollector(Set<Integer> symTableAddr,
                                                              Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // All the addresses that can be reached from the roots, also through the heap itself
    public static Set<Integer> reachableAddresses(Set<Integer> roots, Map<Integer, IValue> heap) {
        Set<Integer> reachable = new HashSet<>(roots);
        boolean changed = true;
        while (changed) {
            // the addresses stored inside the RefValues of the locations we already keep
            Set<Integer> found = reachable.stream()
                    .map(heap::get)                       // null if the address is not in the heap
                    .filter(v -> v instanceof RefValue)
                    .map(v -> ((RefValue) v).address())
                    .collect(Collectors.toSet());
            changed = reachable.addAll(found);            // true only if something new was added
        }
        return reachable;
    }

    // SAFE: keeps every location reachable from the SymTable, directly or through the heap
    public static Map<Integer, IValue> safeGarbageCollector(Set<Integer> symTableAddr,
                                                            Map<Integer, IValue> heap) {
        Set<Integer> reachable = reachableAddresses(symTableAddr, heap);
        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}