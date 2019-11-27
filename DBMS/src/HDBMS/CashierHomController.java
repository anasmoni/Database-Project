package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CashierHomController {

    private Main main;

    @FXML
    private Button bill;

    @FXML
    private Button logout;

    @FXML
    private Button profile;

    @FXML
    private TextField pid;


    @FXML
    void profileAction(ActionEvent event)
    {
        ShowUI.stage.setScene(ShowUI.CashProf);
    }


    @FXML
    void billAction(ActionEvent event){
        ShowUI.stage.setScene(ShowUI.CashBill);
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
