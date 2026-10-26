package repository;
import model.Tree;
import exception.RepositoryException;

// This interface is just a contract.
// It defines the rules for what our repository MUST be able to do.

public interface TreeRepository {

    void add(Tree tree) throws RepositoryException;
    void remove(Tree tree) throws RepositoryException;
    Tree[] getAll();
    int getSize();
}
