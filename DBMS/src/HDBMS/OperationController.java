package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class OperationController {

   private Main main;

    @FXML
    private Button edit;

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Button view;

    @FXML
    private Button back;

    @FXML
    private Button logout;

    @FXML
    private Button reset;

    @FXML
    private TextField room;

    @FXML
    private TextField tname;

    @FXML
    private TextField cost;

    @FXML
    private ComboBox op;

    private ArrayList<String> ts  = new ArrayList<>();

    public  void init(){

        ts.clear();

        String sql = "SELECT DISTINCT NAME FROM OPERATIONS ORDER BY NAME";

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

        op.getItems().addAll(ts);
    }

    @FXML
    void editAction(ActionEvent event) {

        if(op.getValue()==null){
            ShowUI.ShowPop("Please Select OPERATION Name.....","Oops!!");
            return;
        }

        String sql="";
        boolean yup=false;

        if(cost.getText().isEmpty()==false){
            sql = "UPDATE OPERATIONS SET COST = " + cost.getText();
            yup=true;
        }

        if(room.getText().isEmpty()==false){
            if(yup) sql += ", ROOM = '" + room.getText() + "'";
            else sql = "UPDATE OPERATIONS SET ROOM = '" + room.getText() + "'";
            yup=true;
        }


        if(yup){

            sql += " WHERE NAME = '" + op.getValue() + "'";

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

        if(tname.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Operation Name ","Oops!!");
            return;
        }

        if(room.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Room ID ","Oops!!");
            return;
        }

        if(cost.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Operation Cost ","Oops!!");
            return;
        }

        Iterator<String> iter = ts.iterator();
        String curItem="" , oname = tname.getText();

        while ( iter .hasNext() == true )
        {
            curItem =(String) iter .next();
            if (curItem.equals(oname)  ) {
                ShowUI.ShowPop("Operation Already Exists ","Oops!!");
                return;
            }

        }

        String  sql="";

        sql = "INSERT INTO OPERATIONS (NAME,COST,ROOM) VALUES('"+ tname.getText() + "','" + cost.getText() + "','" + room.getText() + "')";

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

        if(op.getValue()==null){
            ShowUI.ShowPop("Please Select Test Name.....","Oops!!");
            return;
        }

        String sql = "DELETE FROM APPOINTMENT WHERE TYPE = "+op.getValue() ;

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

        sql = "DELETE FROM OPERATIONS WHERE NAME = "+op.getValue() ;

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

        ShowUI.ShowPop("Succesfully Deleted....","Wah!!\n");
    }

    @FXML
    void viewAction(ActionEvent event) {
        if(op.getValue()==null){
            ShowUI.ShowPop("Please Select OPERATION Name.....","Oops!!");
            return;
        }

        String sql = "SELECT * FROM OPERATIONS WHERE NAME = '" + op.getValue() + "'";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                tname.setText(rs.getString(1));
                room.setText(rs.getString(3));
                cost.setText(rs.getString(2));
            }
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void backAction(ActionEvent event) {
        Reset();
        ShowUI.stage.setScene(ShowUI.Services);
    }

    @FXML
    void logoutAction(ActionEvent event) {
        Reset();
        ShowUI.stage.setScene(ShowUI.scene1);
    }

    @FXML
    void resetAction(ActionEvent event) {
        Reset();
    }

    void Reset(){
        tname.setText("");
        room.setText("");
        cost.setText("");
        init();
    }

    void setMain(Main main) {
        this.main = main;
        init();
    }
}

