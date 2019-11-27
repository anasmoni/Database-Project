package HDBMS;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctController {

    private Main main;

    @FXML
    private Button app;

    @FXML
    private Button logout;

    @FXML
    private Button profile;

    @FXML
    private Button presc;

    private Stage primaryStage = new Stage();

    private boolean isAppOpen=false;

    @FXML
    void profileAction(ActionEvent event)
    {
        ShowUI.stage.setScene(ShowUI.DPCscene);
    }

    @FXML
    void appAction(ActionEvent event){

        primaryStage.close();

       ArrayList<DoctApp> Dlist = new ArrayList<>();
        System.out.println(ShowUI.EID);
        String sql = "SELECT APP_DATE , TIME , TYPE FROM APPOINTMENT WHERE EID='" + ShowUI.EID + "' AND APP_DATE>=SYSDATE ORDER BY APP_DATE";

        try
        {

            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Dlist.add(new DoctApp(rs.getString(1), rs.getString(2), rs.getString(3)));
            }


            pst.close();
            con.close();
        } catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }

        ShowDoctApp(Dlist);

    }

    @FXML
    void prescAction(ActionEvent event) {

        ShowUI.stage.setScene(ShowUI.Prescribe);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        primaryStage.close();
        ShowUI.stage.setScene(ShowUI.scene1);
    }

    void setMain(Main main) {
        this.main = main;
    }

    public void ShowDoctApp(ArrayList<DoctApp> Slist){



        TableView<DoctApp> teamTable = new TableView<>();


        ObservableList<DoctApp> teams = FXCollections.observableArrayList(Slist);


        teamTable.setItems(teams);
        teamTable.setMaxSize(800, 500);
        teamTable.setMinSize(800, 500);

        String CoumnNames[] = {"App_Date" , "App_Time", "App_Type"};

        String CoumnPVFNames[] = {"apdate" , "aptime","aptype"};


        for(int i=0; i<CoumnNames.length; i++){
            TableColumn<DoctApp, String> colName = new TableColumn<>(CoumnNames[i]);
            colName.setCellValueFactory(new PropertyValueFactory<DoctApp, String>(CoumnPVFNames[i]));
            colName.setMinWidth(teamTable.getMaxWidth()/CoumnNames.length);
            teamTable.getColumns().add(colName);
        }

        GridPane center = new GridPane();

        center.add(teamTable, 0, 1, 5, 5);

        VBox right = new VBox(0);
        right.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setCenter(center);
        root.setRight(right);

        Scene scene = new Scene(root, 800, 500);

        primaryStage.setTitle("Appointments");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public  class DoctApp{

        private final SimpleStringProperty apdate;
        private final SimpleStringProperty aptime;
        private final SimpleStringProperty aptype;

        public DoctApp(String apdate ,String aptime, String atype) {

            this.apdate = new SimpleStringProperty(apdate);
            this.aptime = new SimpleStringProperty(aptime);
            this.aptype = new SimpleStringProperty(atype);
        }

        public String getApdate() {
            return apdate.get();
        }

        public String getAptime() {
            return aptime.get();
        }

        public String getAptype() {
            return aptype.get();
        }
    }

}
