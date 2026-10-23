package controller;

import model.Tree;
import repository.TreeRepository;
import exception.RepositoryException;

// The controller is the brain of the operation
// It connects our data (the repository) to our output (the main view)

public class TreeController {

    // We hold a reference to the repository interface
    // This makes it easy to swap out the database later if we want to

    private final TreeRepository repository; //you cannot change the address, but you can change the things inside

    public TreeController(TreeRepository repository) {
        this.repository = repository;
    }

    // pass the "add" command down to the repository.
    public void addTree(Tree tree) throws RepositoryException {
        if(tree.getAge() < 0)
            throw new RepositoryException("Age cannot be negative!");
        repository.add(tree);
    }



    // we pass the "remove" command down to the repository
    public void removeTree(Tree tree) throws RepositoryException {
        repository.remove(tree);
    }
    // This is where the core logic of your homework lives.
    public Tree[] getTreesOlderThan(int targetAge) {
        Tree[] allTrees = repository.getAll();
        int count = 0;

        // Step 1 Count exactly how many trees are older than the target age
        for (int i = 0; i < allTrees.length; i++) {
            if (allTrees[i].getAge() > targetAge) {
                count++;
            }
        }
        // Step 2 Create a new array that is the exact size of our count
        Tree[] filteredTrees = new Tree[count];
        int index = 0;
        // Step 3 Loop through again and actually place the matching trees into our new array
        for (int i = 0; i < allTrees.length; i++) {
            if (allTrees[i].getAge() > targetAge) {
                filteredTrees[index++] = allTrees[i];
            }
        }
    // list is more dynamic you can use it

        return filteredTrees;
    }
}