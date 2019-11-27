package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminHomeController {

    private Main main;

    @FXML
    private Button employee;

    @FXML
    private Button services;

    @FXML
    private Button purchase;

    @FXML
    private Button doctor;

    @FXML
    private Button nurse;

    @FXML
    private Button companies;

    @FXML
    private Button logout;

    @FXML
    private Button profile;

    @FXML
    void profileAction(ActionEvent event) {
        ShowUI.stage.setScene(ShowUI.Admin_Profile);
    }

    @FXML
    void employeeAction(ActionEvent event){

        ShowUI.stage.setScene(ShowUI.AddEmpScene);
    }

    @FXML
    void servicesAction(ActionEvent event){
        ShowUI.stage.setScene(ShowUI.Services);
    }

    @FXML
    void companiesAction(ActionEvent event){
        ShowUI.stage.setScene(ShowUI.Companies);
    }

    @FXML
    void nurseAction(ActionEvent event){
        ShowUI.stage.setScene(ShowUI.AddNurse);
    }

    @FXML
    void doctorAction(ActionEvent event){
        ShowUI.stage.setScene(ShowUI.AddDoct);
    }

    @FXML
    void purchaseAction(ActionEvent event){
        ShowUI.stage.setScene(ShowUI.PurchaseScene);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        ShowUI.stage.setScene(ShowUI.scene1);
    }

    void setMain(Main main) {
        this.main = main;
    }

}
