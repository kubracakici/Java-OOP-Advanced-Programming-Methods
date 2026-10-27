package interpreter.view;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.Scanner;

public class TextMenu {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- TOY INTERPRETER ---");
            System.out.println("1. Program 1: int v; v=2; print(v)");
            System.out.println("2. Program 2: int a; int b; a=2+3*5; b=a+1; print(b)");
            System.out.println("3. Program 3: bool a; int v; a=true; (if a then v=2 else v=3); print(v)");
            System.out.println("0. Out");
            System.out.print("Please Enter Your Choice: ");

            String option = scanner.nextLine();
            if (option.equals("0")) break;

            IStmt selectedProg = switch (option) {
                case "1" -> Examples.EX1;
                case "2" -> Examples.EX2;
                case "3" -> Examples.EX3;
                default -> null;
            };

            if (selectedProg == null) {
                System.out.println("Invalid choice!");
                continue;
            }

            try {
                PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedProg);
                Controller ctrl = new Controller(new Repository(state), true);
                ctrl.allStep();
                System.out.println("\nOut: " + state.getOut());
            } catch (Exception e) {
                System.out.println("ERROR!!!: " + e.getMessage());
            }
        }
    }
}