package syntatic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Grammar {

    private ArrayList<Production> productions = new ArrayList<>();

    Grammar(String grammarPath) throws IOException {

        BufferedReader buffer = new BufferedReader(new FileReader(grammarPath));
        String line = buffer.readLine();

        while (line != null) {
            if (!line.equals("")) {
                productions.add(new Production(line));
            }

            line = buffer.readLine();
        }

    }

    ArrayList<Production> getProductions() {
        return productions;
    }

    private ArrayList<String> calcFirst() {
        return null;
    }

    private ArrayList<String> calcFollow() {
        return null;
    }

}
