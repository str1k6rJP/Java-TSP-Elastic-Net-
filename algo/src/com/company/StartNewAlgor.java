package com.company;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.LinkedList;

class StartNewAlgor extends Thread {
    //Drawer.ifInitialized =false;
    Drawer drawer;        // the first one defines the distance from normalizing ring center to side dots

    // while the both last are referred to set the number of dots both to left
    // and to right from the current iteration selected node
    public StartNewAlgor(int nodesPerCity
            , int maxIteration
            , int maxGaussianRadius
            , int screenRefresh
            , double learnRateBooster
            , String path
            , double maxLearnRate
            , boolean ifFromCitiesMassCenter
            , int width
            , int height
            , boolean ifWriteToConsole
            , boolean ifWriteToFile
            , double normCircleRadius
            , int minRadius
            , boolean ifRndCitiesGener
            , String citiesQuantity
            , String higherXBound
            , String higherYBound
    ) {
        drawer = new Drawer(nodesPerCity
        ,maxIteration
        ,maxGaussianRadius
        ,screenRefresh
        ,learnRateBooster
        ,path
        ,maxLearnRate
        ,ifFromCitiesMassCenter
        ,width
        ,height
        ,ifWriteToConsole
        ,ifWriteToFile
        ,normCircleRadius
        ,minRadius
        ,ifRndCitiesGener
        ,citiesQuantity
        , higherXBound
        , higherYBound);


        drawer.createNewBooleanArray();
        drawer.initializeDrawableArrays(drawer.excelFunc.nodes.length);
        for (
                int i = 0;
                i < drawer.excelFunc.nodes.length; i++) {
            drawer.transformNodeIntoDrawableParams(drawer.excelFunc.nodes[i], i);
        }

        System.out.println("Drawing class has been successfully set");
        JFrame jFrame;
        jFrame = new

                JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setIconImage(new ImageIcon(Paths.get("", "initData\\iconAlgor.png").toString()).getImage());
        jFrame.setTitle("Plot");
        jFrame.setSize(drawer.MYWIDTH + Drawer.BORDER, drawer.MYHEIGHT + Drawer.BORDER);

        jFrame.setResizable(false);
        jFrame.setVisible(true);

        jFrame.add(drawer);
        //isRunning = true;
        drawer.ifInitialized = true;

        boolean bExit = false;


    }

    @Override
    public void run() {
        super.run();


        class ExitThread extends Thread {
            @Override
            public void run() {

                super.run();
                /*drawer.excelFunc.executeOnAlgorithmSolution();
                System.out.println("Current iteration is " + drawer.excelFunc.currentIter);
                drawer.excelFunc.executeOnAlgorithmSolution();*/

            }
        }

        ExitThread exitThread = new ExitThread();
        Runtime.getRuntime().

                addShutdownHook(exitThread);
        drawer = null;
    }



    @Override
    public synchronized void start() {
        super.start();

if (drawer.finalRepaintIsDone){
    interrupt();
}
    }


}
