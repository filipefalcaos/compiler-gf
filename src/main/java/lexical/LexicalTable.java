package lexical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class LexicalTable {

    static HashMap<String, Tokens> map;
    static List<Character> symbols;

    static {

        symbols = new ArrayList<>();
        map = new HashMap<>();

        // Keywords
        map.put("main", Tokens.main);
        map.put("int", Tokens.typeInt);
        map.put("float", Tokens.typeFloat);
        map.put("boolean", Tokens.typeBool);
        map.put("char", Tokens.typeChar);
        map.put("string", Tokens.typeString);
        map.put("empty", Tokens.typeEmpty);
        map.put("null", Tokens.typeNull);
        map.put("function", Tokens.funDecl);
        map.put("var", Tokens.varDecl);
        map.put("print", Tokens.cmdPrint);
        map.put("read", Tokens.cmdRead);
        map.put("if", Tokens.cmdIf);
        map.put("elif", Tokens.cmdElif);
        map.put("else", Tokens.cmdElse);
        map.put("while", Tokens.cmdWhile);
        map.put("repeat", Tokens.cmdRepeat);
        map.put("in", Tokens.cmdIn);
        map.put("return", Tokens.cmdReturn);

        // Operators
        map.put("=", Tokens.opAssign);
        map.put("+", Tokens.opAditiv);
        map.put("-", Tokens.opAditiv);
        map.put("*", Tokens.opMult);
        map.put("/", Tokens.opMult);
        map.put("%", Tokens.opMult);
        map.put("^", Tokens.opMult);
        map.put("==", Tokens.opEquals);
        map.put("!=", Tokens.opEquals);
        map.put("<", Tokens.opRel);
        map.put("<=", Tokens.opRel);
        map.put(">", Tokens.opRel);
        map.put(">=", Tokens.opRel);
        map.put("++", Tokens.opConcat);
        map.put("not", Tokens.opNot);
        map.put("and", Tokens.opAnd);
        map.put("or", Tokens.opOr);

        // Terminator
        map.put(";", Tokens.endLine);

        // Separator
        map.put(",", Tokens.commaSep);
        map.put(":", Tokens.castSep);

        // Delimiters
        map.put("(", Tokens.paramBeg);
        map.put(")", Tokens.paramEnd);
        map.put("begin", Tokens.blockBeg);
        map.put("end", Tokens.blockEnd);
        map.put("[", Tokens.arrayBeg);
        map.put("]", Tokens.arrayEnd);
        map.put("{", Tokens.arrayValBeg);
        map.put("}", Tokens.arrayValEnd);

        // Symbols
        symbols.add(';');
        symbols.add(':');
        symbols.add(',');
        symbols.add('+');
        symbols.add('-');
        symbols.add('*');
        symbols.add('/');
        symbols.add('%');
        symbols.add('^');
        symbols.add('(');
        symbols.add(')');
        symbols.add(']');
        symbols.add('[');
        symbols.add('{');
        symbols.add('}');

    }

}
