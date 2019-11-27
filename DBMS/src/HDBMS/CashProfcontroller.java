package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashProfcontroller {

    private Main main;

    @FXML
    private Button view;

    @FXML
    private Button edit;

    @FXML
    private Button pback;

    @FXML
    private Button signout;

    @FXML
    private TextField eid;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;

    @FXML
    private TextField age;

    @FXML
    private TextField jdate;

    @FXML
    private TextField degree;

    @FXML
    private TextField dept;

    @FXML
    private TextField email;

    @FXML
    private TextField sal;

    @FXML
    private TextField username;

    @FXML
    private TextField pass;

    boolean check(String TABLE , String VAL , String COL){
        boolean ret=true;

        String sql = "SELECT COUNT(*) FROM " + TABLE +  " WHERE " + COL +"='"+ VAL +"'";


        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                if(rs.getString(1).equals("0")==false)ret = false;
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return ret;
    }

    @FXML
    void viewAction(ActionEvent event)
    {
        String sql = "SELECT E.EID , E.NAME, E.ADDRESS, E.PHONE, E.AGE , E.JOIN_DATE, E.EMAIL,E.SALARY, E.DEGREE , E.DEPT ,\n" +
                "L.USERNAME , L.PASSWORD  FROM EMPLOYEE E JOIN LOGIN L ON L.EID=E.EID WHERE L.USERNAME ='"+ ShowUI.UserName +
                "' AND L.PASSWORD='" + ShowUI.PassWord +"'";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                eid.setText(rs.getString(1));
                name.setText(rs.getString(2));
                address.setText(rs.getString(3));
                phone.setText(rs.getString(4));
                age.setText(rs.getString(5));
                jdate.setText(rs.getString(6));
                email.setText(rs.getString(7));
                sal.setText(rs.getString(8));
                degree.setText(rs.getString(9));
                dept.setText(rs.getString(10));
                username.setText(rs.getString(11));
                pass.setText(rs.getString(12));
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void editAction(ActionEvent event)
    {
        String sql ,EID, NAME, PHONE, EMAIL, ADDRESS ;

        sql = EID = NAME = PHONE = EMAIL = ADDRESS  = "$";

        sql = "SELECT E.NAME,E.PHONE,  E.EMAIL, E.ADDRESS , E.EID FROM EMPLOYEE E JOIN LOGIN L ON L.EID=E.EID WHERE L.USERNAME ='"+
                ShowUI.UserName +
                "' AND L.PASSWORD='" + ShowUI.PassWord +"'";


        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){

                NAME = rs.getString(1);
                PHONE = rs.getString(2);
                EMAIL = rs.getString(3);
                ADDRESS = rs.getString(4);
                EID = rs.getString(5);
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        boolean yup=false;



        if(name.getText().equals(NAME)==false){

            sql = "UPDATE EMPLOYEE  SET NAME = '" + name.getText() + "'";

            yup=true;
        }

        if(phone.getText().equals(PHONE)==false){

            if(phone.getText().length()<6){
                ShowUI.ShowPop("Phone No  must contains at least 6 digits ","Oops!!");
                return;
            }

            if(yup==false){
                sql = "UPDATE EMPLOYEE  SET PHONE = '" + phone.getText() + "'";
            }
            else{
                sql += ",PHONE = '" + phone.getText() + "'";
            }
            yup=true;
        }

        if(address.getText().equals(ADDRESS)==false){
            if(yup==false){
                sql = "UPDATE EMPLOYEE  SET ADDRESS = '" + address.getText() + "'";
            }
            else{
                sql += ",ADDRESS = '" + address.getText()  + "'";
            }
            yup=true;
        }

        if(email.getText().equals(EMAIL)==false){
            String e = email.getText();
            if(e.length()<=15 || e.substring(e.length()-10 , e.length()).equals("@gmail.com") == false ){
                ShowUI.ShowPop("Please Enter A Valid Email...","Oops!!");
                return;
            }

            if(check("EMPLOYEE", e, "EMAIL" )==false){
                ShowUI.ShowPop("Email already exist...","Oops!!");
                return;
            }

            if(yup==false){
                sql = "UPDATE EMPLOYEE  SET EMAIL = '" + e + "'";
            }
            else{
                sql += ",EMAIL = '" + e + "'";
            }
            yup=true;
        }


        if(yup){

            sql += " WHERE EID = " + EID;

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

        boolean yey=false;

        if(username.getText().equals(ShowUI.UserName)==false && username.getText().isEmpty()==false){

            String u = username.getText();

            if(u.length()<8){
                ShowUI.ShowPop("User Name must contains at least 8 charecters ","Oops!!");
                return;
            }

            if(check("LOGIN",u, "USERNAME" )==false){
                ShowUI.ShowPop("UserName already exist...","Oops!!");
                return;
            }

            sql = "UPDATE LOGIN  SET USERNAME = '" + u + "'";

            ShowUI.UserName  = u;
            yey=true;
        }

        if(pass.getText().equals(ShowUI.PassWord)==false && pass.getText().isEmpty()==false){

            String p = pass.getText();

            if(p.length()<8){
                ShowUI.ShowPop("PassWord must contains at least 8 charecters ","Oops!!");
                return;
            }

            if(check("LOGIN",p, "PASSWORD" )==false){
                ShowUI.ShowPop("Please Enter a Unique PassWord...","Oops!!");
                return;
            }

            if(yey){
                sql += ", PASSWORD = '" + pass.getText() + "'";
            }
            else{
                sql = "UPDATE LOGIN  SET PASSWORD = '" + pass.getText() + "'";
            }

            ShowUI.PassWord = pass.getText();
            yey=true;
        }


        if(yey){
            sql += " WHERE EID = " + EID ;

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

        if(yup || yey){
            ShowUI.ShowPop("Succesfully Updated.....","WAH!!");
        }
    }




    @FXML
    void pbackAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.CashHome);
    }

    @FXML
    void signoutAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.scene1);
    }


    void reset(){

        eid.setText("");
        name.setText("");
        address.setText("");
        phone.setText("");
        age.setText("");
        jdate.setText("");
        sal.setText("");
        username.setText("");
        pass.setText("");
        email.setText("");
        dept.setText("");
        degree.setText("");
    }

    void setMain(Main main) {

        this.main = main;
    }

}
