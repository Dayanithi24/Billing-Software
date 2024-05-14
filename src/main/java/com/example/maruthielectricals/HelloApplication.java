package com.example.maruthielectricals;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;


public class HelloApplication extends Application {
    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        stg=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("IntroPage");
        stage.setScene(scene);
        stage.show();
    }

    public void changeScene(String fxml,String s)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Parent pane = fxmlLoader.load();
        stg.setMaximized(true);
        stg.getScene().setRoot(pane);
        stg.setTitle(s);
    }
    public static void main(String[] args) throws FileNotFoundException, MalformedURLException {
        launch();
    }
}