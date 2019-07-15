package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class CitiesGenerator {
    static Random r = new Random();

    public static File getFileWithNodes(int amount, int highestXValue, int highestYValue) {
        File file;
        file = new File(Paths.get("", "generatedSources\\generSource" + amount + ".tsp").toAbsolutePath().toString());

        try {
            System.out.println(file.createNewFile());

        } catch (IOException e) {
            System.out.println("IOException");
        }
        System.out.println(file.getAbsolutePath().toString());
        //file = new File()
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("NAME:  " + amount);
            writer.newLine();
            for (int i = 0; i < amount; i++) {
                String string = (i+1) + " " + r.nextInt(highestXValue) + " " + r.nextInt(highestYValue);
                writer.write(string);
                writer.newLine();
            }
            writer.write("EOF");
        } catch (Exception e) {
            System.out.println("Smt was wrong");
        }

        return file;
    }
}
