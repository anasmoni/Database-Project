package HDBMS;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;;
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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class PatientController {

   private Main main;

    @FXML
    private Button view;

    @FXML
    private Button assign;

    @FXML
    private Button pback;

    @FXML
    private Button add;

    @FXML
    private Button resetp;

    @FXML
    private Button signout;

    @FXML
    private Button delete;

    @FXML
    private Button vacants;

    @FXML
    private Button release;

    @FXML
    private TextField pid;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField date;

    @FXML
    private TextField phone;

    @FXML
    private TextField gphone;

    @FXML
    private TextField age;

    @FXML
    private DatePicker cin;

    @FXML
    private ComboBox sex;

    @FXML
    private ComboBox dept;

    @FXML
    private TextField room;

    @FXML
    private TextField bed;

    private Stage primaryStage;

    public void init(){

        primaryStage = new Stage();

        sex.getItems().addAll(
                "Male",
                "Female"
        );

        ArrayList<String> DP  = new ArrayList<>();

        String sql = "SELECT DISTINCT DEPT FROM ROOMS ORDER BY DEPT";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                DP.add(rs.getString(1));
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        dept.getItems().addAll(DP);

    }

    @FXML
    void deleteAction(ActionEvent event) {

        if(cin.getValue()==null){
            ShowUI.ShowPop("Please Pick a Date....","Oops!!");
            return;
        }

        String sql = "BEGIN\n" +
                "    DELETE_PATIENT_BEFORE(" + "TO_DATE('" + cin.getValue() + "','YYYY-MM-DD') );\n" +
                "END;";

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

        ShowUI.ShowPop("Successfully Deleted..... ","WAH!!");

    }

    @FXML
    void viewAction(ActionEvent event)
    {

        if(pid.getText().isEmpty()==false){

            String sql = "SELECT  P.PID,P.NAME,P.AGE,P.SEX,P.ADDRESS,P.PHONE,P.GUARDIAN_CONTACT,P.DATE_VISITED, S.RID , " +
                    "S.BID, P.DEPT FROM PATIENT P LEFT OUTER JOIN STAYS S ON P.PID=S.PID WHERE P.PID = "+ pid.getText();

            try
            {
                Connection con = new DBMSConnection().getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    //PID,NAME,AGE,SEX,ADDRESS,PHONE,GUARDIAN_CONTACT,DATE_VISITED)
                    pid.setText(rs.getString(1));
                    name.setText(rs.getString(2));
                    age.setText(rs.getString(3));
                    sex.setValue(rs.getString(4));/////
                    address.setText(rs.getString(5));
                    phone.setText(rs.getString(6));
                    gphone.setText(rs.getString(7));
                    date.setText(rs.getString(8));
                    room.setText(rs.getString(9));
                    bed.setText(rs.getString(10));
                    dept.setValue(rs.getString(11));
                }
                pst.close();
                con.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }


        }
        else{
            ShowUI.ShowPop("Please Enter PID..... ","Oops!!");
        }
    }

    @FXML
    void resetpAction(ActionEvent event){
        reset();
    }

    @FXML
    void releaseAction(ActionEvent event){

        if(pid.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter PID..... ","Oops!!");
            return;
        }

        if(room.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Room No..... ","Oops!!");
            return;
        }
        /*
        if(cin.getValue()==null){
            ShowUI.ShowPop("Please Pick a Date....","Oops!!");
            return;
        }
        */

        String sql = "UPDATE STAYS SET CHECK_OUT = SYSDATE WHERE PID=" + pid.getText() + " AND RID=" + room.getText();

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
        ShowUI.ShowPop("Successfully Released..... ","WAH!!");


    }

    @FXML
    void addAction(ActionEvent event){



        String sql = "SELECT MAX(PID)+1 FROM PATIENT" , ed="";


        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                ed = rs.getString(1);
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        pid.setText(ed);

        if(name.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Name..... ","Oops!!");
            return;
        }

        if(address.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Address..... ","Oops!!");
            return;
        }

        if(phone.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Phone No..... ","Oops!!");
            return;
        }

        if(age.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Age..... ","Oops!!");
            return;
        }

        if(gphone.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Guardian's Phone No..... ","Oops!!");
            return;
        }

        if(sex.getValue()==null){
            ShowUI.ShowPop("Please Select Gender..... ","Oops!!");
            return;
        }

        if(cin.getValue()==null){
            ShowUI.ShowPop("Please Pick a Date..... ","Oops!!");
            return;
        }

        if(dept.getValue()==null){
            ShowUI.ShowPop("Please Select Department..... ","Oops!!");
            return;
        }

        sql = "INSERT INTO PATIENT (PID,NAME,AGE,SEX,ADDRESS,PHONE,GUARDIAN_CONTACT,DATE_VISITED,DEPT) " +
                "VALUES(" + ed + ",'" + name.getText() + "'," + age.getText() + ",'" + sex.getValue() + "','" +
                address.getText()  + "','" + phone.getText() + "','" + gphone.getText() + "',TO_DATE('" + cin.getValue() + "','YYYY-MM-DD'),'"
                + dept.getValue()  + "')";

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
        ShowUI.ShowPop("Successfully Added..... ","WAH!!");
    }

    @FXML
    void assignAction(ActionEvent event)
    {

        if(pid.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter PID..... ","Oops!!");
            return;
        }

        if(room.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Room No..... ","Oops!!");
            return;
        }

        if(cin.getValue()==null){
            ShowUI.ShowPop("Please Pick a Date..... ","Oops!!");
            return;
        }

        /*
        CallableStatement callSt = null;
        try {

            callSt = con.prepareCall("{?= call myfunction(?,?)}");
            callSt.setInt(1,200);
            callSt.registerOutParameter(2, Types.DOUBLE);
            callSt.execute();
            Double output = callSt.getDouble(2);
            System.out.println("The output returned from sql function: "+output);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                if(callSt != null) callSt.close();
                if(con != null) con.close();
            } catch(Exception ex){}
        }
        */


        String sql = "SELECT COUNT(*)  FROM ROOMS WHERE TYPE='WARD' AND RID = " + room.getText();
        boolean ret=true;
        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                if(rs.getString(1).equals("0"))ret = false;
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        if(ret && bed.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Bed No cz It's a Ward..... ","Oops!!");
            return;
        }


         sql = "INSERT INTO STAYS (PID,RID,BID,CHECK_IN,CHECK_OUT) VALUES(" + pid.getText() + "," + room.getText() +
                        ",'" + bed.getText() + "',TO_DATE('" + cin.getValue() + "','YYYY-MM-DD'),null)";


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
        ShowUI.ShowPop("Succesfully Assigned.....","WAH!!");
    }

    @FXML
    void pbackAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.scene2);
    }

    @FXML
    void signoutAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.scene1);
    }


    void reset(){

        pid.setText("");
        name.setText("");
        address.setText("");
        phone.setText("");
        age.setText("");
        room.setText("");
        bed.setText("");
        gphone.setText("");
        date.setText("");
        primaryStage.close();
    }

    void setMain(Main main) {

        this.main = main;
        init();
    }

    @FXML
    void vacAction(ActionEvent event)
    {
        if(dept.getValue()==null){
            ShowUI.ShowPop("Please Select Department..... ","Oops!!");
            return;
        }

        String sql = "SELECT DISTINCT S.RID,S.BID,R.COST,R.TYPE FROM STAYS S JOIN ROOMS R ON S.RID=R.RID \n" +
                "WHERE S.CHECK_OUT<=SYSDATE AND R.DEPT = '" + dept.getValue() + "' AND (R.TYPE='CABIN' OR R.TYPE='WARD')\n" +
                "ORDER BY TYPE";

        ArrayList<Vacant_Rooms> Slist = new ArrayList<>();

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            boolean yup=true;
            while (rs.next())
            {
                yup = false;
                Slist.add(new Vacant_Rooms(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4)));
            }
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        ShowRooms(Slist);
    }

    public void ShowRooms(ArrayList<Vacant_Rooms> Slist){


        TableView<Vacant_Rooms> teamTable = new TableView<>();


        ObservableList<Vacant_Rooms> teams = FXCollections.observableArrayList(Slist);


        teamTable.setItems(teams);
        teamTable.setMaxSize(800, 500);
        teamTable.setMinSize(800, 500);

        String CoumnNames[] = {"Room_No", "Bed_No" , "Cost" , "Type"};

        String CoumnPVFNames[] = {"rid", "bid" , "cost" , "type"};


        for(int i=0; i<CoumnNames.length; i++){
            TableColumn<Vacant_Rooms, String> colName = new TableColumn<>(CoumnNames[i]);
            colName.setCellValueFactory(new PropertyValueFactory<Vacant_Rooms, String>(CoumnPVFNames[i]));
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

        primaryStage.setTitle("Available Cabin And Ward(Beds).");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public  class Vacant_Rooms{

        private final SimpleStringProperty rid;
        private final SimpleStringProperty bid;
        private final SimpleStringProperty cost;
        private final SimpleStringProperty type;

        public Vacant_Rooms(String di ,String b ,String c , String t) {

            this.rid = new SimpleStringProperty(di);
            this.bid = new SimpleStringProperty(b);
            this.cost = new SimpleStringProperty(c);
            this.type = new SimpleStringProperty(t);
        }

        public String getRid() {
            return rid.get();
        }

        public String getBid() {
            return bid.get();
        }
        public String getCost() {
            return cost.get();
        }

        public String getType() {
            return type.get();
        }
    }
}
