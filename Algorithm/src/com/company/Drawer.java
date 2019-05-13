package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

public class Drawer extends JPanel implements ActionListener
        //, KeyListener
{
    MyFunction excelFunc;
    Timer timer = new Timer((int) (1 * 1000 / 25), this);
    static final int MYWIDTH = 600, MYHEIGHT = 600, BORDER = 120, SCREN_REFRESH_FREQUENCY = 30;
    Random r = new Random();
    double[] iterXArray;
    double[] iterYArray;
    public static final int HALF_OF_CITY_SIZE = 3, HALF_OF_NODE_SIZE = 2;

    {
        excelFunc = new MyFunction(70
               , new File("src\\com\\company\\data")
                //, NodesGenerator.getFileWithNodes(100,100,100)
                , 5000, 15, 30, 1, 0.7);
        excelFunc.actualRadius = excelFunc.maxRadius - excelFunc.minRadius;

        //excelFunc.actualRadius /= 2;

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

    public void transformNodeIntoDrawableParams(Node node, int index) {
        iterXArray[index] = node.getX() * MYWIDTH + BORDER / 2;
        iterYArray[index] = node.getY() * MYHEIGHT + BORDER / 2;
    }



/*
    static boolean  isRunning = false;
    public void changeCondition(){
        isRunning=!isRunning;
    }
*/

    /*@Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == ' ') {
            changeCondition();
            for (int i = 0; i < 100; i++) {
                System.out.println("changed");
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_SPACE) {
            changeCondition();
            for (int i = 0; i < 100; i++) {
                System.out.println("changed");
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }*/

    /*@Override
    public void mouseClicked(MouseEvent e) {
        changeCondition();
    }

    @Override
    public void mousePressed(MouseEvent e){

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }*/


    @Override
    public void paint(Graphics g) {
        super.paint(g);


        g.setColor(Color.DARK_GRAY);
        g.drawLine(BORDER / 2, BORDER / 2, MYWIDTH + BORDER / 2, BORDER / 2);
        g.drawLine(BORDER / 2, BORDER / 2, BORDER / 2, MYHEIGHT + BORDER / 2);
        g.drawLine(BORDER / 2, MYHEIGHT + BORDER / 2, MYWIDTH + BORDER / 2, MYHEIGHT + BORDER / 2);
        g.drawLine(MYWIDTH + BORDER / 2, BORDER / 2, MYWIDTH + BORDER / 2, MYHEIGHT + BORDER / 2);

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

            timer.start();

        }


        //      timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(excelFunc.algorithmCloseCondition())) {
            iteration();
        } else {
            for (int i = 0; i < excelFunc.nodes.length; i++) {
                transformNodeIntoDrawableParams(excelFunc.nodes[i], i);
            }
            System.out.println("Iteration " + excelFunc.currentIter + "; Learning rate is " + excelFunc.scaleK + "; Current nodes ring length is " + excelFunc.computeNormalizedRingLength(excelFunc.nodes) + " ; Actual radius is " + excelFunc.actualRadius);
            repaint();
            timer.stop();
            excelFunc.executeOnAlgorithmSolution();
            while (true) {

            }
        }
        if (excelFunc.currentIter % SCREN_REFRESH_FREQUENCY == 0) {
            for (int i = 0; i < excelFunc.nodes.length; i++) {
                transformNodeIntoDrawableParams(excelFunc.nodes[i], i);
            }
            System.out.println("Iteration " + excelFunc.currentIter + "; Learning rate is " + excelFunc.scaleK + "; Current nodes ring length is " + excelFunc.computeNormalizedRingLength(excelFunc.nodes) + " ; Actual radius is " + excelFunc.actualRadius);
        }
        excelFunc.currentIter++;
        repaint();
    }


    int previous, prePrevious = -1;

    public void iteration() {
        boolean[] ifCityAdapted = new boolean[excelFunc.normalizedCities.length];
        for (int i = 0; i < ifCityAdapted.length; i++) {
            ifCityAdapted[i] = false;
        }

        for (int index = 0; index < excelFunc.normalizedCities.length; index++) {
            int indexOfCity = r.nextInt(excelFunc.normalizedCities.length-index);
            while (ifCityAdapted[indexOfCity]){
                if(indexOfCity++ > excelFunc.normalizedCities.length-1){
                    indexOfCity-=excelFunc.normalizedCities.length;
                }
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

        /*for (int i = 0; i < excelFunc.nodes.length; i++) {
            transformNodeIntoDrawableParams(excelFunc.nodes[i], i);
        }*/
  /*      System.out.println("City " + indexOfCity + " :  " + excelFunc.normalizedCities[indexOfCity].getX() + ";" + excelFunc.normalizedCities[indexOfCity].getY()
                + ";Winnernode " + excelFunc.normalizedCities[indexOfCity].nearestNodeIndex);*/

  ifCityAdapted[indexOfCity] = true;
        }
    }

    public static void main(String[] args) {
        Drawer drawer = new Drawer();

        drawer.initializeDrawableArrays(drawer.excelFunc.nodes.length);
        for (int i = 0; i < drawer.excelFunc.nodes.length; i++) {
            drawer.transformNodeIntoDrawableParams(drawer.excelFunc.nodes[i], i);
        }


        JFrame jFrame;
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jFrame.setTitle("Plot");
        jFrame.setSize(MYWIDTH + BORDER, MYHEIGHT + BORDER);

        jFrame.setResizable(false);
        jFrame.setVisible(true);

        jFrame.add(drawer);
        //isRunning = true;


        class ExitThread extends Thread {
            @Override
            public void run() {
                super.run();
                drawer.excelFunc.executeOnAlgorithmSolution();
            }
        }
        ExitThread exitThread = new ExitThread();
        Runtime.getRuntime().addShutdownHook(exitThread);

    }
}


