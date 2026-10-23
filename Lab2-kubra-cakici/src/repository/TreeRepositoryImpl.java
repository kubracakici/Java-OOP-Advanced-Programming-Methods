package repository;

import model.Tree;
import exception.RepositoryException;
public class TreeRepositoryImpl implements TreeRepository {
    // We use a fixed-size array because the homework specifically asked for it.
    private Tree[] trees;
    private int size;
    // This keeps track of how many trees are currently sitting inside our array.

    // This class does the actual heavy lifting. It implements the rules from our interface.

    public TreeRepositoryImpl(int capacity)
    {
        trees = new Tree[capacity];
        size = 0;
    }

    @Override
    public void add(Tree tree) throws RepositoryException {
        if (size == trees.length) {
            throw new RepositoryException("Repository capacity exceeded.");
        }
        trees[size++] = tree;
    }

    @Override
    // We search the array one by one to find the tree we want to delete.
    public void remove(Tree tree) throws RepositoryException {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (trees[i].equals(tree)) {
                index = i;
                break;
            }
        }
        // If we finished searching and didn't find it, throw an error
        if (index == -1) {
            throw new RepositoryException("Tree not found in repository.");
        }
        for (int i = index; i < size - 1; i++) {
            trees[i] = trees[i + 1];
        }
        trees[size - 1] = null;
        size--;
    }

    @Override
    public Tree[] getAll() {
        // We create a brand new array that perfectly fits the current number of trees.
        Tree[] currentTrees = new Tree[size];
        for (int i = 0; i < size; i++) {
            currentTrees[i] = trees[i]; // Return the perfectly sized array so we don't send back empty slots.
        }
        return currentTrees;
    }

    @Override
    public int getSize() {
        return size; // Return the perfectly sized array so we don't send back empty slots.
    }

}
