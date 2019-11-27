package HDBMS;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import static javafx.geometry.Pos.TOP_CENTER;

public class AppController
{
    private Main main;

    @FXML
    private TextField pid;

    @FXML
    private TextField eid;

    @FXML
    private ComboBox dname;

    @FXML
    private TextField hour;

    @FXML
    private TextField miniute;

    @FXML
    private ComboBox spec;

    @FXML
    private Button Reset;

    @FXML
    private Button update;

    @FXML
    private  ComboBox timep;

    @FXML
    private  ComboBox type;

    @FXML
    private DatePicker datep;

    @FXML
    private Button register;

    @FXML
    private Button back;

    @FXML
    private Button logout;

    @FXML
    private Button show;

    @FXML
    private Button appoint;

    public void DoctName(String sp){


        ArrayList<String> names  = new ArrayList<>();

        String sql = "SELECT E.NAME FROM EMPLOYEE E JOIN DOCTORS D ON D.EID=E.EID WHERE D.SPECIALIZATION='" + sp  +"' ORDER BY E.NAME";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                names.add(rs.getString(1));
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        dname.getItems().addAll(names);
    }

    public  void init(){


        timep.getItems().addAll("PM","AM");

         ArrayList<String> depts  = new ArrayList<>();

         String sql = "SELECT DISTINCT SPECIALIZATION FROM DOCTORS ORDER BY SPECIALIZATION";

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


         spec.getItems().addAll(depts);

        ArrayList<String> types  = new ArrayList<>();

        sql = "SELECT DISTINCT NAME FROM OPERATIONS ORDER BY NAME";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                types.add(rs.getString(1));
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        type.getItems().addAll(types);

    }

    boolean isSelected(){

        if (spec.getValue() == null) {
            ShowUI.ShowPop("Please Select specialization....\n\nOR Enter EID..\n", "Oopas!");
            return false;
        }

        if (dname.getValue() == null) {
            ShowUI.ShowPop("Please Select Doctor's Name....\n\nOR Enter EID..\n", "Oopas!");
            return false;
        }
        return true;
    }

    @FXML
    void updateAction(ActionEvent event) {
        ShowUI.stage.setScene(ShowUI.AppUpdateScene);
    }

    @FXML
    void specAction(ActionEvent event) {

        dname.getItems().clear();

        DoctName((String)spec.getValue());

        dname.setPromptText("Select");
    }



    @FXML
    void showAction(ActionEvent event)
    {
        String sql , p , d , Eid;
        ArrayList<DoctApp> Slist = new ArrayList<>();

        if(eid.getText().isEmpty()==true && isSelected()==false )return;

        Eid = eid.getText();

        if(datep.getValue() == null){
             sql = "SELECT E.EID , E.NAME , D.SPECIALIZATION , D.TYPE , D.QUALIFICATION , D.VISIT , A.APP_DATE , A.TIME \n" +
                     "FROM EMPLOYEE E LEFT OUTER JOIN APPOINTMENT A  ON E.EID=A.EID AND A.APP_DATE >= SYSDATE , DOCTORS D\n" +
                     "WHERE  E.EID = '" +  Eid  + "' AND E.EID = D.EID";
        }
        else {
            sql = "SELECT E.EID , E.NAME , D.SPECIALIZATION , D.TYPE , D.QUALIFICATION , D.VISIT , A.APP_DATE , A.TIME \n" +
                    "FROM EMPLOYEE E LEFT OUTER JOIN APPOINTMENT A  ON E.EID=A.EID AND" +
                    " A.APP_DATE >= " + "TO_DATE('" + datep.getValue() + "','YYYY-MM-DD')" +  ", DOCTORS D\n" +
                    "WHERE  E.EID = '" +  Eid  + "' AND E.EID = D.EID";
        }

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            boolean yup=true;
            while (rs.next())
            {
                yup = false;
                Slist.add(new DoctApp(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
            }

            if(yup==true){
                sql = "SELECT E.EID , E.NAME , D.SPECIALIZATION , D.TYPE , D.QUALIFICATION , D.VISIT " +
                        "FROM EMPLOYEE E JOIN DOCTORS D ON D.EID = E.EID \n" +

                        "WHERE E.NAME = '" + dname.getValue() + "' AND D.SPECIALIZATION = '" + spec.getValue() +"'";

                pst  = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    Slist.add(new DoctApp(rs.getString(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getString(6),"null","null"));
                }
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        ShowDoctApp(Slist);
    }


    @FXML
    void appointAction(ActionEvent event)
    {
        //type.setValue("Gurte aisi");
        //System.out.println(datep.getValue());
        //datep.setValue(LOCAL_DATE("01-05-2016"));

        String p , sql;

        if(pid.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Patient ID......\n","Oops!");
            return;
        }

        if(hour.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Appointment Time......\n","Oops!");
            return;
        }

        p = hour.getText()+":";

        if (miniute.getText().isEmpty()==true) {
            p += "00";
        } else p += (String) miniute.getText();

        if(timep.getValue()==null) p += " AM";
        else p +=  " "+(String)timep.getValue();

        if(type.getValue()==null){
            ShowUI.ShowPop("Please Select Appointment Type......\n","Oopas!");
            return;
        }

        if (ShowUI.isHe(pid.getText(), "PATIENT", "PID") == false) {
            ShowUI.ShowPop("Please Enter A Valid Patient ID......\n", "Oopas!");
            return;
        }



        if(eid.getText().isEmpty()==false){

            if(ShowUI.isHe(eid.getText(),"DOCTORS" , "EID") == false){
                ShowUI.ShowPop("Please Enter A Valid Doctor ID......\n","Oopas!");
                return;
            }

            if (datep.getValue() == null) {

                sql = "INSERT INTO APPOINTMENT (PID,EID,APP_DATE,TIME,TYPE) VALUES('" + pid.getText() + "','" + eid.getText() + "'," + "SYSDATE" + ",'" + p + "','"
                        + type.getValue() + "')";
            } else {
                System.out.println(datep.getValue());
                sql = "INSERT INTO APPOINTMENT (PID,EID,APP_DATE,TIME,TYPE) VALUES('" + pid.getText() + "','" +
                        eid.getText() + "'," + "TO_DATE('" + datep.getValue() + "','YYYY-MM-DD')" + ",'" + p + "','"
                        + type.getValue() + "')";
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

        }
        else {

            if (spec.getValue() == null) {
                ShowUI.ShowPop("Please Select specialization....\n\nOR Enter EID..\n", "Oopas!");
                return;
            }

            if (dname.getValue() == null) {
                ShowUI.ShowPop("Please Select Doctor.....\n\nOR Enter EID....\n", "Oopas!");
                return;
            }

            if (eid.getText().isEmpty()==true) {
                ShowUI.ShowPop("Please Enter Doctor ID......\n", "Oops!\n");
                return;
            }

            if (datep.getValue() == null) {

                sql = "INSERT INTO APPOINTMENT (PID,EID,APP_DATE,TIME,TYPE) VALUES('" + pid.getText() + "','" + eid.getText() + "'," + "SYSDATE" + ",'" + p + "','"
                        + type.getValue() + "')";
            } else {
                System.out.println("oak");
                sql = "INSERT INTO APPOINTMENT (PID,EID,APP_DATE,TIME,TYPE) VALUES('" + pid.getText() + "','" +
                        eid.getText() + "'," + "TO_DATE('" + datep.getValue() + "','YYYY-MM-DD')" + ",'" + p + "','"
                        + type.getValue() + "')";
            }


            try {
                Connection con = new DBMSConnection().getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                pst.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        ShowUI.ShowPop("Succesfully Appointed....","Wah!!\n");


    }

    @FXML
    void datepAction(ActionEvent event)
    {

        System.out.println(datep.getValue());
    }



    @FXML
    void dnameAction(ActionEvent event){
        String sql = "SELECT E.EID FROM DOCTORS D JOIN EMPLOYEE E ON D.EID = E.EID WHERE D.SPECIALIZATION='" +
                spec.getValue() + "' AND E.NAME = '" + dname.getValue() + "'";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            System.out.println("hre ir aa");
            if(rs.next()){
                eid.setText(rs.getString(1));
                System.out.println("eod sert");
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void timepAction(ActionEvent event){
        System.out.println(timep.getValue());

    }

    @FXML
    void backAction(ActionEvent event) {
          ShowUI.stage.setScene(ShowUI.scene2);
    }

    @FXML
    void logoutAction(ActionEvent event) {
          ShowUI.stage.setScene(ShowUI.scene1);
    }




    @FXML
    void resetAction(ActionEvent event) {
        pid.setText("");
        hour.setText("");
        miniute.setText("");
        eid.setText("");
        init();
    }

    void setMain(Main main) {

        this.main = main;
        init();
    }




    public void ShowDoctApp(ArrayList<DoctApp> Slist){

        Stage primaryStage = new Stage();

        TableView<DoctApp> teamTable = new TableView<>();


        ObservableList<DoctApp> teams = FXCollections.observableArrayList(Slist);


        teamTable.setItems(teams);
        teamTable.setMaxSize(800, 500);
        teamTable.setMinSize(800, 500);

        String CoumnNames[] = {"EID", "Name" , "Specialization" , "Type" , "Degree" , "Visit" , "App_Date" , "App_Time"};

        String CoumnPVFNames[] = {"did", "name" , "special" , "dtype" , "degree" , "visit" , "apdate" , "aptime"};


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

        primaryStage.setTitle("Doctors Appointment Detail.");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public  class DoctApp{

        private final SimpleStringProperty did;
        private final SimpleStringProperty name;
        private final SimpleStringProperty special;
        private final SimpleStringProperty dtype;
        private final SimpleStringProperty degree;
        private final SimpleStringProperty visit;
        private final SimpleStringProperty apdate;
        private final SimpleStringProperty aptime;

        public DoctApp(String di ,String name ,String sp , String dtype,String degree ,String visit ,String apdate ,String aptime) {

            this.did = new SimpleStringProperty(di);
            this.name = new SimpleStringProperty(name);
            this.special = new SimpleStringProperty(sp);
            this.dtype = new SimpleStringProperty(dtype);
            this.degree = new SimpleStringProperty(degree);
            this.visit = new SimpleStringProperty(visit);
            this.apdate = new SimpleStringProperty(apdate);
            this.aptime = new SimpleStringProperty(aptime);
        }

        public String getDid() {
            return did.get();
        }

        public String getName() {
            return name.get();
        }

        public String getSpecial() {
            return special.get();
        }

        public String getDtype() {
            return dtype.get();
        }

        public String getDegree() {
            return degree.get();
        }

        public String getVisit() {
            return visit.get();
        }

        public String getApdate() {
            return apdate.get();
        }

        public String getAptime() {
            return aptime.get();
        }
    }

}
