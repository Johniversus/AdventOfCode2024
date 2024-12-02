package de.johniverse.adventofcode.share;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputLoader {

    private InputLoader() {}

    public static List<String> getInput(String fileName) {

        try (InputStream inputStream = InputLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            if (inputStream == null) {
                throw new IllegalArgumentException("Datei nicht gefunden: " + fileName);
            }

            List<String> input = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                input.add(line);
            }

            return input;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
