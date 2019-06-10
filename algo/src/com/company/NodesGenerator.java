package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class NodesGenerator {
    static Random r = new Random();

    public static File getFileWithNodes(int amount, int highestXValue, int highestYValue) {
        File file = new File("DATA");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < amount; i++) {
                String string = r.nextInt(highestXValue) + " " + r.nextInt(highestYValue);
                writer.write(string);
                writer.newLine();
            }
        } catch (Exception e) {

        }

        return file;
    }
}
