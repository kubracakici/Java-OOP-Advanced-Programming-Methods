package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record AssignStmt(String id, IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (!symTable.isDefined(id)) {
            throw new StatementException("variable " + id + " was not declared");
        }
        IValue val = exp.eval(symTable);
        if (!val.getType().equals(symTable.lookup(id).getType())) {
            throw new StatementException("declared type of variable " + id + " and type of the assigned expression do not match");
        }
        symTable.update(id, val);
        return state;
    }

    @Override
    public String toString() {
        return id + "=" + exp;
    }
}

//this checks if the var is decleared in symtable if not -> statement exception
//it verifies that the type of the evaluated value matches the decleared type of the variable
//if it is valid it updates the value in symtable