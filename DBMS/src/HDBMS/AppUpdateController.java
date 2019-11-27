package HDBMS;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

public class AppUpdateController {

    private Main main;

    @FXML
    private TextField pid;

    @FXML
    private TextField eid;

    @FXML
    private TextField hour;

    @FXML
    private TextField miniute;

    @FXML
    private  ComboBox timep;

    @FXML
    private  ComboBox type;

    @FXML
    private DatePicker datep;

    @FXML
    private DatePicker newdate;

    @FXML
    private Button Reset;

    @FXML
    private Button back;

    @FXML
    private Button logout;

    @FXML
    private Button update;

    @FXML
    private Button delete;


    public  void init(){
        String sql;
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

    @FXML
    void updateAction(ActionEvent event) {

        String p="$" , sql;
        boolean yup=false;

        if(pid.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Patient ID......\n","Oopas!");
            return;
        }
        sql = "UPDATE APPOINTMENT SET ";

        if(hour.getText().isEmpty()==false){
            p = hour.getText()+":";

            if (miniute.getText().isEmpty()==true) {
                p += "00";
            } else p += (String) miniute.getText();

            if(timep.getValue()==null) p += " AM";
            else p +=  " "+(String)timep.getValue();

            sql += "TIME = '" + p + "'";
            yup=true;
        }

        if((type.getValue()==null)==false){
            if(yup){
                sql += ", TYPE = '" + type.getValue() + "'";
            }
            else sql += " TYPE = '" + type.getValue() + "'";
            yup=true;
        }

        System.out.println("date et  r r");

        if (datep.getValue() == null) {
            ShowUI.ShowPop("Please Pick Previous Appointment Date......\n", "Oopas!");
            return;
        }


        if (ShowUI.isHe(pid.getText(), "PATIENT", "PID") == false) {
            ShowUI.ShowPop("Please Enter A Valid Patient ID......\n", "Oopas!");
            return;
        }

        if(yup)sql += ",";

        if(newdate.getValue()==null)sql += " APP_DATE = TO_DATE('" + datep.getValue() + "','YYYY-MM-DD')";
        else {
            sql += " APP_DATE = TO_DATE('" + newdate.getValue() + "','YYYY-MM-DD')";
            yup=true;
        }

        if (eid.getText().isEmpty()==true) {
            ShowUI.ShowPop("Please Enter Doctor ID......\n", "Oops!\n");
            return;
        }

        if(ShowUI.isHe(eid.getText(),"DOCTORS" , "EID") == false){
            ShowUI.ShowPop("Please Enter A Valid Doctor ID......\n","Oopas!");
            return;
        }

        if(yup) {

            sql += " WHERE EID = '" + eid.getText() + "' AND PID = '" + pid.getText() + "' AND APP_DATE = " +
                    "TO_DATE('" + datep.getValue() + "','YYYY-MM-DD')";

            try {
                Connection con = new DBMSConnection().getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                pst.close();
                con.close();
            } catch (SQLException e) {
                System.out.println("u r doomed");
                e.printStackTrace();
            }

            ShowUI.ShowPop("Succesfully Updated....", "Wah!!\n");
        }
    }

    @FXML
    void deleteAction(ActionEvent event)
    {
        if (datep.getValue() == null) {
            ShowUI.ShowPop("Please Pick A Date from 'Date'......\n", "Oopas!");
            return;
        }

        String sql = "DELETE FROM APPOINTMENT WHERE APP_DATE<= " + "TO_DATE('" + datep.getValue() + "','YYYY-MM-DD')";

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
    void datepAction(ActionEvent event)
    {

    }


    @FXML
    void timepAction(ActionEvent event){
        System.out.println(timep.getValue());

    }

    @FXML
    void backAction(ActionEvent event) {
        ShowUI.stage.setScene(ShowUI.appo);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        ShowUI.stage.setScene(ShowUI.scene1);
    }

    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }


    @FXML
    void resetAction(ActionEvent event) {
        pid.setText("");
        hour.setText("");
        miniute.setText("");
        eid.setText("");
    }

    void setMain(Main main) {

        this.main = main;
        init();
    }
}
