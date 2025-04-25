package de.johniverse.adventofcode.tag8;

import de.johniverse.adventofcode.share.InputLoader;
import io.vavr.Tuple2;

import java.util.*;

public class Main2 {
    private static int schwingungsbauchCount = 0;
    public static void main(String[] args) {

        List<String> inputList = InputLoader.getInput("adventofcode/tag8/Input.txt");

        Map<Character, List<Tuple2<Integer, Integer>>> frequenzMap = new HashMap<>();

        for (int y = 0; y < inputList.size(); y++) {
            String line = inputList.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c != '.' && c != '#') {
                    if (frequenzMap.containsKey(c)) {
                        frequenzMap.get(c).add(new Tuple2<>(y, x));
                    } else {
                        frequenzMap.put(c, new ArrayList<>(List.of(new Tuple2<>(y, x))));
                    }
                }
            }
        }

        frequenzMap.forEach((k, v) -> System.out.println(k + ": " + v + " L: " + v.size()));

        // Jede Frequenz durchgehen
        frequenzMap.forEach((key, antennen) -> {
            int anzahlFunkwege = berechneFunkwege(antennen.size());

            List<Tuple2<Integer, Integer>> funkwegePaare = berechneFunkwegePaare(anzahlFunkwege);

            for (Tuple2<Integer, Integer> funkwegPaar : funkwegePaare) {

                Tuple2<Integer, Integer> antenne1 = antennen.get(funkwegPaar._1);
                Tuple2<Integer, Integer> antenne2 = antennen.get(funkwegPaar._2);

                int abstandY = Math.abs(antenne1._1 - antenne2._1);
                int abstandX = Math.abs(antenne1._2 - antenne2._2);

                int y1 = antenne1._1, y2 = antenne2._1;
                int x1 = antenne1._2, x2 = antenne2._2;
                if (y1 < y2) { // Antenne 1 oben
                    if (x1 < x2) { // Antenne 1 links oben
                        setzeSchwingungsbauch(inputList, antenne1._1 - abstandY, antenne1._2 - abstandX);
                        setzeSchwingungsbauch(inputList, antenne2._1 + abstandY, antenne2._2 + abstandX);
                    } else if (x1 > x2) { // Antenne 1 rechts oben
                        setzeSchwingungsbauch(inputList, antenne1._1 - abstandY, antenne1._2 + abstandX);
                        setzeSchwingungsbauch(inputList, antenne2._1 + abstandY, antenne2._2 - abstandX);
                    } else { // Antenne 1 mittig oben
                        setzeSchwingungsbauch(inputList, antenne1._1 - abstandY, antenne1._2);
                        setzeSchwingungsbauch(inputList, antenne2._1 + abstandY, antenne2._2);
                    }
                } else if (y1 > y2) { // Antenne 1 unten
                    if (x1 < x2) { // Antenne 1 links unten
                        setzeSchwingungsbauch(inputList, antenne1._1 + abstandY, antenne1._2 - abstandX);
                        setzeSchwingungsbauch(inputList, antenne2._1 - abstandY, antenne2._2 + abstandX);
                    } else if (x1 > x2) { // Antenne 1 rechts unten
                        setzeSchwingungsbauch(inputList, antenne1._1 + abstandY, antenne1._2 + abstandX);
                        setzeSchwingungsbauch(inputList, antenne2._1 - abstandY, antenne2._2 - abstandX);
                    } else { // Antenne 1 mittig unten
                        setzeSchwingungsbauch(inputList, antenne1._1 + abstandY, antenne1._2);
                        setzeSchwingungsbauch(inputList, antenne2._1 - abstandY, antenne2._2);
                    }
                } else { // Antenne 1 gleiche höhe
                    if (x1 < x2) { // Antenne 1 links gleiche höhe
                        setzeSchwingungsbauch(inputList, antenne1._1, antenne1._2 - abstandX);
                        setzeSchwingungsbauch(inputList, antenne2._1, antenne2._2 + abstandX);
                    } else { // Antenne 1 rechts gleiche höhe
                        setzeSchwingungsbauch(inputList, antenne1._1, antenne1._2 + abstandX);
                        setzeSchwingungsbauch(inputList, antenne2._1, antenne2._2 - abstandX);
                    }
                }
            }
        });

        inputList.forEach(System.out::println);

        int count = 0;
        for (int i = 0; i < inputList.size(); i++) {
            String line = inputList.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '#') count++;
            }
        }
        System.out.println("SchwingungsbauchCount: " + schwingungsbauchCount);
    }

    private static void setzeSchwingungsbauch(List<String> inputList, int y, int x) {
        if ((y >= 0 && y < inputList.size()) && (x >= 0 && x < inputList.getFirst().length())) {
            StringBuilder sb2 = new StringBuilder(inputList.get(y));
            if (sb2.charAt(x) != '#') schwingungsbauchCount++;

            if (sb2.charAt(x) == '.') {
                sb2.replace(x, x + 1, "#");
                inputList.set(y, sb2.toString());
            }
        }
    }

    private static int berechneFunkwege(int anzahlAntennen) {
        return switch (anzahlAntennen) {
            case 2 -> 1;                    // 2 Antennen: 1 Funkweg, 2 Schwingungsbäuche
            case 3 -> 3;                    // 3 Antennen: 3 Funkwege, 6 Schwindungsbäuche
            case 4 -> 6;                    // 4 Antennen: 6 Funkwege, 12 Schwingungsbäuche
            default -> -1;
        };
    }

    private static List<Tuple2<Integer, Integer>> berechneFunkwegePaare(int anzahlFunkwege) {
        return switch (anzahlFunkwege) {
            case 1 -> List.of(new Tuple2<>(0, 1));
            case 3 -> List.of(new Tuple2<>(0, 1), new Tuple2<>(0, 2), new Tuple2<>(1, 2));
            case 6 -> List.of(new Tuple2<>(0, 1), new Tuple2<>(0, 2), new Tuple2<>(0, 3), new Tuple2<>(1, 2), new Tuple2<>(1, 3), new Tuple2<>(2, 3));
            default -> Collections.emptyList();
        };
    }
}
