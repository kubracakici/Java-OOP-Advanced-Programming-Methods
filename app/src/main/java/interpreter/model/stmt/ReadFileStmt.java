package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt
{
    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        var symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new StatementException("readFile: variable " + varName + " is not decleared");
        if(!symTable.lookup(varName).getType().equals(new IntType()))
            throw new StatementException("readFile: variable "+ varName + " is not an int." );
        if(!(exp.eval(symTable) instanceof StringValue fileName))
            throw new StatementException("readFile: " + exp + " is not a string.");
        var fileTable = state.getFileTable();
        if(!fileTable.isDefined(fileName))
            throw new StatementException("readFile: " + fileName+ " is not open.");

        try
        {
            String line = fileTable.lookup(fileName).readLine();
            int n = (line == null) ? 0: Integer.parseInt(line.strip());
            //line null means the end of the file -> 0
            //empty line -> not null so parseInt error number formal exception catching
            symTable.update(varName, new IntValue(n));

        }
        catch (IOException e)
        {
            throw new StatementException("readFile: I/O error "+ e.getMessage());
        }
        catch (NumberFormatException e)
        {
            throw new StatementException("readFile: the line is not integer");
        }

        return state;
    }

    @Override
    public String toString()
    {
        return "readFile (" + exp + ", " + varName + ")";
    }
}