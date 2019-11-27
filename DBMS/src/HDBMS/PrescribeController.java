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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrescribeController {

private Main main;

    @FXML
    private TextField pid;

    @FXML
    private TextField presid;

    @FXML
    private TextField mname;

    @FXML
    private TextField tname;

    @FXML
    private TextField comm;

    @FXML
    private TextField dose;

    @FXML
    private TextField duration;

    @FXML
    private TextField recom;

    @FXML
    private DatePicker date;

    @FXML
    private Button view;

    @FXML
    private Button back;

    @FXML
    private Button logout;

    @FXML
    private Button addmed;

    @FXML
    private Button delmed;

    @FXML
    private Button addtest;

    @FXML
    private Button deltest;

    private String id="";

    private Stage primaryStage = new Stage();

    public void init(){

        String sql = "SELECT MAX(PRESCRIPTION_ID)+1 FROM PRESCRIPTION";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                presid.setText(rs.getString(1));
                id = rs.getString(1);
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public boolean prescribe(){

        init();


        if (pid.getText().isEmpty()) {
            ShowUI.ShowPop("Looks Like It's a new Prescription_ID..Please Prescribe First & Enter Pid...... ", "Oops!!");
            return false;
        }

        String sql = "INSERT INTO PRESCRIPTION (PRESCRIPTION_ID,PID,DID,DATE_VISITED,DOCTORS_COMMENT) VALUES(" +
                        presid.getText() + "," + pid.getText() + "," + ShowUI.EID + ",SYSDATE,'"
                        + comm.getText() + "')";

        try {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @FXML
    void commentAction(ActionEvent event){
        if(ShowUI.isHe(presid.getText(),"PRESCRIPTION","PRESCRIPTION_ID")==false){
            if(prescribe()==false)return;
        }

        String sql = "UPDATE PRESCRIPTION SET DOCTORS_COMMENT = '"+ comm.getText() + "' WHERE PRESCRIPTION_ID="+ presid.getText();


        try {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ShowUI.ShowPop("Action Succesfull....", "Wah!!\n");

    }


    @FXML
    void addmedAction(ActionEvent event){
        if(ShowUI.isHe(presid.getText(),"PRESCRIPTION","PRESCRIPTION_ID")==false){

            if(prescribe()==false)return;
        }

        if(mname.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Medicine Name...... ","Oops!!");
            return;
        }

        if(dose.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Dose...... ","Oops!!");
            return;
        }

        if(duration.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Duration...... ","Oops!!");
            return;
        }

        String sql = "INSERT INTO PRESMED (PRES_ID,DOSE,DURATION,MNAME) VALUES(" + presid.getText() + ",'" + dose.getText() + "','" +
                duration.getText() +"','" + mname.getText() + "')";


        try {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ShowUI.ShowPop("Succesfully Added....", "Wah!!\n");
    }

    @FXML
    void delmedAction(ActionEvent event)
    {
        if(ShowUI.isHe(presid.getText(),"PRESCRIPTION","PRESCRIPTION_ID")==false){

            if(prescribe()==false)return;
        }

        if(mname.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Medicine Name...... ","Oops!!");
            return;
        }

        String sql = "DELETE FROM PRESMED WHERE PRES_ID = " + presid.getText()+ " AND MNAME = '" + mname.getText() + "'";

        try {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ShowUI.ShowPop("Succesfully Deleted....", "Wah!!\n");
    }


    @FXML
    void deltestAction(ActionEvent event)
    {
        if(ShowUI.isHe(presid.getText(),"PRESCRIPTION","PRESCRIPTION_ID")==false){
            if(prescribe()==false)return;
        }

        if(tname.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Test Name...... ","Oops!!");
            return;
        }

        String sql = "DELETE FROM PRES_TEST WHERE PRES_ID = " + presid.getText()+ " AND TEST_NAME = '" + tname.getText() + "'";

        try {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ShowUI.ShowPop("Succesfully Deleted....", "Wah!!\n");
    }

    @FXML
    void addtestAction(ActionEvent event){

        if(ShowUI.isHe(presid.getText(),"PRESCRIPTION","PRESCRIPTION_ID")==false){
            if(prescribe()==false)return;
        }

        if(tname.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Test Name...... ","Oops!!");
            return;
        }

        String sql = "INSERT INTO PRES_TEST (PRES_ID,TEST_NAME,RULES) VALUES(" + presid.getText() + ",'" + tname.getText() + "','" +
                        recom.getText() + "')";


        try {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ShowUI.ShowPop("Succesfully Added....", "Wah!!\n");
    }

    @FXML
    void backAction(ActionEvent event) {
        reset();
        ShowUI.stage.setScene(ShowUI.Doct_Home);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        reset();
        ShowUI.stage.setScene(ShowUI.scene1);
    }

    public void reset(){
        presid.setText("");
        pid.setText("");
        mname.setText("");
        dose.setText("");
        duration.setText("");
        tname.setText("");
        recom.setText("");
    }

    @FXML
    void viewAction(ActionEvent event){

        if(ShowUI.isHe(presid.getText(),"PRESCRIPTION","PRESCRIPTION_ID")==false){

            if(prescribe()==false)return;
        }

        String doct="Prescribed By:\n-----------------\n" , prec_to="Prescribe To:\n-----------------\n" , dat="";
        String sql = "SELECT E.NAME,D.SPECIALIZATION,D.QUALIFICATION FROM DOCTORS D JOIN EMPLOYEE E ON D.EID=E.EID WHERE D.EID="+ShowUI.EID;

        ArrayList<Prescrition> Slist = new ArrayList<>();
        ArrayList<Prescrition> Tlist = new ArrayList<>();

        try {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                doct += rs.getString(1) + "\n" + rs.getString(2)+ " , " + rs.getString(3);
            }

            sql = "SELECT P.NAME,P.AGE,PR.DATE_VISITED FROM PRESCRIPTION PR JOIN PATIENT P ON PR.PID=P.PID WHERE PR.PRESCRIPTION_ID="+presid.getText();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            if(rs.next()){
                prec_to += rs.getString(1) + "\n Age : " + rs.getString(2);
                doct += "\nDate : " + rs.getString(3);
            }

            sql = "SELECT * FROM PRESMED WHERE PRES_ID="+presid.getText();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()){
                Slist.add(new Prescrition(rs.getString(4),rs.getString(2),rs.getString(3)));
            }

            sql = "SELECT PT.TEST_NAME,PT.RULES,TH.REPORT FROM PRES_TEST PT LEFT OUTER JOIN TEST_HISTORY TH ON PT.PRES_ID=TH.PRES_ID WHERE PT.PRES_ID="
                    +presid.getText();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()){

                Tlist.add(new Prescrition(rs.getString(1),rs.getString(2),rs.getString(3)));
            }

            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ShowTable(Slist,Tlist,doct,prec_to);
    }

    public void ShowTable(ArrayList<Prescrition> Slist,ArrayList<Prescrition> Tests , String doct, String pat){


        Text txtHeader = new Text("                                     PRESCRIPTION_ID: " + presid.getText()
                                + "\n                                        Medicines");
        txtHeader.setFont(Font.font(20));
        txtHeader.setFill(Color.GREEN);



        TableView<Prescrition> teamTable = new TableView<>();


        ObservableList<Prescrition> teams = FXCollections.observableArrayList(Slist);

        double w=700,h=200;
        teamTable.setItems(teams);
        teamTable.setMaxSize(w, h);
        teamTable.setMinSize(w, h);


        TableColumn<Prescrition, String> colName = new TableColumn<>("Medicine Name");
        colName.setCellValueFactory(new PropertyValueFactory<Prescrition, String>("ame"));
        colName.setMinWidth(teamTable.getMaxWidth()/3);


        TableColumn<Prescrition, String> colCountry = new TableColumn<>("Dose");
        colCountry.setCellValueFactory(new PropertyValueFactory<Prescrition, String>("country"));
        colCountry.setMinWidth(teamTable.getMaxWidth()/3);

        TableColumn<Prescrition, String> colStadium = new TableColumn<>("Duration");
        colStadium.setCellValueFactory(new PropertyValueFactory<Prescrition, String>("stadium"));
        colStadium.setMinWidth(teamTable.getMaxWidth()/3);

        teamTable.getColumns().addAll(colName, colCountry, colStadium);

        GridPane center = new GridPane();


        Text txtHeader1 = new Text("\n                                                       Tests");
        txtHeader1.setFont(Font.font(20));
        txtHeader1.setFill(Color.GREEN);

        TableView<Prescrition> teamTable1 = new TableView<>();


        ObservableList<Prescrition> teams1 = FXCollections.observableArrayList(Tests);


        teamTable1.setItems(teams1);
        teamTable1.setMaxSize(w, h);
        teamTable1.setMinSize(w, h);


        TableColumn<Prescrition, String> colName1 = new TableColumn<>("Test Name");
        colName1.setCellValueFactory(new PropertyValueFactory<Prescrition, String>("ame"));
        colName1.setMinWidth(teamTable.getMaxWidth()/3);


        TableColumn<Prescrition, String> colCountry1 = new TableColumn<>("Recommendation");
        colCountry1.setCellValueFactory(new PropertyValueFactory<Prescrition, String>("country"));
        colCountry1.setMinWidth(teamTable.getMaxWidth()/3);

        TableColumn<Prescrition, String> colStadium1 = new TableColumn<>("Result");
        colStadium1.setCellValueFactory(new PropertyValueFactory<Prescrition, String>("stadium"));
        colStadium1.setMinWidth(teamTable.getMaxWidth()/3);



        teamTable1.getColumns().addAll(colName1, colCountry1, colStadium1);


        VBox right1 = new VBox(0);
        right1.setAlignment(Pos.CENTER);

        Text Pat = new Text(pat);
        Pat.setFont(Font.font(10));


        center.add(Pat, 0, 0, 5, 1);

        center.add(txtHeader, 0, 0, 5, 1);
        center.add(teamTable, 0, 1, 5, 5);

        center.add(txtHeader1, 0, 7, 5, 1);
        center.add(teamTable1, 0, 8, 5, 5);

        Text Doct = new Text(doct);
        Doct.setFont(Font.font(10));
        Doct.setLayoutX(234);




        if(comm.getText().isEmpty()==false) {
            Text com = new Text("Comment : " + comm.getText() + "\n");
            com.setFont(Font.font(13));
            com.setFill(Color.color(0.1, 0.3, 0.8));
            center.add(com, 0, 22, 5, 1);
        }

        center.add(Doct, 0, 21, 5, 1);

        VBox right = new VBox(0);
        right.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setCenter(center);
        root.setRight(right);
        root.setRight(right1);

        Scene scene = new Scene(root, 700, 620);

        primaryStage.setTitle("Prescription");
        primaryStage.setScene(scene);


        primaryStage.show();

    }

    void setMain(Main main) {
        // init();
        this.main = main;

    }

    public  class Prescrition{

        private final SimpleStringProperty ame;
        private final SimpleStringProperty country;
        private final SimpleStringProperty stadium;

        public Prescrition(String strName, String strCountry, String strStadium) {
            this.ame = new SimpleStringProperty(strName);
            this.country = new SimpleStringProperty(strCountry);
            this.stadium = new SimpleStringProperty(strStadium);

        }

        public String getAme() {
            return ame.get();
        }

        public String getCountry() {
            return country.get();
        }

        public String getStadium() {
            return stadium.get();
        }

    }

}
