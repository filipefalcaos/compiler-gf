package lexical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class LexicalAnalyzer {

    private BufferedReader inputBuffer;
    private int currLineIdx, currColumnIdx;
    private String currLine;
    private Token previousToken, currToken;
    private StringBuilder tokenValue = new StringBuilder();

    public LexicalAnalyzer(String inputPath) throws FileNotFoundException {
        this.currLineIdx = 0;
        this.currColumnIdx = 0;
        this.inputBuffer = new BufferedReader(new FileReader(inputPath));
    }

    public boolean hasNext() throws IOException {

        // First line
        if (currLineIdx == 0 && currColumnIdx == 0) {
            nextLine();
            printLine();

            if (currLine == null) {
                return false;
            }
        }

        // Next lines
        if (currLine.substring(currColumnIdx).matches("\\s*")) {
            while (nextLine()) {
                currLineIdx++;
                currColumnIdx = 0;
                printLine();

                if (!currLine.matches("\\s*")) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }

    }

    public Token nextToken() {

        char currChar = currLine.charAt(currColumnIdx);
        int tokenColumn = currColumnIdx;
        int tokenLine = currLineIdx;
        tokenValue.setLength(0);

        // Ignoring spaces and tabs
        while (currChar == ' ' || currChar == '\t') {
            currChar = nextChar();
            tokenColumn++;
        }

        // Test if is digit or id
        if (isDigitOrId(currChar)) {
            return makeToken(tokenValue.toString(), tokenLine, tokenColumn);
        }

        // Test if is a string constant
        if (isConstString(currChar)) {
            return makeToken(tokenValue.toString(), tokenLine, tokenColumn);
        }

        // Check if is a symbol
        if (isSymbol(currChar)) {
            return makeToken(tokenValue.toString(), tokenLine, tokenColumn);
        }

        return makeToken(tokenValue.toString(), tokenLine, tokenColumn);

    }

    private boolean isDigitOrId(char currChar) {

        // Check if is int or float
        if (Character.toString(currChar).matches("\\d")) {
            tokenValue.append(currChar);
            currChar = nextChar();

            while (Character.toString(currChar).matches("\\d")) {
                tokenValue.append(currChar);
                currChar = nextChar();
            }

            if (currChar == '.') {
                tokenValue.append(currChar);
                currChar = nextChar();

                while (Character.toString(currChar).matches("\\d")) {
                    tokenValue.append(currChar);
                    currChar = nextChar();
                }
            }
        }
        // Check if ids
        else {
            while (!LexicalTable.symbols.contains(currChar)) {
                if (currChar == '\"') { break; }
                tokenValue.append(currChar);
                currChar = nextChar();
                if (currChar == ' ' || currChar == '\n') { break; }
            }

        }

        return !(tokenValue.toString().equals(""));

    }

    private boolean isConstString(char currChar) {

        // Begin of a string
        if (currChar == '"') {
            tokenValue.append(currChar);
            currChar = nextChar();

            // Check if empty string
            if (currChar == '"') {
                tokenValue.append(currChar);
                currColumnIdx++;
            }
            // Read a full string
            else {
                while (currChar != '\n') {

                    // Ignore a \\ escape char
                    if (currChar == '\\') {
                        currChar = nextChar();
                    }

                    tokenValue.append(currChar);
                    currChar = nextChar();

                    // Reached the end of string
                    if (currChar == '"') {
                        tokenValue.append(currChar);
                        currColumnIdx++;
                        break;
                    }
                }
            }
        }

        return !(tokenValue.toString().isEmpty());

    }

    private boolean isSymbol(char currChar) {

        // Check symbol cases
        if (currChar == '<' || currChar == '>' || currChar == '=' || currChar == '!') {
            tokenValue.append(currChar);
            currChar = nextChar();

            if (currChar == '=') {
                tokenValue.append(currChar);
                currColumnIdx++;
            }

            return true;
        } else if (currChar == '+') {
            tokenValue.append(currChar);
            currChar = nextChar();

            if (currChar == '+') {
                tokenValue.append(currChar);
                currColumnIdx++;
            }

            return true;
        } else if (currChar == '-' || currChar == '*' || currChar == '/' || currChar == '%' || currChar == '^') {
            tokenValue.append(currChar);
            nextChar();
            return true;
        } else if (currChar == ';' || currChar == ',' || currChar == '(' || currChar == ')' || currChar == '['
                || currChar == ']' || currChar == '{' || currChar == '}') {
            tokenValue.append(currChar);
            nextChar();
            return true;
        }

        return false;

    }

    private Character nextChar() {
        currColumnIdx++;

        if (currColumnIdx < currLine.length()) {
            return currLine.charAt(currColumnIdx);
        }

        return '\n';
    }

    private boolean nextLine() throws IOException {

        String line;
        line = inputBuffer.readLine();

        // Remove comments
        if (line != null) {
            line = line.replaceAll("#.*", "");
            currLine = line;
            return true;
        }

        return false;

    }

    private void printLine() {
        String fmt = String.format("[%04d]  %s", currLineIdx + 1, currLine.trim());
        System.out.println(fmt);
    }

    private Token makeToken(String value, int line, int column) {

        previousToken = currToken;
        Token token = new Token(value, line, column, findCategory(value));
        currToken = token;
        return token;

    }

    private Tokens findCategory(String value) {

        if (value.equals("-") && isUnaryNeg()) {
            return Tokens.opUnaryNeg;
        } else if (LexicalTable.map.containsKey(value)) {
            return LexicalTable.map.get(value);
        } else {
            return isConsOrId(value);
        }

    }

    private Tokens isConsOrId(String value) {

        if (value.matches("\\d+")) {
            return Tokens.constNumInt;
        } else if (value.matches("(\\d)+\\.(\\d)+")) {
            return Tokens.constNumFloat;
        } else if (value.equals("true") || value.equals("false")) {
            return Tokens.constBool;
        } else if (value.startsWith("\'") && value.length() == 3 && value.endsWith("\'")) {
            return Tokens.constChar;
        } else if (value.startsWith("\'") && value.length() == 4 && value.endsWith("\'")) {
            if (value.indexOf("\\") == 1 && (value.indexOf("n") == 2 || value.indexOf("t") == 2)) {
                return Tokens.constChar;
            }
        } else if (value.startsWith("\"") && value.length() > 1 && value.endsWith("\"")) {
            return Tokens.constString;
        } else if(value.matches("[a-z_A-Z](\\w)*")) {
            return Tokens.id;
        }

        return Tokens.unknown;

    }

    private boolean isUnaryNeg() {

        if (previousToken != null) {
            Tokens previousCtg = previousToken.getCategory();
            return previousCtg != Tokens.constNumInt && previousCtg != Tokens.constNumFloat &&
                    previousCtg != Tokens.id && previousCtg != Tokens.paramEnd;
        }

        return true;

    }

}
