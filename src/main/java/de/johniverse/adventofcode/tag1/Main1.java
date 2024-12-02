package de.johniverse.adventofcode.tag1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main1 {
    public static void main(String[] args) {
        String inputFileName = "adventofcode/tag1/input.txt";

        try (InputStream inputStream = Main1.class.getClassLoader().getResourceAsStream(inputFileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            if (inputStream == null) {
                throw new IllegalArgumentException("Datei nicht gefunden: " + inputFileName);
            }

            List<Integer> orte1 = new ArrayList<>();
            List<Integer> orte2 = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
