package de.johniverse.adventofcode.tag2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main1 {

    final static int MIN_ABSTAND = 1;
    final static int MAX_ABSTAND = 3;

    public static void main(String[] args) {
        String inputFileName = "adventofcode/tag2/input.txt";

        try (InputStream inputStream = de.johniverse.adventofcode.tag1.Main1.class.getClassLoader().getResourceAsStream(inputFileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            if (inputStream == null) {
                throw new IllegalArgumentException("Datei nicht gefunden: " + inputFileName);
            }

            List<String> berichte = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                berichte.add(line);
            }

            int sichereBerichteCount = 0;

            for (String bericht : berichte) {
                if (berichtValid(bericht))
                    sichereBerichteCount++;
            }

            System.out.println("Sichere Berichte: " + sichereBerichteCount);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean berichtValid(String bericht) {
        int[] ebenen = Arrays.stream(bericht.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        boolean berichtSicher = true;
        boolean aufsteigend = false;
        for (int i = 1; (i < ebenen.length && berichtSicher); i++) {
            if (i == 1) {
                int ebeneBefore = ebenen[i - 1];
                int ebeneActual = ebenen[i];
                aufsteigend = ebeneActual > ebeneBefore;

                if (abstandInvalide(ebeneBefore, ebeneActual))
                    berichtSicher = false;

            } else {
                int ebeneBefore = ebenen[i - 1];
                int ebeneActual = ebenen[i];

                if (aufsteigend) {
                    if ((ebeneBefore >= ebeneActual) || abstandInvalide(ebeneBefore, ebeneActual))
                        berichtSicher = false;
                } else {
                    if ((ebeneBefore <= ebeneActual) || abstandInvalide(ebeneBefore, ebeneActual))
                        berichtSicher = false;
                }
            }
        }
        return berichtSicher;
    }

    private static boolean abstandInvalide(int ebeneBefore, int ebeneActual) {
        int abstand = Math.abs(ebeneBefore - ebeneActual);

        return abstand < MIN_ABSTAND || abstand > MAX_ABSTAND;
    }
}
