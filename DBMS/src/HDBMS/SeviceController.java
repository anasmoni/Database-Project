package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SeviceController {

    private Main main;

    @FXML
    private Button room;

    @FXML
    private Button test;

    @FXML
    private Button op;


    @FXML
    private Button logout;

    @FXML
    private Button back;

    @FXML
    void roomAction(ActionEvent event) {
        ShowUI.stage.setScene(ShowUI.Rooms);
    }

    @FXML
    void testAction(ActionEvent event){

        ShowUI.stage.setScene(ShowUI.Tests);
    }

    @FXML
    void opAction(ActionEvent event){
        ShowUI.stage.setScene(ShowUI.Operations);
    }

    @FXML
    void backAction(ActionEvent event)
    {
        ShowUI.stage.setScene(ShowUI.AdminHomeScene);
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
