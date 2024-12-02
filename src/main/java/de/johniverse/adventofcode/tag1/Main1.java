package de.johniverse.adventofcode.tag1;

import de.johniverse.adventofcode.share.InputLoader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main1 {
    public static void main(String[] args) {
        List<String> input = InputLoader.getInput("adventofcode/tag1/input.txt");

        List<Integer> orte1 = new ArrayList<>();
        List<Integer> orte2 = new ArrayList<>();

        for (String line : input) {
            orte1.add(Integer.parseInt(line.split(" {3}")[0]));
            orte2.add(Integer.parseInt(line.split(" {3}")[1]));
        }

        Collections.sort(orte1);
        Collections.sort(orte2);

        int count = 0;

        for (int i = 0; i < orte1.size(); i++) {
            count += Math.abs(orte2.get(i) - orte1.get(i));
            System.out.println("Count: " + count + ", " + orte2.get(i) + " + " + orte1.get(i) + " = " + Math.abs(orte2.get(i) - orte1.get(i)));
        }

        System.out.println("Ergebnis: " + count);
    }
}
