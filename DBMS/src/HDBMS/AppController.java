package HDBMS;

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
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppController
{
    private Main main;

    @FXML
    private TextField pid;

    @FXML
    private TextField dname;

    @FXML
    private TextField time;

    @FXML
    private TextField deptname;

    @FXML
    private Button Reset;

    @FXML
    private  ComboBox dept;

    @FXML
    private  ComboBox timep;

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

     public  void init(){



        timep.getItems().addAll("AM","PM");

        dept.getItems().addAll(

            "Acute medical unit",
            "Allergy and Clinical Immunology Unit",
            "Anesthesiology ",
            "Anesthesiology and Intensive Care ",
            "Bone Marrow Transplantation",
            "Burn center",
            "Cardiology",
            "Cardiothoracic Surgery ",
            "Central sterile services department",
            "Clinical Microbiology and Infectious Diseases",
            "Coronary care unit",
            "Department of Medicine ",
            "Department of Medicine - Mt. Scopus",
            "DermatologyEar Nose and Throat and Head / Neck Surgery",
            "Emergency Medicine ",
            "Emergency department",
            "Endocrinology ",
            "Endoscopy unit",
            "Gastroenterology ",
            "Genetics and Metabolic Diseases",
            "Geriatric intensive-care unit",
            "Geriatrics ",
            "Hematology",
            "Intensive Care",
            "Intensive care unit",
            "Margaret and Charles Juravinski Centre",
            "Medical Biophysics and Nuclear Medicine",
            "Metabolic Diseases and Eating Disorders",
            "Neonatal intensive care unit",
            "Neonatologyû Mt. Scopus",
            "Nephrology and Hypertension",
            "Neurology",
            "Neurology - Pediatrics",
            "Obstetrics and Gynecology",
            "Oncology",
            "Ophthalmology",
            "Oral & Maxillofacial Surgery",
            "Orthopedics",
            "Osteoporosis ",
            "Pathology ",
            "Pediatric Surgery",
            "Pediatric intensive care unit",
            "Pediatrics ",
            "Physical Medicine and Rehabilitation",
            "Physical therapy",
            "Plastic and Esthetic Surgery",
            "Post-anesthesia care unit",
            "Psychiatric hospital",
            "Psychiatry and Child Psychiatry ",
            "Pulmonology ",
            "Radiology ",
            "Rehabilitation - Mt. Scopus",
            "Rheumatology ",
            "Surgery - Ein Kerem ",
            "Surgery - Mt. Scopus",
            "Tissue Typing",
            "Urology",
            "Urology ",
            "Vascular Surgery "
        );

    }

    @FXML
    void showAction(ActionEvent event)
    {
        String sql =
                "SELECT  E.NAME , D.SPECIALIZATION , (SELECT COUNT(*) FROM APPOINTMENT AA WHERE AA.EID=D.EID AND AA.APP_DATE="+
                        "TO_DATE('" + datep.getValue()+"','YYYY-MM-DD'))" +
                        "FROM  DOCTORS D LEFT OUTER JOIN EMPLOYEE E ON D.EID=E.EID\n" +
                        "WHERE D.SPECIALIZATION=" +"'" +deptname.getText()+"'"+ "ORDER BY E.NAME"
                ;

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            String s = "NAME\t" + "\t\tSPECIALIZATION\t\t" + "\t\tAPPOINTMENTS\n";
            ResultSet rs = pst.executeQuery();
            System.out.println(s);

            while (rs.next())
            {
                s = "";
                for(int i=1; i<=3; i++){
                    s += rs.getString(i) + "\t\t";
                }
                System.out.println(s);
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void appointAction(ActionEvent event)
    {

    }

    @FXML
    void datepAction(ActionEvent event)
    {

        System.out.println(datep.getValue());
    }


    @FXML
    void deptAction(ActionEvent event){

        System.out.println(dept.getValue());
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
        pid.setText(null);
        dname.setText(null);
        deptname.setText(null);
        time.setText(null);


    }


}
