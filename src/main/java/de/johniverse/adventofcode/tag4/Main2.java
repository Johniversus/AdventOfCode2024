package de.johniverse.adventofcode.tag4;

import de.johniverse.adventofcode.share.InputLoader;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        List<String> input = InputLoader.getInput("adventofcode/tag4/input.txt");

        int ergebnis = getXMASCount(input);

        System.out.println("Ergebnis: " + ergebnis);
    }

    private static int getXMASCount(List<String> input) {

        int xmasCount = 0;

        int zeileCount = 0;
        for (String aktuelleZeile : input) {
            int tempXmasCount = 0;

            // die letzten 2 Zeilen auslassen
            if (zeileCount < input.size() - 2) {
                for (int i = 0; i < aktuelleZeile.length() - 2; i++) {
                    String tempZeile = aktuelleZeile.substring(i);
                    if ((tempZeile.startsWith("M") || tempZeile.startsWith("S")) && (tempZeile.charAt(2) == 'M' || tempZeile.charAt(2) == 'S')) {
                        char charLinks = tempZeile.charAt(0);
                        char charRechts = tempZeile.charAt(2);

                        String zeile1 = input.get(zeileCount + 1);
                        String zeile2 = input.get(zeileCount + 2);

                        if (checkDiagonalRight(i, zeile1, zeile2, charLinks) && checkDiagonalLeft(i + 2, zeile1, zeile2, charRechts)) {
                                tempXmasCount++;
                            }

                    }
                }
            }
            System.out.println("Zeile: " + zeileCount + ", XmasCount: " + tempXmasCount);
            xmasCount += tempXmasCount;
            zeileCount++;
        }

        return xmasCount;
    }

    private static boolean checkDiagonalRight(int i, String zeile1, String zeile2, char charLinks) {
        if (i > zeile1.length() - 2) {
            return false;
        }

        char char1 = zeile1.charAt(i + 1);
        char char2 = zeile2.charAt(i + 2);

        return char1 == 'A'
                && char2 == (charLinks == 'M' ? 'S' : 'M');
    }

    private static boolean checkDiagonalLeft(int i, String zeile1, String zeile2, char charRechts) {
        char char1 = zeile1.charAt(i - 1);
        char char2 = zeile2.charAt(i - 2);

        return char1 == 'A'
                && char2 == (charRechts == 'M' ? 'S' : 'M');
    }
}
