package HDBMS;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ShowUI extends ManageNurse
{

    public static Scene scene1;
    public static Scene scene2;
    public static Scene appo;
    public static Scene DPCscene;
    public static Scene Recp_Profile;
    public static Scene Admin_Profile;
    public static Scene Doct_Home;
    public static Scene AppUpdateScene;
    public static Scene AdminHomeScene;
    public static Scene AddEmpScene;
    public static Scene AddNurse;
    public static Scene AddDoct;
    public static Scene PurchaseScene;
    public static Scene Companies;
    public static Scene Services;
    public static Scene Rooms;
    public static Scene Tests;
    public static Scene Operations;
    public static Scene Prescribe;
    public static Scene Patient;

    public static Scene PhrmHome;
    public static Scene PhrmProf;
    public static Scene Pharmacy;

    public static Scene CashHome;
    public static Scene CashProf;
    public static Scene CashBill;


    public static Scene PathoHome;
    public static Scene PathoProf;
    public static Scene PathoTest;

    public static Stage stage;

    public static String UserName;
    public static String PassWord;
    public static String EID;

    public static void ShowPop(String s , String h){

        Text txtHeader = new Text(h);
        txtHeader.setFont(Font.font(17));
        if(h.charAt(0)=='O')txtHeader.setFill(Color.RED);
        else txtHeader.setFill(Color.GREEN);


        Label label = new Label(s);
        Stage stage = new Stage();
        stage.setMinHeight(233);
        stage.setMinWidth(400);

        stage.setMaxHeight(233);
        stage.setMaxWidth(400);

        FlowPane flowPane = new FlowPane(10,10);
        flowPane.setAlignment(Pos.CENTER);


        Scene scene = new Scene(flowPane , 400,233);
        stage.setScene(scene);

        Button ok = new Button("OK");


        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        flowPane.getChildren().addAll(txtHeader,label,ok);

        stage.show();
    }

    public static boolean isHe(String id, String TableName , String ColName){

        String sql = "SELECT COUNT(*)  FROM " +  TableName + " WHERE " + ColName + " = '"+ id + "'";
        boolean ret=true;
        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                if(rs.getString(1).equals("0"))ret = false;
            }
            else ret=false;

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return ret;
    }

    public static LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
}
