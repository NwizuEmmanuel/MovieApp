package com.example.moviephlix.controller;

import com.example.moviephlix.db.Database;
import com.example.moviephlix.functions.MyFileChooser;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditViewController{
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
    private Label movie_id_label;

    @FXML
    void addFileAction() {
        MyFileChooser.show(filepath_input);
    }

    @FXML
    void cancelAction() {
        MainController.getSecondaryStage().close();
    }

    @FXML
    void updateAction() {
        Database database = new Database();
        database.updateData(
                Integer.parseInt(movie_id_label.getText()),
                title_input.getText(),
                director_input.getText(),
                year_input.getValue(),
                runtime_input.getValue(),
                filepath_input.getText()
        );
        MainController.getSecondaryStage().close();
    }

    public void setInputs(int id, String title, String director, int year, int runtime, String filePath){
        movie_id_label.setText(String.valueOf(id));
        title_input.setText(title);
        director_input.setText(director);
        year_input.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,9999, year));
        runtime_input.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,9, runtime));
        filepath_input.setText(filePath);
    }
}
