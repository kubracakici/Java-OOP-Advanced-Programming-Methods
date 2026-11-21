package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


class FileStmtTest
{
    //tempdir gives every test a fresh temporary folder where it creates its own input files,
    // so tests are independent of the working directory and of each other, and they leave nothing behind.
    @TempDir
    Path dir;

    private PrgState state;
    private ValueExp file;
    private StringValue fileName;




}
