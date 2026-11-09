package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import java.io.BufferedReader;
import interpreter.model.value.StringValue;


public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable, IList<IValue> out, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = program;
        this.exeStack.push(program);
    }

    public IStack<IStmt> getExeStack() { return exeStack; }
    public void setExeStack(IStack<IStmt> exeStack) { this.exeStack = exeStack; }

    public IDictionary<StringValue, BufferedReader> fileTable; //added this to fix the errors in file files

    public IDictionary<String, IValue> getSymTable() { return symTable; }
    public void setSymTable(IDictionary<String, IValue> symTable) { this.symTable = symTable; }

    public void setFileTable(IDictionary<StringValue, BufferedReader> fileTable)
    {
        this.fileTable=fileTable;
    }
    public IList<IValue> getOut() { return out; }
    public void setOut(IList<IValue> out) { this.out = out; }

    public IStmt getOriginalProgram() { return originalProgram; }

    @Override
    public String toString() {
        return "ExeStack:\n" + exeStack + "\nSymTable:\n" + symTable + "\nOut:\n" + out + "\n" + "-".repeat(30);
    }
}