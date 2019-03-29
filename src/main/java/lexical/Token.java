package lexical;


public class Token {

    private Tokens category;
    private String value;
    private int line;
    private int column;

    Tokens getCategory() {
        return category;
    }

    Token(String value, int line, int column, Tokens category) {
        this.value = value;
        this.line = line;
        this.column = column;
        this.category = category;
    }

    @Override
    public String toString() {
        String fmt = "        [%04d, %04d] (%04d, %20s) {%s}";
        return String.format(fmt, line, column + 1, category.ordinal(), category.toString(), value);
    }

}
