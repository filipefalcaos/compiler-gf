import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.File;
import java.io.IOException;
import lexical.LexicalAnalyzer;
import syntatic.SyntaticAnalyzer;


public class Main {

    @Parameter(names = "--input", description = "Path to the input file", order = 1)
    private String inputPath = "";

    public static void main(String ... argv) {

        // Create a JCommander instance and start the compiler
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(argv);
        main.run();

    }

    private void run() {

        try {
            File tokensFile = new File("output_tokens.txt");  // Tokens output file
            boolean exists = tokensFile.exists();

            if (exists) {
                tokensFile.delete();
            }

            // Start the compilation process
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(inputPath);
            SyntaticAnalyzer syntaticAnalyzer = new SyntaticAnalyzer(lexicalAnalyzer);

            if (syntaticAnalyzer.analyze()) {
                System.out.println("\n---------- ACCEPTED ----------");
            } else {
                System.out.println("\n---------- ERROR ----------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
