package de.johniverse.adventofcode.tag9;

import de.johniverse.adventofcode.share.InputLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main1 {
    public static void main(String[] args) {
        String input = InputLoader.getInput("adventofcode/tag9/input.txt").getFirst();
        System.out.println("Step1, Input einlesen: " + input);

        List<String> idDiskList = buildIdDiskList(input);
        System.out.println("Step2, DiskMap in IDDiskList umwandeln: ");
        idDiskList.forEach(System.out::println);

        List<String> compIdDiskList = buildCompIdDiskList(idDiskList);
        System.out.println("Step3, IDDiskMap in CompIDDiskList umwandeln: ");
        compIdDiskList.forEach(System.out::println);

        List<String> compIdDiskListCutted = cutCompIdDikList(compIdDiskList);
        System.out.println("Step4, IDDiskMap in CompIDDiskListCutted umwandeln: ");
        compIdDiskListCutted.forEach(System.out::println);

        long checksum = berechneChecksum(compIdDiskListCutted);
        System.out.println("Ergebnis: " + checksum);
    }

    static List<String> buildIdDiskList(final String input) {
        List<String> idDiskList = new ArrayList<>();

        boolean isFile = true;
        int fileCount = 0;
        for (int i = 0; i < input.length(); i++) {
            int value = Integer.parseInt(input.substring(i, i + 1));
            if (isFile) {
                for (int j = 0; j < value; j++) {
                    idDiskList.add("" + fileCount);
                }
                isFile = false;
                fileCount++;
            } else {
                for (int j = 0; j < value; j++) {
                    idDiskList.add(".");
                }
                isFile = true;
            }
        }
        return idDiskList;
    }

    private static List<String> buildCompIdDiskList(final List<String> idDiskList) {
        List<String> compIdDiskList = new ArrayList<>(idDiskList);

        boolean spaceLeft = true;
        int indexLastFileblock = compIdDiskList.size() - 1;
        while (spaceLeft) {
            int indexFirstSpace = compIdDiskList.indexOf(".");
            if (indexFirstSpace - 1 == indexLastFileblock) {
                spaceLeft = false;
            } else {
                if (!compIdDiskList.get(indexLastFileblock).equals(".")) {
                    compIdDiskList.set(indexFirstSpace, compIdDiskList.get(indexLastFileblock));
                    compIdDiskList.set(indexLastFileblock, ".");
                }
            }
            indexLastFileblock--;
        }
        return compIdDiskList;
    }

    private static List<String> cutCompIdDikList(final List<String> compIdDiskList) {
        List<String> compIdDiskListCutted = new ArrayList<>();

        for (int i = 0; i < compIdDiskList.size(); i++) {
            if (!compIdDiskList.get(i).equals(".")) {
                compIdDiskListCutted.add(compIdDiskList.get(i));
            }
        }

        return compIdDiskListCutted;
    }

    private static long berechneChecksum(final List<String> compIdDiskListCutted) {
        AtomicInteger i = new AtomicInteger(0);

        return compIdDiskListCutted.stream()
                .mapToLong(Long::parseLong)
                .map(value -> i.getAndIncrement() * value)
                .sum();
    }
}
