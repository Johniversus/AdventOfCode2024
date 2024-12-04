package de.johniverse.adventofcode.tag4;

import de.johniverse.adventofcode.share.InputLoader;

import java.util.List;

public class Main1 {
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
            for (int i = 0; i < aktuelleZeile.length(); i++) {
                String tempZeile = aktuelleZeile.substring(i);

                // aktuelle zeile überprüfen
                if (tempZeile.startsWith("XMAS") || tempZeile.startsWith("SAMX")) {
                    tempXmasCount++;
                }

                // die letzten 3 Zeilen müssen nicht mehr vertikal oder diagonal überprüft werden
                if (zeileCount <= input.size() - 4) {
                    String zeile1 = input.get(zeileCount + 1);
                    String zeile2 = input.get(zeileCount + 2);
                    String zeile3 = input.get(zeileCount + 3);

                    // überprüfen ob X vorkommt
                    if (tempZeile.startsWith("X")) {
                        if (checkVertikalDownX(i, zeile1, zeile2, zeile3)) {
                            tempXmasCount++;
                        }

                        if (checkDiagonalLinksX(i, zeile1, zeile2, zeile3)) {
                            tempXmasCount++;
                        }

                        if (checkDiagonalRechtsX(i, zeile1, zeile2, zeile3)) {
                            tempXmasCount++;
                        }
                    }

                    // überprüfen ob S vorkommt
                    else if (tempZeile.startsWith("S")) {
                        if (checkVertikalDownS(i, zeile1, zeile2, zeile3)) {
                            tempXmasCount++;
                        }

                        if (checkDiagonalLinksS(i, zeile1, zeile2, zeile3)) {
                            tempXmasCount++;
                        }

                        if (checkDiagonalRechtsS(i, zeile1, zeile2, zeile3)) {
                            tempXmasCount++;
                        }
                    }
                }
            }
            xmasCount += tempXmasCount;
            zeileCount++;
        }

        return xmasCount;
    }

    private static boolean checkVertikalDownX(int i, String zeile1, String zeile2, String zeile3) {
        return zeile1.charAt(i) == 'M' && zeile2.charAt(i) == 'A' && zeile3.charAt(i) == 'S';
    }

    private static boolean checkVertikalDownS(int i, String zeile1, String zeile2, String zeile3) {
        return zeile1.charAt(i) == 'A' && zeile2.charAt(i) == 'M' && zeile3.charAt(i) == 'X';
    }

    private static boolean checkDiagonalLinksX(int i, String zeile1, String zeile2, String zeile3) {
        if (i < 3)
            return false;

        char char1 = zeile1.charAt(i - 1);
        char char2 = zeile2.charAt(i - 2);
        char char3 = zeile3.charAt(i - 3);

        return char1 == 'M'
                && char2 == 'A'
                && char3 == 'S';
    }

    private static boolean checkDiagonalRechtsX(int i, String zeile1, String zeile2, String zeile3) {
        if (i > zeile1.length() - 4)
            return false;

        char char1 = zeile1.charAt(i + 1);
        char char2 = zeile2.charAt(i + 2);
        char char3 = zeile3.charAt(i + 3);

        return char1 == 'M'
                && char2 == 'A'
                && char3 == 'S';
    }

    private static boolean checkDiagonalLinksS(int i, String zeile1, String zeile2, String zeile3) {
        if (i < 3)
            return false;

        char char1 = zeile1.charAt(i - 1);
        char char2 = zeile2.charAt(i - 2);
        char char3 = zeile3.charAt(i - 3);

        return char1 == 'A'
                && char2 == 'M'
                && char3 == 'X';
    }

    private static boolean checkDiagonalRechtsS(int i, String zeile1, String zeile2, String zeile3) {
        if (i > zeile1.length() - 4)
            return false;

        char char1 = zeile1.charAt(i + 1);
        char char2 = zeile2.charAt(i + 2);
        char char3 = zeile3.charAt(i + 3);

        return char1 == 'A'
                && char2 == 'M'
                && char3 == 'X';
    }
}
