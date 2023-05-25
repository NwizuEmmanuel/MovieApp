package com.example.moviephlix.functions;

import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MyFileChooser {
    static public void show(TextArea textArea){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Media files", "*.mp4", "*.mkv")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null){
            textArea.setText(selectedFile.getPath());
        }
    }
}
