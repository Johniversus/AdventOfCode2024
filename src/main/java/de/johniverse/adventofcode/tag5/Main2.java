package de.johniverse.adventofcode.tag5;

import de.johniverse.adventofcode.share.InputLoader;
import io.vavr.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

public class Main2 {
    public static void main(String[] args) {
        List<String> input = InputLoader.getInput("adventofcode/tag5/input.txt");

        List<Tuple2<Integer, Integer>> regeln = createRegeln(input);
        List<List<Integer>> rawUpdates = createRawUpdates(input);

        List<List<Integer>> falscheUpdates = new ArrayList<>();

        for (List<Integer> rawUpdate : rawUpdates) {
            List<Tuple2<Integer, Integer>> anwendbareRegeln = regeln.stream()
                    .filter(regel -> rawUpdate.contains(regel._1()) && rawUpdate.contains(regel._2()))
                    .toList();

            if (updateInvalide(rawUpdate, anwendbareRegeln)) {
                sortFalschesRawUpdate(rawUpdate, anwendbareRegeln);
                falscheUpdates.add(rawUpdate);
            }
        }

        // Berechnung Ergebnis:
        int ergebnis = falscheUpdates.stream()
                .mapToInt(update -> update.get(update.size() / 2))
                .sum();

        System.out.println("Ergebnis: " + ergebnis);
    }

    private static boolean updateInvalide(List<Integer> rawUpdate, List<Tuple2<Integer, Integer>> anwendbareRegeln) {
        return anwendbareRegeln.stream()
                .anyMatch(anwendbareRegel -> {
                    int ersteRegel = anwendbareRegel._1();
                    int zweiteRegel = anwendbareRegel._2();

                    int stelleErsteZahl = rawUpdate.indexOf(ersteRegel);
                    int stelleZweiteZahl = rawUpdate.indexOf(zweiteRegel);

                    return stelleErsteZahl > stelleZweiteZahl;
                });

    }

    private static void sortFalschesRawUpdate(List<Integer> rawUpdate, List<Tuple2<Integer, Integer>> anwendbareRegeln) {
        boolean somethingChanged = true;
        while (somethingChanged) {
            somethingChanged = anwendbareRegeln.stream()
                    .anyMatch(anwendbareRegel -> {
                        int ersteRegel = anwendbareRegel._1();
                        int zweiteRegel = anwendbareRegel._2();

                        int stelleErsteZahl = rawUpdate.indexOf(ersteRegel);
                        int stelleZweiteZahl = rawUpdate.indexOf(zweiteRegel);

                        if (stelleErsteZahl > stelleZweiteZahl) {
                            Collections.swap(rawUpdate, stelleErsteZahl, stelleZweiteZahl);
                            return true;
                        }
                        return false;
                    });
        }
    }

    private static List<Tuple2<Integer, Integer>> createRegeln(List<String> input) {
        return input.stream()
                .takeWhile(zeile -> !zeile.isEmpty())
                .map(zeile -> {
                    String[] tempRegeln = zeile.split("\\|");
                    return new Tuple2<>(Integer.parseInt(tempRegeln[0]), Integer.parseInt(tempRegeln[1]));
                })
                .collect(Collectors.toList());
    }

    private static List<List<Integer>> createRawUpdates(List<String> input) {
        return input.stream()
                .dropWhile(zeile -> !zeile.isEmpty())
                .skip(1)
                .map(zeile -> Arrays.stream(zeile.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
                )
                .collect(Collectors.toList());
    }
}
