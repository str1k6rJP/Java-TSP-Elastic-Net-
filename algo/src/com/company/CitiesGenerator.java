package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Random;

public class CitiesGenerator {
    static Random r = new Random();

    public static File getFileWithNodes(int amount, int highestXValue, int highestYValue) {
        File file = new File(Paths.get("", "generatedSources\\generSource" + amount + ".tsp").toString());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("NAME:  " + amount);
            writer.newLine();
            for (int i = 0; i < amount; i++) {
                String string = r.nextInt(highestXValue) + " " + r.nextInt(highestYValue);
                writer.write(string);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Smt was wrong");
        }

        return file;
    }
}
