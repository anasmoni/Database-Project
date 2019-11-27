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
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseController {

    private Main main;

    @FXML
    private Button reset;

    @FXML
    private Button buy;

    @FXML
    private Button back;

    @FXML
    private Button logout;

    @FXML
    private Button delete;

    @FXML
    private Button show;

    @FXML
    private TextField order;

    @FXML
    private TextField proname;

    @FXML
    private TextField price;

    @FXML
    private TextField unit;

    @FXML
    private DatePicker ddate;

    @FXML
    private DatePicker odate;

    @FXML
    private ComboBox view;

    @FXML
    private ComboBox companies;

    private Stage primaryStage;

    public void init(){

        primaryStage = new Stage();

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
        companies.getItems().addAll(DP);

        view.getItems().addAll("OrderNo","Product","Inventory");
    }

    @FXML
    void deleteAction(ActionEvent event) {


        if (odate.getValue()==null) {
            ShowUI.ShowPop("Please Pick A Date..... ", "Oops!!");
            return;
        }

        String  sql = "DELETE FROM PURCHASE WHERE ORDER_DATE < "+ "TO_DATE('" + odate.getValue() + "','YYYY-MM-DD')";

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
        ShowUI.ShowPop("Successfully Deleted..... ","WAH!!");
    }

    @FXML
    void resetAction(ActionEvent event){
        reset();
    }

    @FXML
    void buyAction(ActionEvent event){

        String sql="" , pno = order.getText();

        if(pno.isEmpty()==true) {

            sql = "SELECT MAX(PNO+1) FROM PURCHASE ";

            try {
                Connection con = new DBMSConnection().getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    pno = rs.getString(1);
                }
                pst.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            order.setText(pno);
        }

        if(proname.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Product Name..... ","Oops!!");
            return;
        }

        if(price.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Product Price..... ","Oops!!");
            return;
        }

        if(unit.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter No of Units..... ","Oops!!");
            return;
        }

        if(odate.getValue()==null){
            ShowUI.ShowPop("Please Pick Order_Date..... ","Oops!!");
            return;
        }

        if(ddate.getValue()==null){
            ShowUI.ShowPop("Please Pick Delivery_Date.... ","Oops!!");
            return;
        }

        if(companies.getValue()==null){
            ShowUI.ShowPop("Please Select Company... ","Oops!!");
            return;
        }

        sql = "SELECT * FROM MEDICINE WHERE MNAME = '" + proname.getText() + "'";

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                sql = "UPDATE MEDICINE SET PRICE = '" + price.getText() + "', UNITS = UNITS + " + unit.getText() +" WHERE MNAME = '" +
                        proname.getText() +"'";
            }
            else sql = "INSERT INTO MEDICINE (MNAME,PRICE,UNITS) VALUES('" + proname.getText() + "','"
                    + price.getText() +"','" +unit.getText()+"')";

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

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


        sql = "INSERT INTO PURCHASE (PNO,COMPANY,UNITS,ORDER_DATE,MNAME,DELIV_DATE,PRICE)\n" +
                " VALUES('" + pno + "','" + companies.getValue() + "','" + unit.getText() + "'," +
                "TO_DATE('" + odate.getValue() + "','YYYY-MM-DD')" + ",'" +
                proname.getText() + "'," + "TO_DATE('" + ddate.getValue() + "','YYYY-MM-DD'),'" + price.getText() + "')";

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


        ShowUI.ShowPop("Purchase Action Successfull..... ","WAH!!");
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
        primaryStage.close();
        order.setText("");
        price.setText("");
        unit.setText("");
        proname.setText("");
        init();
    }

    @FXML
    void viewAction(ActionEvent event)
    {
        primaryStage.close();

        if(view.getValue()==null){
            ShowUI.ShowPop("Please Select View Type..... ","Oops!!");
            return;
        }

        String vas = (String)view.getValue() ,sql="";

        if(vas.equals("OrderNo")){

            ArrayList<PurHist> Hlist = new ArrayList<>();

            if(order.getText().isEmpty()==false){

                sql = "SELECT P.MNAME,P.UNITS,P.PRICE,P.ORDER_DATE,P.DELIV_DATE,P.COMPANY FROM PURCHASE P " +
                        " WHERE P.PNO = " + order.getText();
                int bill=0;

                try
                {
                    Connection con = new DBMSConnection().getConnection();
                    PreparedStatement pst = con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();

                    while(rs.next()){

                        int u = Integer.parseInt(rs.getString(2));
                        int p = Integer.parseInt(rs.getString(3));
                        bill += u*p;

                        Hlist.add(new PurHist(rs.getString(1),rs.getString(2),rs.getString(3),
                                rs.getString(4),rs.getString(5),rs.getString(6)));
                    }

                    Hlist.add(new PurHist(null, null, null,
                            null, null, "Bill : " + Integer.toString(bill)));

                    pst.close();
                    con.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
                ShowPurchase(Hlist);

            }
            else {
                ShowUI.ShowPop("Please Enter Order No..... ","Oops!!");
                return;
            }
        }
        else if(vas.equals("Product")) {

            if (proname.getText().isEmpty()) {
                ShowUI.ShowPop("Please Enter Product Name..... ", "Oops!!");
                return;
            }

            ArrayList<PurHist> Hlist = new ArrayList<>();


            sql = "SELECT P.MNAME,P.UNITS,P.PRICE,P.ORDER_DATE,P.DELIV_DATE,P.COMPANY FROM PURCHASE P " +
                    " WHERE P.MNAME = '" + proname.getText() + "' ORDER BY P.ORDER_DATE";

            try {
                Connection con = new DBMSConnection().getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Hlist.add(new PurHist(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6)));
                }

                pst.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ShowPurchase(Hlist);
        }
        else {

            ArrayList<Inventory> Hlist = new ArrayList<>();

            sql = "SELECT * FROM MEDICINE ORDER BY UNITS";

            try {
                Connection con = new DBMSConnection().getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Hlist.add(new Inventory(rs.getString(1), rs.getString(3), rs.getString(2)));
                }

                pst.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ShowInventory(Hlist);
        }
    }

    void setMain(Main main) {

        this.main = main;
        init();
    }

    public void ShowInventory(ArrayList<Inventory> Slist){

        TableView<Inventory> teamTable = new TableView<>();


        ObservableList<Inventory> teams = FXCollections.observableArrayList(Slist);


        teamTable.setItems(teams);
        teamTable.setMaxSize(800, 500);
        teamTable.setMinSize(800, 500);

        String CoumnNames[] = {"Product", "Units" , "Price"};

        String CoumnPVFNames[] = {"prod", "unt" , "cost" };


        for(int i=0; i<CoumnNames.length; i++){
            TableColumn<Inventory, String> colName = new TableColumn<>(CoumnNames[i]);
            colName.setCellValueFactory(new PropertyValueFactory<Inventory, String>(CoumnPVFNames[i]));
            colName.setMinWidth(teamTable.getMaxWidth()/CoumnNames.length);
            teamTable.getColumns().add(colName);
        }

        GridPane center = new GridPane();

        center.add(teamTable, 0, 1, 5, 5);

        VBox right = new VBox(0);
        right.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setCenter(center);
        root.setRight(right);

        Scene scene = new Scene(root, 800, 500);

        primaryStage.setTitle("Inventory");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public  class Inventory{

        private final SimpleStringProperty prod;
        private final SimpleStringProperty unt;
        private final SimpleStringProperty cost;

        public Inventory(String prod ,String unt ,String cost) {

            this.prod = new SimpleStringProperty(prod);
            this.unt = new SimpleStringProperty(unt);
            this.cost = new SimpleStringProperty(cost);
        }

        public String getProd() {
            return prod.get();
        }

        public String getUnt() {
            return unt.get();
        }

        public String getCost() {
            return cost.get();
        }
    }



    public void ShowPurchase(ArrayList<PurHist> Slist){

        TableView<PurHist> teamTable = new TableView<>();


        ObservableList<PurHist> teams = FXCollections.observableArrayList(Slist);


        teamTable.setItems(teams);
        teamTable.setMaxSize(800, 500);
        teamTable.setMinSize(800, 500);

        String CoumnNames[] = {"Product", "Units" , "Price" , "Order_Date" , "Delivery_Date" , "Company"};

        String CoumnPVFNames[] = {"prod", "unt" , "cost" , "ord" , "deliv" , "comp" };


        for(int i=0; i<CoumnNames.length; i++){
            TableColumn<PurHist, String> colName = new TableColumn<>(CoumnNames[i]);
            colName.setCellValueFactory(new PropertyValueFactory<PurHist, String>(CoumnPVFNames[i]));
            colName.setMinWidth(teamTable.getMaxWidth()/CoumnNames.length);
            teamTable.getColumns().add(colName);
        }

        GridPane center = new GridPane();

        center.add(teamTable, 0, 1, 5, 5);

        VBox right = new VBox(0);
        right.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setCenter(center);
        root.setRight(right);

        Scene scene = new Scene(root, 800, 500);

        primaryStage.setTitle("Purchase History.");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public  class PurHist{
        //{"prod", "unt" , "cost" , "ord" , "deliv" , "comp" };
        private final SimpleStringProperty prod;
        private final SimpleStringProperty unt;
        private final SimpleStringProperty cost;
        private final SimpleStringProperty ord;
        private final SimpleStringProperty deliv;
        private final SimpleStringProperty comp;

        public PurHist(String prod ,String unt ,String cost , String ord,String deliv ,String comp) {

            this.prod = new SimpleStringProperty(prod);
            this.unt = new SimpleStringProperty(unt);
            this.cost = new SimpleStringProperty(cost);
            this.ord = new SimpleStringProperty(ord);
            this.deliv = new SimpleStringProperty(deliv);
            this.comp = new SimpleStringProperty(comp);
        }

        public String getProd() {
            return prod.get();
        }

        public String getUnt() {
            return unt.get();
        }

        public String getCost() {
            return cost.get();
        }

        public String getOrd() {
            return ord.get();
        }

        public String getDeliv() {
            return deliv.get();
        }

        public String getComp() {
            return comp.get();
        }
    }

}


