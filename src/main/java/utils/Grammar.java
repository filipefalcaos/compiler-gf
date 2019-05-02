package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Grammar {

    private ArrayList<Production> productions = new ArrayList<>();

    public Grammar(String grammarPath) throws IOException {

        BufferedReader buffer = new BufferedReader(new FileReader(grammarPath));
        String line = buffer.readLine();

        while (line != null) {
            if (!line.equals("")) {
                productions.add(new Production(line));
            }

            line = buffer.readLine();
        }

    }

    public ArrayList<Production> getProductions() {
        return productions;
    }

}
