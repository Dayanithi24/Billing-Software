package com.example.maruthielectricals;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Main implements Initializable {
    @FXML
    private Button logout;
    @FXML
    private BorderPane pane;


    public void manageProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Manage_Products.fxml"));
        pane.setCenter(root);
    }

    public void manageCustomer() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Manage_Cust.fxml"));
        pane.setCenter(root);
    }
    public void handler3() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ManageInvoice.fxml"));
        pane.setCenter(root);
    }
    public void handler2() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Invoice.fxml"));
        pane.setCenter(root);
    }
    public void handler1() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        pane.setCenter(root);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            handler1();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logout.setOnAction(event -> {
            Stage currentStage = (Stage) logout.getScene().getWindow();
            currentStage.close();
        });
    }
}
