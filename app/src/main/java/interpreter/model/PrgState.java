package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
//represents the complete execution state of a running program
public class PrgState {
    private IStack<IStmt> exeStack; //stack of stats left to execute
    private IDictionary<String, IValue> symTable; //maps variable names to their current values
    private IList<IValue> out; //stores output vals printed by program
    private IDictionary<StringValue, BufferedReader> fileTable; //maps open file names to their buffreader streams
    private final IStmt originalProgram; //backup of the initial program statement

    //initializes the program state with the given structs and pushes the initial prog onto the stack
    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable, IList<IValue> out,
                    IDictionary<StringValue, BufferedReader> fileTable, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;
        this.exeStack.push(program);
    }

    // Creates a state with empty structures in one place
    //so when the constructor changes, only the factory
    //changes and not every new prgstate call in the code
    //and the tests
    public static PrgState of(IStmt program)
    {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                new MyDictionary<>(), program);
    }
    //the reason we used of is because we added new structures and we update
    //the factory method, we dont need to change all of the test files one by one
    //stattic factory method its name


    //getters and setters for program
    public IStack<IStmt> getExeStack() { return exeStack; }
    public void setExeStack(IStack<IStmt> exeStack) { this.exeStack = exeStack; }

    public IDictionary<String, IValue> getSymTable() { return symTable; }
    public void setSymTable(IDictionary<String, IValue> symTable) { this.symTable = symTable; }

    public IList<IValue> getOut() { return out; }
    public void setOut(IList<IValue> out) { this.out = out; }

    public IDictionary<StringValue, BufferedReader> getFileTable() { return fileTable; }
    public void setFileTable(IDictionary<StringValue, BufferedReader> fileTable) { this.fileTable = fileTable; }

    public IStmt getOriginalProgram() { return originalProgram; }

    //it returns a formatted string representation of the current state for console output
    @Override
    public String toString() {
        return "ExeStack:\n" + exeStack + "\nSymTable:\n" + symTable + "\nOut:\n" + out
                + "\nFileTable:\n" + fileTable.toMap().keySet() + "\n" + "-".repeat(30);
    }

    // Text written to the log file after every step
    public String toLogString() {
        var sb = new StringBuilder();
        sb.append("ExeStack:\n");
        exeStack.toList().forEach(s -> sb.append(s).append('\n'));
        sb.append("SymTable:\n");
        symTable.toMap().forEach((k, v) -> sb.append(k).append(" --> ").append(v).append('\n'));
        sb.append("Out:\n");
        out.toList().forEach(v -> sb.append(v).append('\n'));
        sb.append("FileTable:\n");
        fileTable.toMap().keySet().forEach(f -> sb.append(f.val()).append('\n'));
        return sb.toString();
    }

    //both tostring and tologstring utilize filetable tomap keyset
    //this is because there is no value in printing the memory
    //address of the bufferreader object, which resides the value
    //portion, as we only wish to see the names of currently open files
}