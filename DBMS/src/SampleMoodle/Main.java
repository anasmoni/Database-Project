package SampleMoodle;

import SampleMoodle.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        showLoginPage();
    }

    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Loading the controller
       LoginController controller = loader.getController();
       controller.setMain(this);

        // Set the primary stage
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 400, 250));
        stage.show();
    }

//    public void showTable() throws IOException
//    {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("SampleMoodle/tableview.fxml"));
//        Parent root = loader.load();
//
//        // Loading the controller
//        TableViewController controller = loader.getController();
//        controller.load();
//        controller.setMain(this);
//
//        // Set the primary stage
//        stage.setTitle("Dashboard");
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
//


    public static void main(String[] args) {
        launch(args);
    }
}
