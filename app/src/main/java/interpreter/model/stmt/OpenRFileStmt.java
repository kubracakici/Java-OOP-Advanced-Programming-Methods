package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//what does this file do?
//so this file takes exp to the table and checks if it is a string or not,
// if it is not it throws statement exception
//checks if prgstate and filetable is open or not, open -> error
//with bufferreader and filereader -> file reading mode
//it adds bufferreader object  with the filename key  into filetable
//so next readfile or closeRFile can enter the flow

public record OpenRFileStmt(IExp exp) implements IStmt
{
    //this part executes the open file statement
    //and evaluates the file path, verifies it is not already opened,
    //opens a buffer reader stream and registers it in the filetable
    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
        {
            throw new StatementException("openRFile: " + exp + " is not a string");
        }
        var fileTable = state.getFileTable();
        if (fileTable.isDefined(fileName))
        {
            throw new StatementException("openRFile: " + fileName + " is already open");
        }
        try
        { //the file should be open till close file
            var reader = new BufferedReader(new FileReader(fileName.val()));
            fileTable.put(fileName, reader);
        } catch (IOException e)
        {
            throw new StatementException("openRFile: cannot open " + fileName + ": " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString()

    {
        return "openRFile(" + exp + ")";
    }
}
