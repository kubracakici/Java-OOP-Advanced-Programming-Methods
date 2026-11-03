package interpreter.model.exp;

public enum ArithOp {
    ADD("+"), SUB("-"), MUL("*"), DIV("/");

    private final String symbol;

    ArithOp(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}

//enum -> defines a type-safe of constatns
//instead of using raw strings like "+" or ints like 1,
// enum prevents invali opr vals at compile time