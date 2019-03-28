package lexical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LexicalTable {

    public static HashMap<String, Tokens> map;
    public static HashMap<String, Tokens> delimiters;
    public static List<Character> symbols;

    static {

        symbols = new ArrayList<Character>();
        map = new HashMap<String, Tokens>();

        map.put("+", Tokens.opAditiv);
        map.put("-", Tokens.opAditiv);

    }

}
