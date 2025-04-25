package de.johniverse.adventofcode.tag9;

import de.johniverse.adventofcode.share.InputLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static de.johniverse.adventofcode.tag9.Main1.buildIdDiskList;

public class Main2 {
    public static void main(String[] args) {
        String input = InputLoader.getInput("adventofcode/tag9/input.txt").getFirst();
        System.out.println("Step1, Input einlesen: " + input);

        List<String> idDiskList = buildIdDiskList(input);
        System.out.println("Step2, DiskMap in IDDiskList umwandeln: ");
        idDiskList.forEach(System.out::print);
        System.out.println();

        List<String> compIdDiskList = buildCompIdDiskList(idDiskList);
        System.out.println("Step3, IDDiskMap in CompIDDiskList umwandeln: ");
        compIdDiskList.forEach(System.out::print);
        System.out.println();

        long checksum = berechneChecksum(compIdDiskList);
        System.out.println("Ergebnis: " + checksum);
    }

    private static List<String> buildCompIdDiskList(final List<String> idDiskList) {
        List<String> compIdDiskList = new ArrayList<>(idDiskList);

        int aktuelleData = Integer.parseInt(idDiskList.getLast());

        while (aktuelleData >= 0) { // wird so oft durchgeführt wie es Data gibt
            int aktuelleDataFinal = aktuelleData;
            int startIndex = compIdDiskList.indexOf("" + aktuelleData);
            int groesse = (int) compIdDiskList.stream()
                    .filter(value -> value.chars().allMatch(Character::isDigit) && Integer.parseInt(value) == aktuelleDataFinal)
                    .count();

            boolean spaceEnaught = false;
            int spaceCount = 0;
            int startIndexSpace = 0;
            for (int i = 0; i < startIndex; i++) {
                if (compIdDiskList.get(i).equals(".")) {
                    if (startIndexSpace == 0) startIndexSpace = i;
                    spaceCount++;
                    if (spaceCount == groesse) {
                        spaceEnaught = true;
                        break;
                    }
                } else {
                    startIndexSpace = 0;
                    spaceCount = 0;
                }
            }

            if (spaceEnaught) {
                for (int i = 0; i < groesse; i++) {
                    compIdDiskList.set(startIndexSpace + i, "" + aktuelleData);
                    compIdDiskList.set(startIndex + i, ".");
                }
            }
            aktuelleData--;
        }
        return compIdDiskList;
    }

    private static long berechneChecksum(final List<String> compIdDiskListCutted) {
        AtomicInteger i = new AtomicInteger(0);

        return compIdDiskListCutted.stream()
                .mapToLong(value -> value.equals(".") ? 0 : Long.parseLong(value))
                .map(value -> i.getAndIncrement() * value)
                .sum();
    }
}
