package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyController {

    private Main main;

    @FXML
    private Button reset;

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private Button logout;

    @FXML
    private Button view;

    @FXML
    private Button delete;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;

    @FXML
    private TextField web;

    @FXML
    private ComboBox comp;

    public void init(){

        ArrayList<String> DP  = new ArrayList<>();

        String sql = "SELECT DISTINCT NAME FROM COMPANIES ORDER BY NAME";

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
        comp.getItems().addAll(DP);
    }

    @FXML
    void deleteAction(ActionEvent event) {
        if (comp.getValue() == null) {
            ShowUI.ShowPop("Please Select Company..... ", "Oops!!");
            return;
        }

        String sql = "DELETE FROM PURCHASE WHERE COMPANY = '" + comp.getValue() + "'";

        try {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "DELETE FROM COMPANIES WHERE NAME = '" + comp.getValue() + "'";

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

    @FXML
    void addAction(ActionEvent event) {

        if(name.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Company Name..... ", "Oops!!");
            return;
        }

        if(address.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Company Address..... ", "Oops!!");
            return;
        }

        if(phone.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Contact No..... ", "Oops!!");
            return;
        }

        if(web.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Company's WebSite..... ", "Oops!!");
            return;
        }

        String sql;
        boolean ase = false;
        sql = "SELECT * FROM COMPANIES WHERE NAME = '" +name.getText() +"'";

        try {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next())ase=true;
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(ase){
            ShowUI.ShowPop("Company Already Exist..... ", "Oops!!");
            return;
        }
        else {
            sql = "INSERT INTO COMPANIES (NAME,ADDRESS,CONTACT,WEBSITE) VALUES('" + name.getText() + "','" + address.getText() + "','"
                    + phone.getText() + "','" + web.getText() + "')";

            try {
                Connection con = new DBMSConnection().getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                pst.close();
                con.close();
                ShowUI.ShowPop("Action Successfull..... ", "WAH!!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void viewAction(ActionEvent event) {

        if (comp.getValue() == null) {
            ShowUI.ShowPop("Please Select Company..... ", "Oops!!");
            return;
        }

        String sql = "SELECT * FROM COMPANIES WHERE NAME = '" + comp.getValue() + "'";

        try {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                name.setText(rs.getString(1));
                address.setText(rs.getString(2));
                phone.setText(rs.getString(3));
                web.setText(rs.getString(4));
            }
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void resetAction(ActionEvent event){
        reset();
    }

    @FXML
    void backAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.AdminHomeScene);
    }

    @FXML
    void logoutAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.scene1);
    }

    void reset(){
        init();
        name.setText("");
        web.setText("");
        phone.setText("");
        address.setText("");
    }

    void setMain(Main main) {

        this.main = main;
        init();
    }
}


