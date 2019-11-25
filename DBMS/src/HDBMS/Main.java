package HDBMS;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

public class Main extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        showLoginPage();
    }

    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader

        // Loading the controller
        /*stage.setTitle("ABC HOSPITAL");
        ManageNurse MN = new ManageNurse();

        MN.setMain(this);*/


        // Set the primary stage

       /* FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RemoveNurse.fxml"));
        Parent root = loader.load();

        // Loading the controller


        // Set the primary stage
        stage.setTitle("ABC HOSPITAL");
        Scene sc1 = new Scene(root, 800, 425);

        FXMLLoader lor = new FXMLLoader();
        lor.setLocation(getClass().getResource("ShowNurse.fxml"));
        Parent rt = lor.load();

        Scene sc2 = new Scene(rt, 800, 425);

        ShowUI.scene1 = sc1;
        ShowUI.scene2 = sc2;
        ShowUI.stage = stage;ADNA

         ManageNurse MN = new ManageNurse();

        MN.setMain(this);*/


        FXMLLoader loader = new FXMLLoader();
        //System.out.println("balmar");
        loader.setLocation(getClass().getResource("LogInAs.fxml"));

        Parent root = loader.load();

        stage.setTitle("ABC HOSPITAL");
        Scene sc1 = new Scene(root, 800, 425);

        FXMLLoader lor = new FXMLLoader();
        lor.setLocation(getClass().getResource("ReceptionistHome.fxml"));
        Parent rt = lor.load();

        Scene sc2 = new Scene(rt, 800, 425);

       FXMLLoader lr = new FXMLLoader();
        lr.setLocation(getClass().getResource("Appointment.fxml"));
        Parent r = lr.load();

        Scene app = new Scene(r, 800, 425);

        ShowUI.appo = app;
        ShowUI.scene1 = sc1;
        ShowUI.scene2 = sc2;
        ShowUI.stage = stage;

        LoginController LC = new LoginController();
        LC.setMain(this);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
