package SampleMoodle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

public class LoginController
{

    private Main main;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;

    @FXML
    void loginAction(ActionEvent event)
    {
        System.out.println(passwordText.getText());
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
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    void setMain(Main main) {
        this.main = main;
    }

}
