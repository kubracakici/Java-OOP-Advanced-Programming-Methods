package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;

import interpreter.model.type.IType;

public record VarDeclStmt(String name, IType type) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new StatementException("variable " + name + " is already declared");
        }
        symTable.put(name, type.defaultValue());
        return state;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}

//VarDeclStmt calls type.defaultValue() on the IType interface, so each type provides its own
// default value and a new type like StringType works without changing any existing class