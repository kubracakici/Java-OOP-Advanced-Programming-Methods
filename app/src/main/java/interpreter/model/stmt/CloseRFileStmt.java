package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt
{
    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        //the file name should be a string
        if (!(exp.eval(state.getSymTable())instanceof StringValue fileName))
            throw new StatementException("closeRFile: " + exp + " is not a string");
        var fileTable = state.getFileTable();
        //we check if the file is open to close it
        if(!fileTable.isDefined(fileName))
            throw new StatementException("closeRFile: "+ fileName + " is not open");

        try
        {
            //we close the bufferreader because the system make s a file handler for files
            //and we dont want it to stay open and tire the computer
            //free the source first, them close the file.
            fileTable.lookup(fileName).close();
            fileTable.remove(fileName);
        }
        catch(IOException e)
        {
            //Java exception stops here, the rest of the app only sees MyException
            throw new StatementException("closeRFile: I/O error: "+ e.getMessage());

        }
        return state;

    }

    @Override
    public String toString()
    {
        return "closeRFile("+ exp + ")";
    }
}