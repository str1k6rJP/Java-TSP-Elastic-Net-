package com.company;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class Run extends Thread {
    public static String path;
    public static boolean ifStart = false;
    static App app = new App();


    class StartNewAlgor extends Thread {
        //Drawer.ifInitialized =false;
        Drawer drawer = new Drawer(app.getNodesPerCity()
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
                , app.getIfWriteToFile());

        @Override
        public void run() {
            super.run();
            drawer.createNewBooleanArray();
            drawer.initializeDrawableArrays(drawer.excelFunc.nodes.length);
            for(
                    int i = 0;
                    i<drawer.excelFunc.nodes.length;i++)

            {
                drawer.transformNodeIntoDrawableParams(drawer.excelFunc.nodes[i], i);
            }

            app.setGraphDrawer(drawer);
            System.out.println("Drawing class has been successfully set");
            JFrame jFrame;
            jFrame =new

                    JFrame();
            jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
jFrame.setIconImage(new ImageIcon(Paths.get("","initData\\iconAlgor.png").toString()).getImage());
            jFrame.setTitle("Plot");
            jFrame.setSize(drawer.MYWIDTH +Drawer.BORDER,drawer.MYHEIGHT +Drawer.BORDER);

            jFrame.setResizable(false);
            jFrame.setVisible(true);

            jFrame.add(drawer);
            //isRunning = true;
            drawer.ifInitialized =true;
            ifStart =false;
            boolean bExit = false;

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
        }
    }

    @Override
    public synchronized void start() {
        super.start();
        LinkedList<StartNewAlgor> algors = new LinkedList<>();
        int waitCounter = 0;
        while (!ifStart) {
            app.updateParams();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Ouch.. Exception");
            }
        }
        StartNewAlgor temp = new StartNewAlgor();
        temp.start();
        algors.add(temp);
        algors.stream().forEach(s -> {
           if (!s.isAlive()){
               algors.remove(s);
           }

        }
        );

        System.out.println("All data has been gotten");
        ifStart = false;
    }

    public static void main(String[] args) {
Run run = new Run();



        app.setVisible(true);

run.start();
}

}
