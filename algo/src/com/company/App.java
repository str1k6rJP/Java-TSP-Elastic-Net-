package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.Hashtable;

public class App extends JFrame{
    private JTextField nodesPerCityTextField;
    private JTextField nodesPerCitiesParams;
    private JTextField maxIterParams;
    private JSlider slider1;
    private JSlider slider2;
    private JTextField learRateDividerParams;
    private JTextField filePathParams;
    private JButton selectFileWithDataButton;
    private JButton startButton;
    private JPanel graphDrawer;
    private JTextField screenRefreshRateTextField;
    private JTextField screenRefreshParams;
    private JTextField maxRadiusParams;
    private JTextField maxLearnRateParams;
    private JPanel mainPanel;
    private JComboBox nodesCircleCenter;
    private JRadioButton writeDownIntermediateDataRadioButton;
    private JRadioButton donTWriteRadioButton;
    private JRadioButton writeToFileButton;
    private JRadioButton dontWriteToFileButton;
    private JTextField textField1;
    private JTextField normRadiusParams;
    private JTextField minRadius;
    private JCheckBox generateCitiesRandomlyByCheckBox;
    private JTextField lowerBoundTextField;
    private JTextField citiesQuantityTextField;
    private JTextField higherXBoundParams;
    private JTextField higherYBoundYParams;

    public App() {
        add(mainPanel);

        setTitle("Plot");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
textField1.setText("\u00A9 by str1k6r(Dmytro Maliovanyi)");
textField1.setBorder(null);
setIconImage(new ImageIcon(Paths.get("","initData\\iconMenu.png").toString()).getImage());
        Hashtable labelTable = new Hashtable();
        for (int i = 1; i <= 10; i++) {
            labelTable.put(new Integer(i), new JLabel((double) i / 10 + ""));
        }
        slider2.setLabelTable(labelTable);

        slider2.setPaintLabels(true);

        selectFileWithDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenFile openFile = new OpenFile();
                String string = null;
                try {
                    string = openFile.getFileDirectory();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    string = "Error! No File Was Selected!";
                }

                filePathParams.setText(string);
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Run.isNewAlgorithmStarted = true;
            }
        });

        citiesQuantityTextField.setEditable(false);
        higherXBoundParams.setEditable(false);
        higherYBoundYParams.setEditable(false);

    }

    public void updateParams() {
        learRateDividerParams.setText(slider1.getValue() + "");
        maxLearnRateParams.setText((double)slider2.getValue()/10 + "");
        repaint();
        if (generateCitiesRandomlyByCheckBox.isSelected()){
            citiesQuantityTextField.setEditable(true);
            higherXBoundParams.setEditable(true);
            higherYBoundYParams.setEditable(true);
        } else {
            citiesQuantityTextField.setText("Cities Quantity");
            higherXBoundParams.setText("Higher X Bound");
            higherYBoundYParams.setText("Higher Y Bound");
        }
    }

    public void setGraphDrawer(JPanel graphDrawer) {
        this.graphDrawer = graphDrawer;
        this.graphDrawer.setVisible(true);
        this.graphDrawer.revalidate();
        this.graphDrawer.repaint();
    }
    public boolean getIfRandomCitiesGeneration(){return generateCitiesRandomlyByCheckBox.isSelected();}
    public String getcitiesQuantityParams(){return citiesQuantityTextField.getText();}
    public String getHigherXBoundParams(){return higherXBoundParams.getText();}
    public String getHigherYBoundParams(){return higherYBoundYParams.getText();}

    public int getMinRadius(){return Integer.parseInt(minRadius.getText());}

    public  double getCircleRadius(){return  Double.parseDouble(normRadiusParams.getText());}


    public int getNodesPerCity() {
        return Integer.parseInt(nodesPerCitiesParams.getText());
    }

    public int getMaxIterParams() {
        return Integer.parseInt(maxIterParams.getText());
    }

    public int getMaxRadius() {
        return Integer.parseInt(maxRadiusParams.getText());
    }

    public int getScreenRefreshRate() {
        return Integer.parseInt(screenRefreshParams.getText());
    }

    public double getLearningRateDecreaseSpeed() {
        return Double.parseDouble(learRateDividerParams.getText());
    }

    public double getMaxLearningRate() {
        return Double.parseDouble(maxLearnRateParams.getText());
    }

    public String getPathToFileWithCitiesParams() {
        return filePathParams.getText();
    }

    public boolean ifFromCitiesMassCenter() {
        String tempString = nodesCircleCenter.getSelectedItem().toString();
        if (tempString.equals("Start from cities' mass center")) {
            return true;
        }
        return false;
    }

    public Dimension getDrawerDimension(){
        return graphDrawer.getSize();
    }
    public boolean getIfWriteToFile(){
        return writeToFileButton.isSelected();
    }
    public boolean getIfWriteToConsole(){
        return writeDownIntermediateDataRadioButton.isSelected();
    }
  /*  Drawer drawer;
private boolean ifStart = false;
private boolean ifRunning = false;
    @Override
    public void run() {
        updateParams();
        repaint();
        if (ifStart){
            Drawer.ifInitialized = false;
            drawer = new Drawer(getNodesPerCity()
                    ,getMaxIterParams()
                    ,getMaxRadius()
                    ,getScreenRefreshRate()
                    ,getLearningRateDecreaseSpeed()
                    ,getPathToFileWithCitiesParams()
                    ,getMaxLearningRate()
                    ,ifFromCitiesMassCenter());

            drawer.createNewBooleanArray();
            drawer.initializeDrawableArrays(drawer.excelFunc.nodes.length);
            for (int i = 0; i < drawer.excelFunc.nodes.length; i++) {
                drawer.transformNodeIntoDrawableParams(drawer.excelFunc.nodes[i], i);
            }
            ifStart =false;
            ifRunning = true;
            graphDrawer.add(drawer);
            System.out.println("Execution should start");

        }


    }*/

}
