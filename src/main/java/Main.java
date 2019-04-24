/*
  Copyright 2017 Filipe Falcão Batista dos Santos
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

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
                System.out.println("\n---------- ACEITO ----------");
            } else {
                System.out.println("\n---------- ERRO ----------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
