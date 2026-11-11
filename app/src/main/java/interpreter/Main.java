package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import interpreter.view.ExitCommand;
import interpreter.view.RunExampleCommand;
import interpreter.view.TextMenu;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Ex4 and Ex5 are added to this list in step 7
        List<IStmt> programs = List.of(Examples.EX1, Examples.EX2, Examples.EX3,
                Examples.EX4, Examples.EX5);
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        for (int i = 0; i < programs.size(); i++) {
            IStmt program = programs.get(i);
            // one state, one repository (own log file) and one controller per example
            var repo = new Repository(PrgState.of(program), "log" + (i + 1) + ".txt");
            var controller = new Controller(repo, true);
            menu.addCommand(new RunExampleCommand(String.valueOf(i + 1),
                    program.toString(), controller));
        }
        menu.show();
    }
}