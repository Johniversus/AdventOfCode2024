package de.johniverse.adventofcode.tag2;

import de.johniverse.adventofcode.share.InputLoader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Main2 {
    final static int MIN_ABSTAND = 1;
    final static int MAX_ABSTAND = 3;

    public static void main(String[] args) {
        List<String> berichte = InputLoader.getInput("adventofcode/tag2/input.txt");

        int sichereBerichteCount = 0;

        for (String bericht : berichte) {
            if (berichtValid(bericht))
                sichereBerichteCount++;
        }

        System.out.println("Sichere Berichte: " + sichereBerichteCount);

    }

    private static boolean berichtValid(String bericht) {
        int[] ebenen = Arrays.stream(bericht.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        boolean berichtSicher = ebenenValid(ebenen);

        if (!berichtSicher) {
            for (int i = 0; i < ebenen.length; i++) {
                int actualIndex = i;
                int[] cutedEbenen = IntStream.range(0, ebenen.length)
                        .filter(j -> j != actualIndex)
                        .map(j -> ebenen[j])
                        .toArray();

                if (ebenenValid(cutedEbenen))
                    berichtSicher = true;
            }
        }

        return berichtSicher;
    }

    private static boolean ebenenValid(int[] ebenen) {
        boolean berichtSicher = true;
        boolean aufsteigend = false;
        for (int i = 1; (i < ebenen.length && berichtSicher); i++) {
            if (i == 1) {
                int ebeneBefore = ebenen[i - 1];
                int ebeneActual = ebenen[i];
                aufsteigend = ebeneActual > ebeneBefore;

                if (abstandInvalid(ebeneBefore, ebeneActual))
                    berichtSicher = false;

            } else {
                int ebeneBefore = ebenen[i - 1];
                int ebeneActual = ebenen[i];

                if (aufsteigend) {
                    if ((ebeneBefore >= ebeneActual) || abstandInvalid(ebeneBefore, ebeneActual))
                        berichtSicher = false;
                } else {
                    if ((ebeneBefore <= ebeneActual) || abstandInvalid(ebeneBefore, ebeneActual))
                        berichtSicher = false;
                }
            }
        }

        return berichtSicher;
    }

    private static boolean abstandInvalid(int ebeneBefore, int ebeneActual) {
        int abstand = Math.abs(ebeneBefore - ebeneActual);

        return abstand < MIN_ABSTAND || abstand > MAX_ABSTAND;
    }
}
