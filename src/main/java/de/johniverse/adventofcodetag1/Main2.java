package de.johniverse.adventofcodetag1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        String inputFileName = "tag1/OrtListe.txt";

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

            long count = 0;

            for (int i = 0; i < orte1.size(); i++) {
                int leftNum = orte1.get(i);

                long aehnlichkeitswert = orte2.stream()
                        .filter(number -> number == leftNum)
                        .count();


                count += leftNum * aehnlichkeitswert;
            }

            System.out.println("Ergebnis: " + count);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
