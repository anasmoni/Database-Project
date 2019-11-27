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

public class LoginController
{

    private Main main;

    @FXML
    private Label label;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField recover_email;

    @FXML
    private PasswordField email;

    @FXML
    private Button reset;

    @FXML
    private Button login;

    @FXML
    private Button recover;

    @FXML
    private ComboBox log;

    private String proff;

    public void init(){
        log.getItems().addAll("Admin","Doctor","Receptionist","Cashier","Pathologist","Pharmacist");
    }

    boolean isOka(){

        if(username.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter UserName.....","Oops!!");
            return false;
        }

        if(password.getText().isEmpty()==true){

            ShowUI.ShowPop("Please Enter Password.....","Oops!!");
            return false;
        }

        if(check()==false){

            ShowUI.ShowPop("Wrong Password or Username...","Oops!!");
            return false;
        }

        ShowUI.UserName = username.getText();
        ShowUI.PassWord = password.getText();

        String sql = "SELECT E.EID, E.PROFESSION FROM EMPLOYEE E JOIN LOGIN L ON L.EID=E.EID WHERE L.USERNAME ='"+ username.getText() +
                "' AND L.PASSWORD='" + password.getText() +"'";


        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                proff = rs.getString(2);
                ShowUI.EID = rs.getString(1);
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return true;
    }

   public boolean check(){

        String sql = "SELECT PASSWORD FROM LOGIN  WHERE USERNAME="+"'"+username.getText()+"'";
        boolean ret=true;
        try
        {

            Connection con = new DBMSConnection().getConnection();
            System.out.println(password.getText());
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                //System.out.println(rs.getString(1));

                if (rs.getString(1).equals(password.getText()) == false) {
                    ret = false;

                }
            }
            else ret=false;

            pst.close();
            con.close();
        } catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }

        return ret;
    }

    @FXML
    void loginAction(ActionEvent event) {

        if(log.getValue()==null){
            ShowUI.ShowPop("Please Select LogIn Type..... ", "Oops!!");
            return;
        }

        String who = (String)log.getValue();
        //"Admin","Doctor","Receptionist","Cashier","Clerk","Pathologist","Pharmacist");

        if(who.equals("Admin")){

            if(isOka()==false)return;

            if(proff.equals("Admin")==false){
                ShowUI.ShowPop("Invalid LogIn Atempt.....\nNot a Admin UserName-PassWord\n\n","Oops!!");
                return;
            }


            ShowUI.stage.setScene(ShowUI.AdminHomeScene);
        }
        else if(who.equals("Doctor")){

            if(isOka()==false)return;

            if(proff.equals("Doctor")==false){
                ShowUI.ShowPop("Invalid LogIn Atempt.....\nNot a Doctor's UserName-PassWord\n\n","Oops!!");
                return;
            }

            ShowUI.stage.setScene(ShowUI.Doct_Home);
        }
        else if(who.equals("Receptionist")) {

            if (isOka() == false) return;

            if (proff.equals("Receptionist") == false) {
                ShowUI.ShowPop("Invalid LogIn Atempt.....\nNot a Receptionist's UserName-PassWord\n\n", "Oops!!");
                return;
            }


            ShowUI.stage.setScene(ShowUI.scene2);
        }
        else if(who.equals("Cashier")){

            if (isOka() == false) return;

            if (proff.equals("Cashier") == false) {
                ShowUI.ShowPop("Invalid LogIn Atempt.....\nNot a Cashier's UserName-PassWord\n\n", "Oops!!");
                return;
            }

            ShowUI.stage.setScene(ShowUI.CashHome);
        }
        else if(who.equals("Pathologist")){

            if (isOka() == false) return;

            if (proff.equals("Pathologist") == false) {
                ShowUI.ShowPop("Invalid LogIn Atempt.....\nNot a Pathologist's UserName-PassWord\n\n", "Oops!!");
                return;
            }

            ShowUI.stage.setScene(ShowUI.PathoHome);
        }
        else{


            if (isOka() == false) return;

            if (proff.equals("Pharmacist") == false) {
                ShowUI.ShowPop("Invalid LogIn Atempt.....\nNot a Pharmacist's UserName-PassWord\n\n", "Oops!!");
                return;
            }

            ShowUI.stage.setScene(ShowUI.PhrmHome);
        }
    }

    @FXML
    void recoverAction(ActionEvent event){

        String e = recover_email.getText();
        if( e.length()<=15 || e.substring(e.length()-10 , e.length()).equals("@gmail.com") == false ){
            ShowUI.ShowPop("Please Enter A Valid Email...","Oops!!");
            return;
        }

        String sql,U,P;
        U=P="";
        boolean yup=true;
        sql = "SELECT USERNAME, PASSWORD FROM LOGIN L JOIN EMPLOYEE E ON L.EID=E.EID AND E.EMAIL = '" + e + "'";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                U = rs.getString(1);
                P = rs.getString(2);
                yup=false;
            }
            pst.close();
            con.close();
        } catch (SQLException ee)
        {
            ee.printStackTrace();
        }

        if(yup){
            ShowUI.ShowPop("No Such Email Found...","Oops!!");
            return;
        }

        SendMail.sendit(e,U,P);

        ShowUI.ShowPop("Your UserName-PassWord sent to your Email...","WAH!!");
    }


    @FXML
    void resetAction(ActionEvent event) {
        username.setText("");
        password.setText("");
    }

    void setMain(Main main) {

        this.main = main;

        init();

        ShowUI.stage.setScene(ShowUI.scene1);;
        ShowUI.stage.show();
    }

}
