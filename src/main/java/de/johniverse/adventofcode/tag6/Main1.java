package de.johniverse.adventofcode.tag6;

import de.johniverse.adventofcode.share.InputLoader;
import io.vavr.Tuple2;

import java.util.List;

public class Main1 {
    private static Tuple2<Integer, Integer> positionWaechter;
    private static boolean waechterImKartiertenBereich;

    public static void main(String[] args) {
        List<String> input = InputLoader.getInput("adventofcode/tag6/input.txt");

        positionWaechter = getPositionWaechter(input);

        waechterImKartiertenBereich = true;
        while (waechterImKartiertenBereich) {
            rueckeWaechterEinePostionVor(input);
        }

        int ergebnis = (int) input.stream()
                        .flatMapToInt(String::chars)
                                .filter(c -> c == 'X')
                                        .count();

        System.out.println("Ergebnis: " + ergebnis);

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
                }
                else { // Wenn alles in Ordnung ist wird ein Feld vorgerückt und das vorherige Feld auf X gesetzt damit es markiert ist
                    String reiheDaueber = input.get(aktuelleZeile - 1);
                    input.set(aktuelleZeile - 1, reiheDaueber.substring(0, aktuelleSpalte) + '^' + reiheDaueber.substring(aktuelleSpalte + 1));
                    input.set(aktuelleZeile, aktuelleReihe.replace('^', 'X'));
                    positionWaechter = new Tuple2<>(aktuelleZeile - 1, aktuelleSpalte);
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
                }
                else { // Wenn alles in Ordnung ist wird ein Feld vorgerückt und das vorherige Feld auf X gesetzt damit es markiert ist
                    String reiheDarunter = input.get(aktuelleZeile + 1);
                    input.set(aktuelleZeile + 1, reiheDarunter.substring(0, aktuelleSpalte) + 'v' + reiheDarunter.substring(aktuelleSpalte + 1));
                    input.set(aktuelleZeile, aktuelleReihe.replace('v', 'X'));
                    positionWaechter = new Tuple2<>(aktuelleZeile + 1, aktuelleSpalte);
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
                }
                else { // Wenn alles in Ordnung ist wird ein Feld vorgerückt und das vorherige Feld auf X gesetzt damit es markiert ist
                    input.set(aktuelleZeile, aktuelleReihe.substring(0, aktuelleSpalte - 1) + '<' + 'X' + aktuelleReihe.substring(aktuelleSpalte + 1));
                    positionWaechter = new Tuple2<>(aktuelleZeile, aktuelleSpalte - 1);
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
                }
                else { // Wenn alles in Ordnung ist wird ein Feld vorgerückt und das vorherige Feld auf X gesetzt damit es markiert ist
                    input.set(aktuelleZeile, aktuelleReihe.substring(0, aktuelleSpalte) + 'X' + '>' + aktuelleReihe.substring(aktuelleSpalte + 2));
                    positionWaechter = new Tuple2<>(aktuelleZeile, aktuelleSpalte + 1);
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
