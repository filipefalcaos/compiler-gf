package lexical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class LexicalAnalyzer {

    private BufferedReader inputBuffer;
    private int currLineIdx, currColumnIdx;
    private String currLine;
    private Token currToken;
    private StringBuilder tokenValue = new StringBuilder();

    public LexicalAnalyzer(String inputPath) throws FileNotFoundException {

        this.currLineIdx = 0;
        this.currColumnIdx = 0;
        this.inputBuffer = new BufferedReader(new FileReader(inputPath));

    }

    public boolean hasNext() throws IOException {

        String line = currLine != null ? currLine.substring(currColumnIdx) : null;

        while (line == null || !line.matches("[\\s]*[^\\s].*")) {
            line = inputBuffer.readLine();
            currLineIdx++;
            currColumnIdx = 0;

            if (line == null) {
                return false;
            }

            currLine = line;
        }

        return true;

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

        if (isDigitOrId(currChar)) {
            return makeToken(tokenValue.toString(), tokenLine, tokenColumn);
        }

        if (isConstString(currChar)) {
            return makeToken(tokenValue.toString(), tokenLine, tokenColumn);
        }

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
                if (currChar == ' ') { break; }
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
        } else if (currChar == '-') {
            tokenValue.append(currChar);
            currChar = nextChar();

            if (currChar == '-') {
                tokenValue.append(currChar);
                currColumnIdx++;
            }

            return true;
        } else if (currChar == '+') {
            tokenValue.append(currChar);
            currChar = nextChar();

            if (currChar == '=' || currChar == '+') {
                tokenValue.append(currChar);
                currColumnIdx++;
            }

            return true;
        } else if (currChar == '*') {
            tokenValue.append(currChar);
            currChar = nextChar();
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

    private Token makeToken(String value, int line, int column) {

        Token token = new Token(value.trim(), line, column, findCategory(value));
        currToken = token;
        return token;

    }

    private Tokens findCategory(String value) {

        if (LexicalTable.map.containsKey(value.trim())) {
            return LexicalTable.map.get(value.trim());
        } else {
            return isConsOrId(value);
        }

    }

    private Tokens isConsOrId(String value) {

        if (value.matches("\\d+")) {
            return Tokens.consNumInt;
        } else if (value.matches("(\\d)+\\.(\\d)+")) {
            return Tokens.consNumFloat;
        } else if (value.equals("true") || value.equals("false")) {
            return Tokens.consBool;
        } else if (value.startsWith("\'") && value.length() == 1 && value.endsWith("\'")) {
            return Tokens.consChar;
        } else if (value.startsWith("\"") && value.length() > 1 && value.endsWith("\"")) {
            return Tokens.consString;
        } else if(value.matches("[a-z_A-Z](\\w)*")) {
            return Tokens.id;
        }

        return Tokens.unknown;

    }

}
