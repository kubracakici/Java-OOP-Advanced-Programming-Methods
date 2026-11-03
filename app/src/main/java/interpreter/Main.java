package interpreter;

//Kubra Cakici Erasmus +
//lab assignment 2
//toy interpreter

import interpreter.view.TextMenu;

public class Main {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.start();
    }
}

//model -> contains domain logic
// repo -> manages state storage
//controller -> executes the logic
//view -> interface for user like text menu etc.