package com.example.moviephlix.controller;

import com.example.moviephlix.MainApplication;
import com.example.moviephlix.db.Database;
import com.example.moviephlix.model.TableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TableColumn<TableModel, String> director_col;

    @FXML
    private TableColumn<TableModel, Integer> id_col;

    @FXML
    private TableColumn<TableModel, Integer> runtime_col;

    @FXML
    private TableView<TableModel> tableView;

    @FXML
    private TableColumn<TableModel, String> title_col;

    @FXML
    private TableColumn<TableModel, Integer> year_col;
    @FXML
    private TextField searchInput;

    //    items for table data
    final ObservableList<TableModel> items = FXCollections.observableArrayList();

    private static final Stage secondaryStage = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        director_col.setCellValueFactory(new PropertyValueFactory<>("director"));
        year_col.setCellValueFactory(new PropertyValueFactory<>("year"));
        runtime_col.setCellValueFactory(new PropertyValueFactory<>("runtime"));
        setItemsToTable();
    }

    @FXML
    void addAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("add-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            secondaryStage.setTitle("Add movie");
            secondaryStage.setResizable(false);
            secondaryStage.sizeToScene();
            secondaryStage.setScene(scene);
            secondaryStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setItemsToTable();
    }

    @FXML
    void searchAction() {
        String input = searchInput.getText();
        if (input.matches("-?\\d+(\\.\\d+)?")) {
            searchById(Integer.parseInt(input));
        } else {
            searchByTitlePrefix(input);
        }
    }

    @FXML
    void deleteAction() {
        Database database = new Database();
        database.deleteData(getSelectedId());
        setItemsToTable();
    }

    @FXML
    void refreshAction() {
        setItemsToTable();
    }

    @FXML
    void updateAction() {
        SelectionModel<TableModel> selectionModel = tableView.getSelectionModel();
        TableModel tableModel = selectionModel.getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("edit-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            EditViewController editViewController = fxmlLoader.getController();
            editViewController.setInputs(tableModel.getId(),
                    tableModel.getTitle(),
                    tableModel.getDirector(),
                    tableModel.getYear(),
                    tableModel.getRuntime(),
                    tableModel.getFilePath());

            secondaryStage.setTitle("Update movie");
            secondaryStage.setResizable(false);
            secondaryStage.sizeToScene();
            secondaryStage.setScene(scene);
            secondaryStage.showAndWait();
            setItemsToTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getSelectedId() {
        SelectionModel<TableModel> selectionModel = tableView.getSelectionModel();
        TableModel tableModel = selectionModel.getSelectedItem();
        return tableModel.getId();
    }

    private String getFilePath() {
        SelectionModel<TableModel> selectionModel = tableView.getSelectionModel();
        TableModel tableModel = selectionModel.getSelectedItem();
        return tableModel.getFilePath();
    }

    private void setItemsToTable() {
        Database database = new Database();
        items.clear();
        for (String[] item : database.queryData()) {
            int id = Integer.parseInt(item[0]);
            String title = item[1];
            String director = item[2];
            int year = Integer.parseInt(item[3]);
            int runtime = Integer.parseInt(item[4]);
            String filePath = item[5];
            items.add(new TableModel(id, title, director, year, runtime, filePath));
        }
        tableView.setItems(items);
    }

    private void searchById(int _id) {
        items.clear();
        Database database = new Database();
        String[] searchedItems = database.idSearch(_id);
        int id = Integer.parseInt(searchedItems[0]);
        String title = searchedItems[1];
        String director = searchedItems[2];
        int year = Integer.parseInt(searchedItems[3]);
        int runtime = Integer.parseInt(searchedItems[4]);
        String filePath = searchedItems[5];
        items.add(new TableModel(id, title, director, year, runtime, filePath));
        tableView.setItems(items);
    }

    private void searchByTitlePrefix(String prefix) {
        items.clear();
        Database database = new Database();
        for (String[] item : database.titleSearch(prefix)) {
            int id = Integer.parseInt(item[0]);
            String title = item[1];
            String director = item[2];
            int year = Integer.parseInt(item[3]);
            int runtime = Integer.parseInt(item[4]);
            String filePath = item[5];
            items.add(new TableModel(id, title, director, year, runtime, filePath));
        }
        tableView.setItems(items);
    }

    public static Stage getSecondaryStage() {
        return secondaryStage;
    }

    public void playFileAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("media-player-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            MediaPlayerController mediaPlayerController = fxmlLoader.getController();
            mediaPlayerController.setPath(getFilePath());
            mediaPlayerController.setMediaView();
            secondaryStage.setTitle("Media Player");
            secondaryStage.sizeToScene();
            secondaryStage.setScene(scene);
            secondaryStage.setOnCloseRequest(e -> mediaPlayerController.stopMediaOnClose());
            secondaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}