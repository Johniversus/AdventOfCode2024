package de.johniverse.adventofcode.tag3;

import de.johniverse.adventofcode.share.InputLoader;

import java.util.ArrayList;
import java.util.List;

public class Main1 {
    public static void main(String[] args) {
        List<String> input = InputLoader.getInput("adventofcode/tag3/input.txt");

        List<String> berechnungen = new ArrayList<String>();

        for (String line : input) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'm' && line.startsWith("mul(", i)) {
                    StringBuilder berechnung = new StringBuilder();

                    for (int j = i + 4; Character.isDigit(line.charAt(j)); j++) {
                        char aktullerChar = line.charAt(j);
                        berechnung.append(aktullerChar);
                        if (!Character.isDigit(line.charAt(j + 1))) {
                            berechnung.append(",");
                            i = j + 2;
                        }
                    }

                    for (int j = i; Character.isDigit(line.charAt(j)); j++) {
                        char aktullerChar = line.charAt(j);
                        berechnung.append(aktullerChar);
                        if (!Character.isDigit(line.charAt(j + 1))) {
                            i = j + 1;
                            if (line.charAt(j + 1) == ')') {
                                berechnungen.add(berechnung.toString());
                            }
                        }
                    }
                }
            }
        }
        berechnungen.forEach(System.out::println);

        int ergebnis = 0;
        for (String line : berechnungen) {
            int multiplikant = Integer.parseInt(line.substring(0, line.indexOf(',')));
            int multiplikator = Integer.parseInt(line.substring(line.indexOf(',') + 1));

            ergebnis += multiplikant * multiplikator;
        }

        System.out.println("Ergebnis: " + ergebnis);
    }
}
