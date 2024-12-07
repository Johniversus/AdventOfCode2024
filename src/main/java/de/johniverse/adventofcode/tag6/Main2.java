package de.johniverse.adventofcode.tag6;

import de.johniverse.adventofcode.share.InputLoader;
import io.vavr.Tuple2;

import java.util.List;

public class Main2 {
    private static Tuple2<Integer, Integer> positionWaechter;
    private static boolean waechterImKartiertenBereich;

    private static boolean geradegedreht = false;
    private static boolean linieGekreuzt = false;

    public static void main(String[] args) {
        List<String> input = InputLoader.getInput("adventofcode/tag6/inputTest.txt");

        positionWaechter = getPositionWaechter(input);

        waechterImKartiertenBereich = true;
        while (waechterImKartiertenBereich) {
            rueckeWaechterEinePostionVor(input);
        }

        int ergebnis = getMoeglichkeitenCount(input);

        System.out.println("Ergebnis: " + ergebnis);

    }

    private static int getMoeglichkeitenCount(List<String> input) {
        int count = 0;
        for (int i = 1; i < input.size() - 1; i++) {
            for (int j = 1; j < input.get(i).length() - 1; j++) {
                char aktuellerChar = input.get(i).charAt(j);
                if (aktuellerChar == '+') {
                    String reiheDarueber = input.get(i - 1);
                    String aktuelleReihe = input.get(i);
                    String reiheDarunter = input.get(i + 1);

                    if (reiheDarueber.charAt(j) == '#') {
                        count++;
                    } else if (reiheDarunter.charAt(j) == '#') {
                        count++;
                    } else if (aktuelleReihe.charAt(j - 1) == '#') {
                        count++;
                    } else if (aktuelleReihe.charAt(j + 1) == '#') {
                        count++;
                    }
                }
            }
        }
        return count - 1;
    }

    private static void rueckeWaechterEinePostionVor(List<String> input) {
        int aktuelleZeile = positionWaechter._1();
        int aktuelleSpalte = positionWaechter._2();
        String aktuelleReihe = input.get(aktuelleZeile);
        char aktuelleBlickrichtung = aktuelleReihe.charAt(aktuelleSpalte);

        switch (aktuelleBlickrichtung) {
            case '^':
                if (aktuelleZeile - 1 < 0) { // Wenn der Kartierte Bereich verlassen wird
                    input.set(aktuelleZeile, aktuelleReihe.replace('^', 'X'));
                    waechterImKartiertenBereich = false;
                    System.out.println("Der Kartierte Bereich wurde verlassen!");
                }
                else if (input.get(aktuelleZeile - 1).charAt(aktuelleSpalte) == '#') { // Wenn ein Hindernis im Weg ist wird um 90 Grad nach Rechts gedreht
                    input.set(aktuelleZeile, aktuelleReihe.replace('^', '>'));
                    geradegedreht = true;
                }
                else { // Wenn alles in Ordnung ist wird ein Feld vorgerückt und das vorherige Feld auf X gesetzt damit es markiert ist
                    String reiheDaueber = input.get(aktuelleZeile - 1);
                    input.set(aktuelleZeile - 1, reiheDaueber.substring(0, aktuelleSpalte) + '^' + reiheDaueber.substring(aktuelleSpalte + 1));
                    positionWaechter = new Tuple2<>(aktuelleZeile - 1, aktuelleSpalte);

                    if (geradegedreht || linieGekreuzt) {
                        input.set(aktuelleZeile, aktuelleReihe.replace('^', '+'));
                        geradegedreht = false;
                        linieGekreuzt = false;

                    } else {
                        if (reiheDaueber.charAt(aktuelleSpalte) == '-')
                            linieGekreuzt = true;

                        input.set(aktuelleZeile, aktuelleReihe.replace('^', '|'));
                    }
                }
                break;
            case 'v':
                if (aktuelleZeile + 1 >= aktuelleReihe.length()) { // Wenn der Kartierte Bereich verlassen wird
                    input.set(aktuelleZeile, aktuelleReihe.replace('v', 'X'));
                    waechterImKartiertenBereich = false;
                    System.out.println("Der Kartierte Bereich wurde verlassen!");
                }
                else if (input.get(aktuelleZeile + 1).charAt(aktuelleSpalte) == '#') { // Wenn ein Hindernis im Weg ist wird um 90 Grad nach Rechts gedreht
                    input.set(aktuelleZeile, aktuelleReihe.replace('v', '<'));
                    geradegedreht = true;
                }
                else { // Wenn alles in Ordnung ist wird ein Feld vorgerückt und das vorherige Feld auf X gesetzt damit es markiert ist
                    String reiheDarunter = input.get(aktuelleZeile + 1);
                    input.set(aktuelleZeile + 1, reiheDarunter.substring(0, aktuelleSpalte) + 'v' + reiheDarunter.substring(aktuelleSpalte + 1));
                    positionWaechter = new Tuple2<>(aktuelleZeile + 1, aktuelleSpalte);

                    if (geradegedreht || linieGekreuzt) {
                        input.set(aktuelleZeile, aktuelleReihe.replace('v', '+'));
                        geradegedreht = false;
                        linieGekreuzt = false;

                    } else {
                        if (reiheDarunter.charAt(aktuelleSpalte) == '-')
                            linieGekreuzt = true;


                        input.set(aktuelleZeile, aktuelleReihe.replace('v', '|'));
                    }
                }
                break;
            case '<':
                if (aktuelleSpalte - 1 < 0) { // Wenn der Kartierte Bereich verlassen wird
                    input.set(aktuelleZeile, aktuelleReihe.replace('<', 'X'));
                    waechterImKartiertenBereich = false;
                    System.out.println("Der Kartierte Bereich wurde verlassen!");
                }
                else if (input.get(aktuelleZeile).charAt(aktuelleSpalte - 1) == '#') { // Wenn ein Hindernis im Weg ist wird um 90 Grad nach Rechts gedreht
                    input.set(aktuelleZeile, aktuelleReihe.replace('<', '^'));
                    geradegedreht = true;
                }
                else { // Wenn alles in Ordnung ist wird ein Feld vorgerückt und das vorherige Feld auf X gesetzt damit es markiert ist
                    positionWaechter = new Tuple2<>(aktuelleZeile, aktuelleSpalte - 1);

                    if (geradegedreht || linieGekreuzt) {
                        input.set(aktuelleZeile, aktuelleReihe.substring(0, aktuelleSpalte - 1) + '<' + '+' + aktuelleReihe.substring(aktuelleSpalte + 1));
                        geradegedreht = false;
                        linieGekreuzt = false;

                    } else {
                        if (aktuelleReihe.charAt(aktuelleSpalte - 1) == '|')
                            linieGekreuzt = true;

                        input.set(aktuelleZeile, aktuelleReihe.substring(0, aktuelleSpalte - 1) + '<' + '-' + aktuelleReihe.substring(aktuelleSpalte + 1));
                    }
                }
                break;
            case '>':
                if (aktuelleSpalte + 1 >= aktuelleReihe.length()) { // Wenn der Kartierte Bereich verlassen wird
                    input.set(aktuelleZeile, aktuelleReihe.replace('>', 'X'));
                    waechterImKartiertenBereich = false;
                    System.out.println("Der Kartierte Bereich wurde verlassen!");
                }
                else if (input.get(aktuelleZeile).charAt(aktuelleSpalte + 1) == '#') { // Wenn ein Hindernis im Weg ist wird um 90 Grad nach Rechts gedreht
                    input.set(aktuelleZeile, aktuelleReihe.replace('>', 'v'));
                    geradegedreht = true;
                }
                else { // Wenn alles in Ordnung ist wird ein Feld vorgerückt und das vorherige Feld auf X gesetzt damit es markiert ist
                    positionWaechter = new Tuple2<>(aktuelleZeile, aktuelleSpalte + 1);

                    if (geradegedreht || linieGekreuzt) {
                        input.set(aktuelleZeile, aktuelleReihe.substring(0, aktuelleSpalte) + '+' + '>' + aktuelleReihe.substring(aktuelleSpalte + 2));
                        geradegedreht = false;
                        linieGekreuzt = false;

                    } else {
                        if (aktuelleReihe.charAt(aktuelleSpalte + 1) == '|')
                            linieGekreuzt = true;

                        input.set(aktuelleZeile, aktuelleReihe.substring(0, aktuelleSpalte) + '-' + '>' + aktuelleReihe.substring(aktuelleSpalte + 2));
                    }
                }
                break;

            default:
                System.out.println("Irgend etwas stimmt hier nicht im Switch");
                waechterImKartiertenBereich = false;
        }
    }

    private static Tuple2<Integer, Integer> getPositionWaechter(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                char aktuellerChar = input.get(i).charAt(j);
                switch (aktuellerChar) {
                    case '^', 'v', '<', '>':
                        return new Tuple2<>(i, j);
                }
            }
        }
        return null;
    }
}
