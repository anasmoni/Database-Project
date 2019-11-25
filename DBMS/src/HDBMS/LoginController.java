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

public class LoginController
{

    private Main main;

    @FXML
    private Label label;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button reset;

    @FXML
    private Button admin;


    @FXML
    private Button doctor;

    @FXML
    private Button receptionist;

    @FXML
    private Button register;

    void warning(String s){
         label.setText(s);
         label.setOpacity(1.0);
    }

   public boolean check(){

        String sql = "SELECT PASSWORD FROM LOGIN  WHERE USERNAME="+"'"+username.getText()+"'";
        boolean ret=true;
        try
        {

            Connection con = new DBMSConnection().getConnection();
            System.out.println(password.getText());
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                //System.out.println(rs.getString(1));

                if (rs.getString(1).equals(password.getText()) == false) {
                    ret = false;

                }
            }

            pst.close();
            con.close();
        } catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }

        return ret;
    }

    @FXML
    void adminAction(ActionEvent event)
    {
       // System.out.println(password.getText());
//        String userName = userText.getText();
//        String password = passwordText.getText();
//        boolean success = new Users().validateLogin(userName, password);
//        if (success)
//        {
//            // successful login
//            try
//            {
//                main.showTable();
//            } catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//
//        } else
//        {
//            // failed login
//            Alert alert = new Alert(AlertType.ERROR);
//            alert.setTitle("Incorrect Credentials");
//            alert.setHeaderText("Incorrect Credentials");
//            alert.setContentText("The username and password you provided is not correct.");
//            alert.showAndWait();
//        }

    }

    @FXML
    void doctorAction(ActionEvent event){
    }

    @FXML
    void receptionistAction(ActionEvent event){
        //System.out.println(password.getText());

        if(username.getText().isEmpty()==true){
            warning("Please Enter UserName.....");
            return;
        }

        if(password.getText().isEmpty()==true){
            warning("Please Enter Password.....");
            return;
        }

        if(check()==false){

           //System.out.println(password.getText());
            warning("Wrong Password or Username...");

            return;
        }


        ShowUI.stage.setScene(ShowUI.scene2);

    }

    @FXML
    void registerAction(ActionEvent event){

    }


    @FXML
    void resetAction(ActionEvent event) {
        username.setText(null);
        password.setText(null);
        label.setOpacity(0.0);
    }

    void setMain(Main main) {

        this.main = main;



        ShowUI.stage.setScene(ShowUI.scene1);;
        ShowUI.stage.show();


    }

}
