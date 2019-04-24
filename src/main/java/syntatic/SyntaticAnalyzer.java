package syntatic;

import lexical.LexicalAnalyzer;
import lexical.Token;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


public class SyntaticAnalyzer {

    private LexicalAnalyzer lexicalAnalyzer;
    private Stack<String> analysisStack = new Stack<>();
    private List<CSVRecord> analysisTableList;
    private ArrayList<Production> productions;

    public SyntaticAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    // Perform the syntatic analysis
    // Returns true if the code is accepted and false otherwise
    public boolean analyze() throws IOException {

        // Create the grammar representation
        String grammarPath = "files/grammar.txt";
        Grammar grammar = new Grammar(grammarPath);
        productions = grammar.getProductions();

        // Load the analysis table
        String tablePath = "files/ll(1)_table.csv";
        CSVParser analysisTable = loadAnalysisTable(tablePath);
        analysisTableList = analysisTable.getRecords();

        // Add the start symbol to the analysis stack
        analysisStack.push("PROGRAM");

        // Read token
        Token token = null;
        if (lexicalAnalyzer.hasNext()) {
            token = lexicalAnalyzer.nextToken();
        }

        // Control variables
        String currAction;
        String currTokenCategory;
        String currLeftSide;

        assert token != null;
        while (!analysisStack.isEmpty()) {

            if (token == null) {
                currTokenCategory = "EOF";
            } else {
                currTokenCategory = token.getCategory().toString();
            }

            while (!analysisStack.isEmpty() && !currTokenCategory.equals(analysisStack.peek())) {
                currAction = getAction(currTokenCategory, analysisStack.peek());

                if (currAction.equals("")) {
                    return false;
                }

                // Get the current production
                if (!currAction.equals("epsilon")) {
                    String[] rightSide = getProductionFromAction(currAction);
                    String rightSideStr = Arrays.toString(rightSide).replace("[", "").
                            replace("]", "").replace(",", "");

                    // Pop the left side and push the right side of the current
                    // production rule
                    currLeftSide = analysisStack.pop();
                    for (int i = rightSide.length - 1; i >= 0; i--) {
                        analysisStack.push(rightSide[i]);
                    }

                    System.out.println("        " + currLeftSide + " -> " + rightSideStr);
                } else {
                    currLeftSide = analysisStack.pop();
                    System.out.println("        " + currLeftSide + " -> epsilon");
                }
            }

            // DAT
            if (token != null) {
                analysisStack.pop();
                System.out.println(token);
            }

            // Read the next token
            if (lexicalAnalyzer.hasNext()) {
                token = lexicalAnalyzer.nextToken();
            } else {
                token = null;  // EOF
            }
        }

        return true;

    }

    // Returns the right side of a given production
    private String[] getProductionFromAction(String currAction) {

        String action = currAction.replace("d", "");
        Production currProduction = productions.get(Integer.parseInt(action) - 1);
        return currProduction.getRight();

    }

    // Get the action from the analysis table for a given pair of:
    // token category and stack top
    private String getAction(String currCtg, String stackTop) {

        String action = "";

        for (CSVRecord cell : analysisTableList) {
            if (cell.get(0).equals(stackTop) && cell.isMapped(currCtg)) {
                action = cell.get(currCtg);
            }
        }

        return action;

    }

    // Parse the analysis table CSV
    private CSVParser loadAnalysisTable(String tablePath) throws IOException {

        Reader reader = Files.newBufferedReader(Paths.get(tablePath));
        return new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withTrim());

    }

}
