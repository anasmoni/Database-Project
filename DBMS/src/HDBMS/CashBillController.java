package HDBMS;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class CashBillController {

    private Main main;

    @FXML
    private Button logout;

    @FXML
    private Button back;

    @FXML
    private Button slip;

    @FXML
    private Button add;

    @FXML
    private Button ref;

    @FXML
    private TextField prid;

    @FXML
    private ComboBox name;

    @FXML
    private TextField pid;

    @FXML
    private ComboBox type;

    private int tot=0;

    private String new_presc;

    private Stage primaryStage = new Stage();

    private ArrayList<Slip> total = new ArrayList<>();


    public void init(){
        type.getItems().addAll("Visit","Test","Operation");

        String sql = "SELECT MAX(PRESCRIPTION_ID)+1 FROM PRESCRIPTION";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                prid.setText(rs.getString(1));
                new_presc = rs.getString(1);
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        ArrayList<String> ts  = new ArrayList<>();

        sql = "SELECT DISTINCT NAME FROM TESTS ORDER BY NAME";

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

        name.getItems().addAll(ts);
    }

    @FXML
    void logoutAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.scene1);
    }

    @FXML
    void resetAction(ActionEvent event)
    {
        reset();
    }

    public void reset(){

        pid.setText("");
        prid.setText("");
        primaryStage.close();
    }

    @FXML
    void backAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.CashHome);
    }

    void setMain(Main main) {

        this.main = main;
        init();
    }

    @FXML
    void addAction(ActionEvent event){

        if(type.getValue()==null){
            ShowUI.ShowPop("Please Select Bill type...","Oops!!");
            return;
        }

        String typ = (String)type.getValue();

        if(typ.equals("Test")){

            if(prid.getText().isEmpty()){

                String sql = "SELECT MAX(PRESCRIPTION_ID)+1 FROM PRESCRIPTION";

                try
                {
                    Connection con = new DBMSConnection().getConnection();
                    PreparedStatement pst = con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()){
                        prid.setText(rs.getString(1));
                        new_presc = rs.getString(1);
                    }

                    pst.close();
                    con.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }

            new_presc = prid.getText();



            if(name.getValue()==null){
                ShowUI.ShowPop("Please Select Test Name...","Oops!!");
                return;
            }

            int rno=0,cst=0;

            try
            {
                Connection con = new DBMSConnection().getConnection();

                CallableStatement pstmt = con.prepareCall("{call TEST_RECEIPT(?,?,?,?)}");
                pstmt.setString(1,(String)name.getValue());
                pstmt.setInt(2, Integer.parseInt(new_presc));
                pstmt.registerOutParameter(3, Types.INTEGER);
                pstmt.registerOutParameter(4, Types.INTEGER);

                pstmt.executeUpdate();

                rno = pstmt.getInt(3);
                cst = pstmt.getInt(4);

                tot += cst;

                total.add(new Slip((String)name.getValue(),Integer.toString(rno),Integer.toString(cst)));

                ShowUI.ShowPop("Succesfully Added.....","WAH!!");

                pstmt.close();
                con.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void slipAction(ActionEvent event)
    {
        if(type.getValue()==null){
            ShowUI.ShowPop("Please Select Bill type...","Oops!!");
            return;
        }

        String typ = (String)type.getValue();

        if(typ.equals("Test")==false) {
            if(pid.getText().isEmpty()){
                ShowUI.ShowPop("Please Enter PID...","Oops!!");
                return;
            }

            String sql = "";

            if(typ.charAt(0)=='V')sql = "SELECT (SELECT NAME FROM EMPLOYEE WHERE EID = A.EID)," +
                    " (SELECT RID FROM EMPLOYEE_ASSIGN WHERE EID = A.EID), (SELECT VISIT FROM DOCTORS WHERE EID=A.EID)\n" +
                    "FROM APPOINTMENT A WHERE A.PID=" + pid.getText() + " AND A.APP_DATE=SYSDATE;";

            else sql = "SELECT R.TYPE,R.RID,R.COST * ROUND(CHECK_OUT - CHECK_IN,0) FROM STAYS S JOIN ROOMS R ON S.RID = R.RID WHERE S.PID="
                    +pid.getText();

            try
            {
                Connection con = new DBMSConnection().getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while(rs.next()){
                    total.add(new Slip(rs.getString(1),rs.getString(2),rs.getString(3)));
                    //System.out.println(rs.getString(3) + " oka " + rs.getString(2) + " bal "+ rs.getString(1));
                    tot += Integer.parseInt(rs.getString(3));
                }

                sql = "SELECT O.NAME,O.RID, O.COST FROM OPERATIONS O JOIN APPOINTMENT A ON A.TYPE=O.NAME WHERE O.NAME<>'Visiting' AND A.PID="+pid.getText();

                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();

                while(rs.next()){
                    total.add(new Slip(rs.getString(1),rs.getString(2),rs.getString(3)));

                    tot += Integer.parseInt(rs.getString(3));
                }

                pst.close();
                con.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }



        }

        total.add(new Slip("","Total : ",Integer.toString(tot)));
        ShowSlip(total);
        tot=0;
        total.clear();
    }

    public void ShowSlip(ArrayList<Slip> Slist){

        TableView<Slip> teamTable = new TableView<>();


        ObservableList<Slip> teams = FXCollections.observableArrayList(Slist);

        double w=600,h=350;
        teamTable.setItems(teams);
        teamTable.setMaxSize(w, h);
        teamTable.setMinSize(w, h);

        String CoumnNames[] = {"Bill Name" , "Room No" , "Cost"};

        String CoumnPVFNames[] = {"name" , "room" , "cost"};


        for(int i=0; i<CoumnNames.length; i++){
            TableColumn<Slip, String> colName = new TableColumn<>(CoumnNames[i]);
            colName.setCellValueFactory(new PropertyValueFactory<Slip, String>(CoumnPVFNames[i]));
            colName.setMinWidth(teamTable.getMaxWidth()/CoumnNames.length);
            teamTable.getColumns().add(colName);
        }

        String tito = " Money Receipt of ";
        if(type.getValue().equals("Visit") || type.getValue().equals("Operation"))tito += "PID : "+ pid.getText();
        else tito += "Prescription ID : "+ prid.getText();

        Text txtHeader = new Text(tito + "\n");
        txtHeader.setFont(Font.font(20));
        txtHeader.setFill(Color.GREEN);

        GridPane center = new GridPane();

        center.add(txtHeader, 0, 0, 5, 1);
        center.add(teamTable, 0, 1, 5, 5);

        VBox right = new VBox(0);
        right.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setCenter(center);
        root.setRight(right);

        Scene scene = new Scene(root, w, h);

        primaryStage.setTitle("Bill");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public  class Slip{

        private final SimpleStringProperty name;
        private final SimpleStringProperty room;
        private final SimpleStringProperty cost;

        public Slip(String nm ,String u ,String c ) {

            this.name= new SimpleStringProperty(nm);
            this.room = new SimpleStringProperty(u);
            this.cost = new SimpleStringProperty(c);
        }

        public String getName() {
            return name.get();
        }

        public String getRoom() {
            return room.get();
        }

        public String getCost() {
            return cost.get();
        }
    }
}
