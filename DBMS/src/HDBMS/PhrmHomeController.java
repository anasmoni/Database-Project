package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PhrmHomeController {

    private Main main;


    @FXML
    private Button pharm;

    @FXML
    private Button logout;

    @FXML
    private Button profile;


    @FXML
    void profileAction(ActionEvent event)
    {
        ShowUI.stage.setScene(ShowUI.PhrmProf);
    }


    @FXML
    void pharmAction(ActionEvent event){

        ShowUI.stage.setScene(ShowUI.Pharmacy);

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
