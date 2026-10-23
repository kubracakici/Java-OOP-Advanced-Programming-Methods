package view;

import controller.TreeController;
import model.Apple;
import model.Cherry;
import model.Pear;
import model.Tree;
import repository.TreeRepository;
import repository.TreeRepositoryImpl;
import exception.RepositoryException;

public class Main {
    public static void main(String[] args) {
        TreeRepository repository = new TreeRepositoryImpl(10);
        TreeController controller = new TreeController(repository);

//        Apple apple = new Apple(3);
//        if(apple instanceof Apple)
//            System.out.println("A is an apple");
        //this is also a way to write the code, java allows it so you can use ths as well

        try { //I changed all of them to the variables thing so I can throw an exception
            Apple a1 = new Apple(-2);
            controller.addTree(a1);
            Apple a2 = new Apple(4);
            controller.addTree(a2);
            Pear p1 = new Pear(5);
            controller.addTree(p1);
            Pear p2 = new Pear(1);
            controller.addTree(p2);
            Cherry c1 = new Cherry(6);
            controller.addTree(c1);
            Cherry c2 = new Cherry(3);
            controller.addTree(c2);

            Tree[] olderTrees = controller.getTreesOlderThan(3);

            for (Tree olderTree : olderTrees) {
                System.out.println("Type: " + olderTree.getType() + " | Age: " + olderTree.getAge());
            }

        } catch (RepositoryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

//NOTES:
//You can use list for a dynamic system it would be easier for you and you will not need to use the loop of i++
//you can use the objects like variables so you can use the controller part for more important stuff
//Learn the meaning of the things you use in java instead of c