package de.johniverse.adventofcode.tag1;

import de.johniverse.adventofcode.share.InputLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        List<String> input = InputLoader.getInput("adventofcode/tag1/input.txt");

        List<Integer> orte1 = new ArrayList<>();
        List<Integer> orte2 = new ArrayList<>();

        for (String line : input) {
            orte1.add(Integer.parseInt(line.split(" {3}")[0]));
            orte2.add(Integer.parseInt(line.split(" {3}")[1]));
        }

        long count = 0;

        for (int i = 0; i < orte1.size(); i++) {
            int leftNum = orte1.get(i);

            long aehnlichkeitswert = orte2.stream()
                    .filter(number -> number == leftNum)
                    .count();

            count += leftNum * aehnlichkeitswert;
        }

        System.out.println("Ergebnis: " + count);
    }
}
