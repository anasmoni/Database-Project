package HDBMS;

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

public class ManageNurse
{

    private Main main;
    private  String output;


    @FXML
    private TextField nid;

    @FXML
    private TextField room;

    @FXML
    private TextField shift;

    @FXML
    private Button details;

    @FXML
    private Button delete;

    @FXML
    private Button assign;

    @FXML
    private Button reset;

    @FXML
    private Button isassigned;

    @FXML
    private Button Back;

    @FXML
    void Backaction(ActionEvent event){
       // System.out.println("oka");
        ShowUI.stage.setScene(ShowUI.scene1);
       // System.out.println("balmar");

    }

    public boolean isNurse(){

        String sql = "SELECT PROFESSION FROM EMPLOYEE  WHERE EID="+nid.getText();
        boolean ret=true;
        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                //System.out.println(rs.getString(1));
                if (rs.getString(1).equals("Nurse") == false) {
                    ret = false;

                }
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return ret;
    }

    private void NurseManagedAs(){
        try {

            ShowUI.stage.setScene(ShowUI.scene2);


        }catch (Exception e){
            System.out.println(e);
        }
        output = "";
    }

    public void DeleteNurse(){
        String sql = "DELETE FROM NURSE_ASSIGN  WHERE EID="+nid.getText();

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void resetAction(ActionEvent event){
        nid.setText("");
        room.setText("");
        shift.setText("");
    }

    @FXML
    void detailsAction(ActionEvent event)
    {
        if(nid.getText().isEmpty()==true){
            output = "  Please enter nurse id" ;
            NurseManagedAs();
            return;
        }

        if(isNurse()==false){
            output = " Please Enter A Valid Nurse Id";
            NurseManagedAs();
            return;
        }

        String sql = "SELECT * FROM EMPLOYEE  WHERE EID="+nid.getText();

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            String []s = {"EID : ","\nNAME : ","\nADDRESS : ","\nPHONE : ","\nAGE : ","\nPROFFESSION : ","\nSALARY : ","\nGENDER : ",
                    "\nJOIN_DATE : "};
            ResultSet rs = pst.executeQuery();

            while (rs.next())
            {
                for(int i=1; i<=9; i++){
                    output += s[i-1] + rs.getString(i);
                }
            }

            sql = "SELECT DEPT,TYPE FROM NURSE  WHERE EID="+nid.getText();
            pst = con.prepareStatement(sql);

            rs = pst.executeQuery();

            if(rs.next())
            {
                output += "\nDEPARTMENT : " + rs.getString(1) + "\nEXPERTISE : " + rs.getString(2);
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        NurseManagedAs();
    }

    @FXML
    void deleteAction(ActionEvent event)
    {
        if(nid.getText().isEmpty()==true){
            output = " Please enter nurse id";
            NurseManagedAs();
            return;
        }

        if(isNurse()) {
            DeleteNurse();
            output = " Deleted.....";
            NurseManagedAs();
        }
        else {
            output = " Please Enter A Valid Nurse Id";
            NurseManagedAs();
        }
     //   System.out.println(nid.getText() + " Deleted");
    }

    @FXML
    void assignAction(ActionEvent event)
    {
        if(nid.getText().isEmpty()==true){
            output = " Please enter nurse id";
            NurseManagedAs();
            return;
        }

        if(isNurse()==false){
            output = " Please Enter A Valid Nurse Id";
            NurseManagedAs();
            return;
        }

        if(room.getText().isEmpty()==true){
            output = " Please enter the room no......";
            NurseManagedAs();
            return;
        }
        if(shift.getText().isEmpty()==true) {
            output = " Please enter a sihft..Day or Night";
            NurseManagedAs();
            return;
        }

        DeleteNurse();

        String sql = "INSERT INTO NURSE_ASSIGN(EID,RID,SHIFT)VALUES('"+nid.getText()+"','"+room.getText()+"','"+shift.getText()+"')";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeQuery();
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        output = " Assigned....";
        NurseManagedAs();
    }

    @FXML
    void isassignedAction(ActionEvent event)
    {
        if(nid.getText().isEmpty()==true){
            output = " Please enter nurse id";
            NurseManagedAs();
            return;
        }

        if(isNurse()==false){
            output = " Please Enter A Valid Nurse Id";
            NurseManagedAs();
            return;
        }


        String sql = "SELECT * FROM NURSE_ASSIGN  WHERE EID="+nid.getText();

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            output = "";

            if(rs.next()){
                String []s = {"EID : ","ROOM_NO : ","SHIFT : "};
                //System.out.println("444444444444444   "+ rs.getString(1) + " oka");
                do
                {
                    //System.out.println("dfdfdff");
                    for(int i=1; i<=3; i++){
                        output += s[i-1] + rs.getString(i) + "\n";
                    }

                }while (rs.next());
            }
            else {
                output = " Isn't assigned yet....";
            }


            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        NurseManagedAs();


    }


    void setMain(Main main) throws java.io.IOException{

        this.main = main;

        ShowUI.stage.setScene(ShowUI.scene1);;
        ShowUI.stage.show();

    }

}
