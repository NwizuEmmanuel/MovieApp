package com.example.moviephlix.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class MediaPlayerController {

    @FXML
    private MediaView mediaView;
    @FXML
    private BorderPane pane;

    MediaPlayer mediaPlayer;
    String path;
    @FXML
    void playAction() {
        mediaPlayer.play();
    }

    @FXML
    void pauseAction() {
        mediaPlayer.pause();
    }

    @FXML
    void stopAction() {
        mediaPlayer.stop();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMediaView() {
        try{
            File file = new File(path);
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.fitHeightProperty().bind(pane.heightProperty());
            mediaView.fitWidthProperty().bind(pane.widthProperty());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopMediaOnClose(){
        mediaPlayer.stop();
    }
}
