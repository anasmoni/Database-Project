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

public class RoomController {

    private Main main;

    @FXML
    private Button edit;

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Button assign;

    @FXML
    private Button view;

    @FXML
    private Button back;

    @FXML
    private Button logout;

    @FXML
    private TextField room;

    @FXML
    private TextField eid;

    @FXML
    private TextField beds;

    @FXML
    private TextField cost;

    @FXML
    private ComboBox type;

    @FXML
    private ComboBox dept;

    private Stage primaryStage;

    public  void init(){

        primaryStage = new Stage();

        ArrayList<String> depts  = new ArrayList<>();

        String sql = "SELECT DISTINCT DEPT FROM ROOMS ORDER BY DEPT";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                depts.add(rs.getString(1));
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        dept.getItems().addAll(depts);

        type.getItems().addAll(
                "CABIN",
                "DOCTORS_ROOM",
                "EMERGENCY",
                "ICU",
                "INVENTORY",
                "LAB",
                "MEDICINE_STORE",
                "OT",
                "WARD"
        );

    }


    @FXML
    void editAction(ActionEvent event) {

        if(room.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Room ID ","Oops!!");
            return;
        }

        String rid = room.getText() , sql="";
        boolean yup=false;

        if(cost.getText().isEmpty()==false){
            sql = "UPDATE ROOMS SET COST = " + cost.getText();
            yup=true;
        }

        if(type.getValue()!=null){
            if(yup) sql += ", TYPE = '" + type.getValue() + "'";
            else sql = "UPDATE ROOMS SET  TYPE = '" + type.getValue() + "'";
            yup=true;
        }

        if(dept.getValue()!=null){
            if(yup) sql += ", DEPT = '" + dept.getValue() + "'";
            else sql = "UPDATE ROOMS SET  DEPT = '" + dept.getValue() + "'";
            yup=true;
        }



        if(yup){

            sql += " WHERE RID = " + rid;

            try
            {
                Connection con = new DBMSConnection().getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                pst.close();
                con.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }

            ShowUI.ShowPop("Succesfully Updated.....","WAH!!");
        }
    }

    @FXML
    void addAction(ActionEvent event){

        if(room.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Room ID ","Oops!!");
            return;
        }

        if(type.getValue()==null){
            ShowUI.ShowPop("Please Selete Room Type ","Oops!!");
            return;
        }

        String tp = (String)type.getValue(), sql="";

        if(dept.getValue()==null){
            ShowUI.ShowPop("Please Selete Department ","Oops!!");
            return;
        }

        if(tp.equals("WARD") || tp.equals("CABIN") || tp.equals("ICU") || tp.equals("EMERGENCY")) {

            if (cost.getText().isEmpty()) {
                ShowUI.ShowPop("Please Enter Room Cost ", "Oops!!");
                return;
            }

            if (beds.getText().isEmpty() && tp.equals("WARD")) {
                ShowUI.ShowPop("Please Enter Beds ", "Oops!!");
                return;
            }

            if(tp.equals("WARD") == false) {
                sql = "INSERT INTO ROOMS (RID,COST,TYPE,DEPT) VALUES('" + room.getText() + "',null,'" + type.getValue()
                        + "','" + dept.getValue() + "')";
            }
            else{
                sql = "INSERT INTO ROOMS (RID,COST,TYPE,DEPT,BEDS) VALUES('" + room.getText() + "',null,'" + type.getValue()
                        + "','" + dept.getValue() + "'," + beds.getText() + ")";
            }
        }

        else{
            sql = "INSERT INTO ROOMS (RID,COST,TYPE,DEPT) VALUES('" + room.getText() + "','" + cost.getText() + "','" + type.getValue()
                    + "','" + dept.getValue() + "')";
        }


        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        ShowUI.ShowPop("Succesfully Added....","Wah!!\n");
    }

    @FXML
    void deleteAction(ActionEvent event){
        if(room.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Room ID ","Oops!!");
            return;
        }
/*
        String sql = "DELETE FROM EMPLOYEE_ASSIGN WHERE RID = "+room.getText();

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        sql = "DELETE FROM ROOMS WHERE RID = " + room.getText();

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        */

        String sql = "BEGIN\n" +
                "    DELETE_ROOMS(" +room.getText() + ");\n" +
                "END;";

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


        ShowUI.ShowPop("Succesfully Deleted....","Wah!!\n");
    }

    @FXML
    void viewAction(ActionEvent event) {
        if(room.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Room ID ","Oops!!");
            return;
        }

        String sql = "SELECT EA.EID, E.PROFESSION,R.TYPE,R.COST FROM EMPLOYEE_ASSIGN EA JOIN EMPLOYEE E ON EA.EID = E.EID ," +
                " ROOMS R WHERE EA.RID = R.RID AND R.RID="+room.getText();

        ArrayList<Employ>Emp = new ArrayList<>();

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            boolean hoise=true;
            while (rs.next()){
                Emp.add(new Employ(rs.getString(1),rs.getString(2),rs.getString(3)));
                if(hoise){
                    hoise=false;
                    cost.setText(rs.getString(4));
                }

            }
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        WhoAssignedAt(Emp);
    }

    @FXML
    void assignAction(ActionEvent event) {
        if(room.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Room ID ","Oops!!");
            return;
        }

        if(eid.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Employee ID ","Oops!!");
            return;
        }

        String sql = "INSERT INTO EMPLOYEE_ASSIGN (RID,EID) VALUES(" + room.getText() + "," + eid.getText() +")";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        ShowUI.ShowPop("Succesfully Assigned....","Wah!!\n");
    }

    @FXML
    void backAction(ActionEvent event) {
        primaryStage.close();
        ShowUI.stage.setScene(ShowUI.Services);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        primaryStage.close();
        ShowUI.stage.setScene(ShowUI.scene1);
    }

    void setMain(Main main) {
        this.main = main;
        init();
    }


    public void WhoAssignedAt(ArrayList<Employ> Slist){


        TableView<Employ> teamTable = new TableView<>();


        ObservableList<Employ> teams = FXCollections.observableArrayList(Slist);


        teamTable.setItems(teams);
        teamTable.setMaxSize(500, 400);
        teamTable.setMinSize(500, 400);

        String CoumnNames[] = {"EID", "Profession","Room_Type"};

        String CoumnPVFNames[] = {"eid", "prof","type"};


        for(int i=0; i<CoumnNames.length; i++){
            TableColumn<Employ, String> colName = new TableColumn<>(CoumnNames[i]);
            colName.setCellValueFactory(new PropertyValueFactory<Employ, String>(CoumnPVFNames[i]));
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

        Scene scene = new Scene(root, 500, 400);

        primaryStage.setTitle("Assigned Employees");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public  class Employ{

        private final SimpleStringProperty eid;
        private final SimpleStringProperty prof;
        private final SimpleStringProperty type;

        public Employ(String di ,String p,String t) {
            this.eid = new SimpleStringProperty(di);
            this.prof = new SimpleStringProperty(p);
            this.type = new SimpleStringProperty(t);
        }

        public String getEid() {
            return eid.get();
        }

        public String getProf() {
            return prof.get();
        }

        public String getType() {
            return type.get();
        }
    }
}
