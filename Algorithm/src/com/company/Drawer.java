package com.company;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class Drawer extends JPanel implements ActionListener
        //, KeyListener
{
    long timeOfEnd = 0, timeOfStart = System.nanoTime(), tmeOfExecution = 0;
    public boolean ifInitialized = false;
    MyFunction excelFunc;
    File file;
    BufferedWriter fileWriter;


    public void setWriters(boolean writerInitCondition) {
        if (writerInitCondition) {
            try {
                File directory = new File(Paths.get("", "output").toString());
                File[] filesInside = directory.listFiles();
                int currentFileIndex = 0;
                int prevIndex = -1;
                try {
                    String[] pathParts = filesInside[filesInside.length - 1].toString().split("\\\\");
                    String[] prevFileNameParts = pathParts[pathParts.length - 1].split("\\.");
                    String prevFileIndex = prevFileNameParts[0].charAt(prevFileNameParts[0].length() - 1) + "";
                    prevIndex = Integer.parseInt(prevFileIndex);
                    currentFileIndex = 1 + prevIndex;
                } catch (Exception e) {

                }

                file = new File(Paths.get("", "output\\writtenData" + currentFileIndex + ".txt").toAbsolutePath().toString());
                System.out.println(file.exists());
                System.out.println(file.createNewFile());
                System.out.println(file.exists());
                file.setWritable(true);
                file.setExecutable(true);
                //   file = new File(Run.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()+"\\writtenData.txt");
                fileWriter = new BufferedWriter(new FileWriter(file, true));

            } catch (IOException e) {

            }
        }
    }

    Timer timer;
    public boolean ifWriteToConsole = false, ifWriteToFile = false;
    int screenRefreshFrequency = 0;
    final int MYWIDTH, MYHEIGHT;
    static final int BORDER = 60, SCREN_REFRESH_FREQUENCY = 1;
    Random r = new Random();
    double[] iterXArray;
    double[] iterYArray;
    public static final int HALF_OF_CITY_SIZE = 3, HALF_OF_NODE_SIZE = 2;
    public boolean doneOnExiting = false;

    public Drawer(int nodesPerCity
            , int maxIteration
            , int maxRadius
            , int ScreenRefreshRate
            , double boosterForLearningRate
            , String fileWithCitiesPath
            , double learningRateMax
            , boolean ifStartFromCitiesMassCenter
            , int width, int height
            , boolean ifWriteToConsole
            , boolean ifWriteToFile
            , double circleRadius
            , int minRadius
            , boolean ifRandomCitiesGeneration
            , String citiesQuantity
            , String higherXBound
            , String higherYBound) {
        this.ifWriteToConsole = ifWriteToConsole;
        this.ifWriteToFile = ifWriteToFile;
        setWriters(ifWriteToFile);
        MYWIDTH = width;
        MYHEIGHT = height;
        if (ifRandomCitiesGeneration) {
            excelFunc = new MyFunction(nodesPerCity
                    , maxIteration
                    , maxRadius
                    , boosterForLearningRate
                    , learningRateMax
                    , ifStartFromCitiesMassCenter
                    , circleRadius
                    , minRadius
                    , Integer.parseInt(citiesQuantity)
                    , Integer.parseInt(higherXBound)
                    , Integer.parseInt(higherYBound));
        } else {
            excelFunc = new MyFunction(nodesPerCity
                    , maxIteration
                    , maxRadius
                    , boosterForLearningRate
                    , fileWithCitiesPath
                    , learningRateMax
                    , ifStartFromCitiesMassCenter
                    , circleRadius
                    , minRadius);
        }
        screenRefreshFrequency = ScreenRefreshRate;
        timer = new Timer((int) (10000 / screenRefreshFrequency), this);

        iterXArray = new double[excelFunc.nodes.length];

        iterYArray = new double[excelFunc.nodes.length];
        for (int i = 0; i < excelFunc.nodes.length; i++) {
            transformNodeIntoDrawableParams(excelFunc.nodes[i], i);
        }
    }

    public void initializeDrawableArrays(int quantityOfNodes) {
        iterYArray = new double[quantityOfNodes];
        iterXArray = new double[quantityOfNodes];
    }

    public void transformNodeIntoDrawableParams(Pointable node, int index) {
        iterXArray[index] = node.getX() * MYWIDTH + BORDER / 2;
        iterYArray[index] = node.getY() * MYHEIGHT + BORDER / 2;
    }


    public int getMYWIDTH() {
        return MYWIDTH;
    }

    public int getMYHEIGHT() {
        return MYHEIGHT;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        g.setColor(Color.DARK_GRAY);
        g.drawLine(BORDER / 2 - 3, BORDER / 2 - 3, MYWIDTH + BORDER / 2 + 3, BORDER / 2 - 3);
        g.drawLine(BORDER / 2 - 3, BORDER / 2 - 3, BORDER / 2 - 3, MYHEIGHT + BORDER / 2 + 3);
        g.drawLine(BORDER / 2 - 3, MYHEIGHT + BORDER / 2 + 3, MYWIDTH + BORDER / 2 + 3, MYHEIGHT + BORDER / 2 + 3);
        g.drawLine(MYWIDTH + BORDER / 2 + 3, BORDER / 2 - 3, MYWIDTH + BORDER / 2 + 3, MYHEIGHT + BORDER / 2 + 3);

        g.drawLine(BORDER / 2, BORDER / 2 + MYHEIGHT / 2, MYWIDTH + BORDER / 2, BORDER / 2 + MYHEIGHT / 2);
        g.drawLine(BORDER / 2 + MYWIDTH / 2, BORDER / 2, BORDER / 2 + MYWIDTH / 2, BORDER / 2 + MYHEIGHT);

        g.setColor(Color.RED);
        for (City c : excelFunc.normalizedCities) {
            g.fillOval((int) (c.getX() * MYWIDTH + BORDER / 2 - HALF_OF_CITY_SIZE), (int) (c.getY() * MYHEIGHT + BORDER / 2 - HALF_OF_CITY_SIZE)
                    , 2 * HALF_OF_CITY_SIZE, 2 * HALF_OF_CITY_SIZE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (excelFunc.currentIter != 0) {

            g.setColor(Color.BLUE);
            for (int i = 1; i < iterXArray.length; i++) {
                g.drawLine((int) iterXArray[i - 1], (int) iterYArray[i - 1], (int) iterXArray[i], (int) iterYArray[i]);
            }
            g.drawLine((int) iterXArray[iterXArray.length - 1], (int) iterYArray[iterYArray.length - 1], (int) iterXArray[0], (int) iterYArray[0]);

            g.setColor(Color.BLACK);
            for (int i = 0; i < iterXArray.length; i++) {
                g.fillOval((int) iterXArray[i] - HALF_OF_NODE_SIZE, (int) iterYArray[i] - HALF_OF_NODE_SIZE
                        , 2 * HALF_OF_NODE_SIZE, 2 * HALF_OF_NODE_SIZE);
            }

            g.setColor(Color.BLACK);
            g.drawString("Iteration: " + excelFunc.currentIter, 160, 15);
            g.drawString("Radius: " + excelFunc.actualRadius, 160, 25);
            g.drawString("Current Learning rate: " + excelFunc.scaleK, 280, 20);

            if (isFinal) {
                g.drawString("FINISH", 20, 20);
                g.setColor(Color.MAGENTA);
                for (int i = 1; i < excelFunc.normalizedCities.length; i++) {
                    g.drawLine((int) (excelFunc.normalizedCities[i - 1].getX() * MYWIDTH + BORDER / 2)
                            , (int) (excelFunc.normalizedCities[i - 1].getY() * MYHEIGHT + BORDER / 2)
                            , (int) (excelFunc.normalizedCities[i].getX() * MYWIDTH + BORDER / 2)
                            , (int) (excelFunc.normalizedCities[i].getY() * MYHEIGHT + BORDER / 2));
                }
                g.drawLine((int) (excelFunc.normalizedCities[0].getX() * MYWIDTH + BORDER / 2)
                        , (int) (excelFunc.normalizedCities[0].getY() * MYHEIGHT + BORDER / 2)
                        , (int) (excelFunc.normalizedCities[excelFunc.normalizedCities.length - 1].getX() * MYWIDTH + BORDER / 2)
                        , (int) (excelFunc.normalizedCities[excelFunc.normalizedCities.length - 1].getY() * MYHEIGHT + BORDER / 2));
                finalRepaintIsDone = true;
            }
            timer.start();

        }


        //      timer.start();
    }

    public boolean isFinal = false;
    public int temporalIterationCounter = 0;
    public boolean finalRepaintIsDone = false;
    boolean isFinalizationNodesRingDraw = false, isFinalizationAbsolutePathDraw = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!finalRepaintIsDone) {
            if (excelFunc.currentIter % SCREN_REFRESH_FREQUENCY == 0) {
                for (int i = 0; i < excelFunc.nodes.length; i++) {
                    transformNodeIntoDrawableParams(excelFunc.nodes[i], i);
                }
                if (ifWriteToConsole) {
                    System.out.println("Iteration " + excelFunc.currentIter + "; Learning rate is " + excelFunc.scaleK
                            + "; Current nodes ring length is " + excelFunc.computeNormalizedRingLength(excelFunc.nodes)
                            + " ; Actual radius is " + excelFunc.actualRadius);
                }
                if (ifWriteToFile) {
                    if (ifWriteToFile) {
                        /*try {

                            fileWriter.write("Iteration " + excelFunc.currentIter + "; Learning rate is " + excelFunc.scaleK
                                    + "; Current nodes ring length is " + excelFunc.computeNormalizedRingLength(excelFunc.nodes)
                                    + " ; Actual radius is " + excelFunc.actualRadius + "\n");
                            fileWriter.newLine();
                        } catch (IOException ex) {
                            try {
                                fileWriter.write("IOException\n");
                            } catch (IOException exception) {
                                System.out.println("IOException");
                            }
                        }*/
                    }
                }
            }


            if (!(excelFunc.algorithmCloseCondition())) {
                iteration();
            } else {
                isFinal = true;
                for (int i = 0; i < excelFunc.nodes.length; i++) {
                    transformNodeIntoDrawableParams(excelFunc.nodes[i], i);
                }
                if (!doneOnExiting) {
                    FinalDialog dialog = new FinalDialog();

//final data initialization
                    Pair<Double, String> finalData = excelFunc.executeOnAlgorithmSolution();
                    timeOfEnd = System.nanoTime();
                    tmeOfExecution = timeOfEnd - timeOfStart;


                    dialog.setTitle("RESULTS");

                    dialog.addTextAreaToTextPanel(("Iteration " + excelFunc.currentIter + ";\n"));
                    dialog.addTextAreaToTextPanel(("Learning rate is " + excelFunc.scaleK + ";\n"));
                    dialog.addTextAreaToTextPanel(("Current nodes ring length is "
                            + excelFunc.computeNormalizedRingLength(excelFunc.nodes) + ";\n"));
                    dialog.addTextAreaToTextPanel((" Actual radius is " + excelFunc.actualRadius + ";\n"));

                    dialog.addTextAreaToTextPanel(("Normalized ring length equals to "
                            + finalData.getKey() + "\n"));
                    dialog.addTextAreaToTextPanel(("Real distance is " + finalData.getValue() + "\n"));


                    dialog.addTextAreaToTextPanel("Time of algorithm execution is " + (double) tmeOfExecution / 1000000000 + "\n");

                    dialog.pack();
                    dialog.setVisible(true);

                    if (ifWriteToConsole) {
                        System.out.println("Iteration " + excelFunc.currentIter + "; Learning rate is " + excelFunc.scaleK
                                + "; Current nodes ring length is " + excelFunc.computeNormalizedRingLength(excelFunc.nodes)
                                + " ; Actual radius is " + excelFunc.actualRadius);
                        System.out.println((("Normalized ring length equals to "
                                + finalData.getKey() + "\n")));
                        System.out.println(("Real distance is " + finalData.getValue() + "\n"));


                        System.out.println("Time of algorithm execution is " + (double) tmeOfExecution / 1000000000 + "\n");

                    }
                    if (ifWriteToFile) {
                        try {
                            String temp = "Iteration " + excelFunc.currentIter + "; Learning rate is " + excelFunc.scaleK
                                    + "; Current nodes ring length is " + excelFunc.computeNormalizedRingLength(excelFunc.nodes)
                                    + " ; Actual radius is " + excelFunc.actualRadius + "\r\n";
                            fileWriter.write(temp);
                            temp = "Normalized ring length equals to "
                                    + finalData.getKey() + "\r\n";
                            fileWriter.write(temp);
                            temp = "Real distance is " + finalData.getValue() + "\r\n";
                            fileWriter.write(temp);
                            temp = "Time of algorithm execution is " + (double) tmeOfExecution / 1000000000 + "\r\n";
                            fileWriter.write(temp);

                            fileWriter.close();
                        } catch (IOException ex) {
                            try {
                                fileWriter.write("IOException" + "\r\n");
                            } catch (IOException exception) {
                                System.out.println("IOException");
                            }
                        }
                    }
                    isFinalizationAbsolutePathDraw = isFinalizationNodesRingDraw = true;

                    JFrame nodFrame = new JFrame("Nodes normalizing ring finalization");
                    NodesFinalizing nodJpanel = new NodesFinalizing(excelFunc.normalizedCities, MYWIDTH, MYHEIGHT, iterXArray, iterYArray);
                    nodFrame.setSize(MYWIDTH + BORDER, MYHEIGHT + BORDER);
                    nodFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    nodFrame.setResizable(false);

                    JFrame cityFrame = new JFrame("Cities absolute path ring finalization");
                    AbsoluteFinalizer cityJPanel = new AbsoluteFinalizer(excelFunc.normalizedCities, MYWIDTH, MYHEIGHT);
                    cityFrame.setSize(MYWIDTH + BORDER, MYHEIGHT + BORDER);
                    cityFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    cityFrame.setResizable(false);

                    cityFrame.add(cityJPanel);
                    nodFrame.add(nodJpanel);

                    try {
                        cityFrame.setIconImage(new ImageIcon(Paths.get("", "initData\\iconAbsolutePlot.png").toString()).getImage());
                    } catch (Exception ex) {
                    }
                    try {
                        nodFrame.setIconImage(new ImageIcon(Paths.get("", "initData\\iconNodePlot.png").toString()).getImage());
                    } catch (Exception ex) {
                    }

                    cityFrame.setVisible(true);
                    nodFrame.setVisible(true);
                    doneOnExiting = true;
                }
            }

            if (temporalIterationCounter < excelFunc.normalizedCities.length - 1) {
                temporalIterationCounter++;
            } else {
                temporalIterationCounter = 0;
                ifCityAdapted = new boolean[excelFunc.normalizedCities.length];
                for (int i = 0; i < ifCityAdapted.length; i++) {
                    ifCityAdapted[i] = false;
                }
            }

            repaint();
        } else {
            timer.stop();
        }
    }


    int previous, prePrevious = -1;
    boolean[] ifCityAdapted;

    public void createNewBooleanArray() {
        ifCityAdapted = new boolean[excelFunc.normalizedCities.length];
        for (int i = 0; i < ifCityAdapted.length; i++) {
            ifCityAdapted[i] = false;
        }
    }

    public void iteration() {
        int indexOfCity = 0;
        //for (int index = 0; index < excelFunc.normalizedCities.length; index++) {


        // int indexOfCity = r.nextInt(excelFunc.normalizedCities.length-temporalIterationCounter);
//        int indexOfCity = temporalIterationCounter;
        indexOfCity = r.nextInt(excelFunc.normalizedCities.length - temporalIterationCounter);
        while (ifCityAdapted[indexOfCity]) {
            indexOfCity++;
        }


 /*       boolean ifNearestIsImmutable;
        do {
            indexOfCity = r.nextInt(excelFunc.normalizedCities.length);
            ifNearestIsImmutable = (excelFunc.isImmutable[excelFunc.normalizedCities[indexOfCity].nearestNodeIndex]);
        } while ((indexOfCity == prePrevious) && (indexOfCity == prePrevious)&&(ifNearestIsImmutable));
        prePrevious = previous;
        previous = indexOfCity;*/

        excelFunc.getBestNodeIndex(indexOfCity);
        excelFunc.adaptWinnerNode(indexOfCity, excelFunc.normalizedCities);


        excelFunc.adaptLearningParams();

        excelFunc.getBestNodeIndex(indexOfCity);
        /*for (int i = 0; i < excelFunc.nodes.length; i++) {
            transformNodeIntoDrawableParams(excelFunc.nodes[i], i);
        }*/

        /*
        System.out.println("City " + indexOfCity + " :  " + excelFunc.normalizedCities[indexOfCity].getX() + ";" + excelFunc.normalizedCities[indexOfCity].getY()
                + ";Winnernode " + excelFunc.normalizedCities[indexOfCity].nearestNodeIndex);
*/
        ifCityAdapted[indexOfCity] = true;
        excelFunc.currentIter++;
        //      }
    }

}


