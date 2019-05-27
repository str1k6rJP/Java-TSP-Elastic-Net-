package com.company;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;

public class OpenFile {

    JFileChooser fileChooser = new JFileChooser(Paths.get("","sources").toString());


    public String getFileDirectory() throws Exception{
        String directory = null;
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){

            directory = fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            directory = "No File Was Selected!!";
        }


        return directory;
    }
}
