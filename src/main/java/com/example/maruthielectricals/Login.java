package com.example.maruthielectricals;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Login {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;

    public void userLogin(ActionEvent event)throws IOException {
        checklogin();
    }
    private void checklogin()throws IOException {
        String name=username.getText();
        String pass=password.getText();
        if(name.equals("maruthi") && pass.equals("123")){
            try{
                HelloApplication m=new HelloApplication();
                m.changeScene("main.fxml","IntroPage");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
