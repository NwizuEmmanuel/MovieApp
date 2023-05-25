package com.example.moviephlix.controller;

import com.example.moviephlix.db.Database;
import com.example.moviephlix.functions.MyFileChooser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddViewController implements Initializable {
    @FXML
    private TextField director_input;

    @FXML
    private TextArea filepath_input;

    @FXML
    private Spinner<Integer> runtime_input;

    @FXML
    private TextField title_input;

    @FXML
    private Spinner<Integer> year_input;

    @FXML
    void addFileAction() {
        MyFileChooser.show(filepath_input);
    }

    @FXML
    void cancelAction() {
        MainController.getSecondaryStage().close();
    }

    @FXML
    void saveAction() {
        String title = title_input.getText();
        String director = director_input.getText();
        Integer year = year_input.getValue();
        Integer runtime = runtime_input.getValue();
        String filePath = filepath_input.getText();

        Database database = new Database();
        database.insertData(title, director, year, runtime, filePath);
        MainController.getSecondaryStage().close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        year_input.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,9999, 1800));
        runtime_input.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,9, 1));
    }
}
