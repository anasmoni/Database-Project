package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RcpController
{
    private Main main;

    @FXML
    private Button patient;

    @FXML
    private Button app;

    @FXML
    private Button logout;

    @FXML
    private Button profile;



    @FXML
    void profileAction(ActionEvent event)
    {

    }

    @FXML
    void patientAction(ActionEvent event){
    }


    @FXML
    void appAction(ActionEvent event){

        ShowUI.stage.setScene(ShowUI.appo);

    }


    @FXML
    void logoutAction(ActionEvent event) {
        ShowUI.stage.setScene(ShowUI.scene1);
    }



}
