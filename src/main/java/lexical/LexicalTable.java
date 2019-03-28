package lexical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class LexicalTable {

    static HashMap<String, Tokens> map;
    static List<Character> symbols;

    static {

        symbols = new ArrayList<Character>();
        map = new HashMap<String, Tokens>();

        // Operators
        map.put("+", Tokens.opAditiv);
        map.put("-", Tokens.opAditiv);
        map.put("*", Tokens.opMult);
        map.put("/", Tokens.opMult);
        map.put("%", Tokens.opMult);
        map.put("^", Tokens.opMult);

        // Terminator
        map.put(";", Tokens.endLine);

        // Delimiters
        map.put("(", Tokens.paramBeg);
        map.put(")", Tokens.paramEnd);

        // Symbols
        symbols.add(';');
        symbols.add('+');
        symbols.add('-');
        symbols.add('*');
        symbols.add('/');
        symbols.add('%');
        symbols.add('^');
        symbols.add('(');
        symbols.add(')');

    }

}
