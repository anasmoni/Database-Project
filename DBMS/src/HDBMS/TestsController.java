package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestsController {

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
    private ComboBox tests;

    public  void init(){

        ArrayList<String> ts  = new ArrayList<>();

        String sql = "SELECT DISTINCT NAME FROM TESTS ORDER BY NAME";

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

        tests.getItems().addAll(ts);
    }

    @FXML
    void editAction(ActionEvent event) {

        if(tests.getValue()==null){
            ShowUI.ShowPop("Please Select Test Name.....","Oops!!");
            return;
        }

        String sql="";
        boolean yup=false;

        if(cost.getText().isEmpty()==false){
            sql = "UPDATE TESTS SET COST = " + cost.getText();
            yup=true;
        }

        if(room.getText().isEmpty()==false){
            if(yup) sql += ", LAB_NO = '" + room.getText() + "'";
            else sql = "UPDATE TESTS SET LAB_NO = '" + room.getText() + "'";
            yup=true;
        }


        if(yup){

            sql += " WHERE NAME = '" + tests.getValue() + "'";

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
            ShowUI.ShowPop("Please Enter Test Name ","Oops!!");
            return;
        }

        if(room.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Room ID ","Oops!!");
            return;
        }

        if(cost.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Test Cost ","Oops!!");
            return;
        }

        String  sql="";

        sql = "INSERT INTO TESTS (NAME,LAB_NO,COST) VALUES('"+ tname.getText() + "','" + room.getText() + "','" + cost.getText() + "')";

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

        if(tests.getValue()==null){
            ShowUI.ShowPop("Please Select Test Name.....","Oops!!");
            return;
        }
        /*
        String sql = "DELETE FROM TEST_HISTORY WHERE NAME = "+tests.getValue() ;

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
        String sql = "DELETE FROM TEST_HISTORY WHERE TEST_NAME = '"+tests.getValue() +"'";

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
        if(tests.getValue()==null){
            ShowUI.ShowPop("Please Select Test Name.....","Oops!!");
            return;
        }

        String sql = "SELECT * FROM TESTS WHERE NAME = '" + tests.getValue() + "'";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                tname.setText(rs.getString(1));
                room.setText(rs.getString(2));
                cost.setText(rs.getString(3));
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

