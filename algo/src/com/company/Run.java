package com.company;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class Run extends Thread {
    public static boolean isNewAlgorithmStarted = false;
    static App app = new App();


    public static void main(String[] args) {
        app.pack();
app.setSize(950,450);
        app.setVisible(true);


        while (true) {
            app.updateParams();

            if (isNewAlgorithmStarted) {
                StartNewAlgor startNewAlgor = new StartNewAlgor(app.getNodesPerCity()
                        , app.getMaxIterParams()
                        , app.getMaxRadius()
                        , app.getScreenRefreshRate()
                        , app.getLearningRateDecreaseSpeed()
                        , app.getPathToFileWithCitiesParams()
                        , app.getMaxLearningRate()
                        , app.ifFromCitiesMassCenter()
                        // , app.getDrawerDimension().width
                        // , app.getDrawerDimension().height
                        , 700
                        , 700
                        , app.getIfWriteToConsole()
                        , app.getIfWriteToFile()
                        , app.getCircleRadius()     // Don't mix up circleRadius variable with maxRadius and minRadius:
                        , app.getMinRadius()
                        , app.getIfRandomCitiesGeneration()
                        , app.getcitiesQuantityParams()
                        , app.getHigherXBoundParams()
                        , app.getHigherYBoundParams());
                startNewAlgor.start();
                isNewAlgorithmStarted = false;
            }

        }


    }
}
