package HDBMS;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PathoTestcontroller {

    private Main main;

    @FXML
    private Button add;

    @FXML
    private Button logout;

    @FXML
    private Button reset;

    @FXML
    private Button back;

    @FXML
    private Button report;

    @FXML
    private TextField pid;

    @FXML
    private TextField result;

    @FXML
    private ComboBox tname;

    private Stage primaryStage = new Stage();

    public  void init(){

        ArrayList<String> ts  = new ArrayList<>();

        String sql = "SELECT DISTINCT NAME FROM TESTS";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                ts.add(rs.getString(1));
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        tname.getItems().addAll(ts);
    }


    @FXML
    void addAction(ActionEvent event)
    {
        if(pid.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Prescription ID...","Oops!!");
            return;
        }

        if(tname.getValue()==null){
            ShowUI.ShowPop("Please Select Test Name......","Oops!!");
            return;
        }

        if(result.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Test Result...","Oops!!");
            return;
        }

        String sql = "UPDATE TEST_HISTORY SET REPORT = '" + result.getText()
                + "' WHERE PRES_ID = " + pid.getText() + " AND TEST_NAME = '" + tname.getValue() + "' AND REPORT IS NULL";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        ShowUI.ShowPop("Succesfully Added.....","WAH!!");

    }


    @FXML
    void resetAction(ActionEvent event){
        resetAll();
    }

    @FXML
    void logoutAction(ActionEvent event)
    {
        resetAll();
        ShowUI.stage.setScene(ShowUI.scene1);
    }

    public void resetAll(){
        pid.setText("");
        result.setText("");
        primaryStage.close();
    }


    void setMain(Main main) {

        init();
        this.main = main;
    }

    @FXML
    void reportAction(ActionEvent event){

        ArrayList<Slip> total = new ArrayList<>();

        String sql = "SELECT * FROM TEST_HISTORY WHERE PRES_ID = " + pid.getText();

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                total.add(new Slip(rs.getString(1),rs.getString(3),rs.getString(4)));
            }
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        ShowSlip(total);
    }

    @FXML
    void backAction(ActionEvent event){
        resetAll();
        ShowUI.stage.setScene(ShowUI.PathoHome);
    }


     public void ShowSlip(ArrayList<Slip> Slist){

        TableView<Slip> teamTable = new TableView<>();


        ObservableList<Slip> teams = FXCollections.observableArrayList(Slist);

        double w=600,h=350;
        teamTable.setItems(teams);
        teamTable.setMaxSize(w, h);
        teamTable.setMinSize(w, h);

        String CoumnNames[] = {"Test Name" , "Date" , "Result"};

        String CoumnPVFNames[] = {"name" , "date" , "res"};


        for(int i=0; i<CoumnNames.length; i++){
            TableColumn<Slip, String> colName = new TableColumn<>(CoumnNames[i]);
            colName.setCellValueFactory(new PropertyValueFactory<Slip, String>(CoumnPVFNames[i]));
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

        Scene scene = new Scene(root, w, h);

        primaryStage.setTitle("Slip for Presc_ID "+ pid.getText());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public  class Slip{

        private final SimpleStringProperty name;
        private final SimpleStringProperty date;
        private final SimpleStringProperty res;

        public Slip(String nm ,String u ,String c ) {

            this.name= new SimpleStringProperty(nm);
            this.date = new SimpleStringProperty(u);
            this.res = new SimpleStringProperty(c);
        }

        public String getName() {
            return name.get();
        }

        public String getDate() {
            return date.get();
        }

        public String getRes() {
            return res.get();
        }
    }


}
