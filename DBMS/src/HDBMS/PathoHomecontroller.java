package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PathoHomecontroller {

    private Main main;

    @FXML
    private Button test;

    @FXML
    private Button logout;

    @FXML
    private Button profile;

    @FXML
    void profileAction(ActionEvent event)
    {
        ShowUI.stage.setScene(ShowUI.PathoProf);
    }

    @FXML
    void testAction(ActionEvent event){

        ShowUI.stage.setScene(ShowUI.PathoTest);
    }

    @FXML
    void logoutAction(ActionEvent event)
    {
        ShowUI.stage.setScene(ShowUI.scene1);
    }

    void setMain(Main main) {

        this.main = main;
    }

}

