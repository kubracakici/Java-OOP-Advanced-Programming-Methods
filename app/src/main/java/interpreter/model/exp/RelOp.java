package interpreter.model.exp;

public enum RelOp
{
    LT("<"), //less than
    LE("<="), //less than or equal
    EQ("=="), //equal
    NE("!="), //not equal
    GT(">"), //greater than
    GE(">=");//greater than or eqwual
    //here relational operator constants
    private final String symbol;

    RelOp(String symbol)
    {
        this.symbol = symbol;
    }

    @Override
    public String toString()
    {
        return symbol;
    }
}