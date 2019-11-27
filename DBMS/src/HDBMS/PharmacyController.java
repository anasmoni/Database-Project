package HDBMS;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class PharmacyController {

    private Main main;

    @FXML
    private Button extract;

    @FXML
    private Button view;

    @FXML
    private Button logout;

    @FXML
    private Button ref;

    @FXML
    private Button back;

    @FXML
    private Button slip;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField units;

    private int tot=0;

    private Stage primaryStage = new Stage();

    private ArrayList<Slip> total = new ArrayList<>();

    @FXML
    void viewAction(ActionEvent event)
    {
        if(name.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Name...","Oops!!");
            return;
        }

        String sql = "SELECT * FROM MEDICINE WHERE INITCAP(MNAME) = INITCAP('" + name.getText() + "')" ;

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                name.setText(rs.getString(1));
                price.setText(rs.getString(2));
                units.setText(rs.getString(3));
            }
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    @FXML
    void extractAction(ActionEvent event){

        if(name.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Name...","Oops!!");
            return;
        }

        if(units.getText().isEmpty()){
            ShowUI.ShowPop("Please Enter Units...","Oops!!");
            return;
        }

        String sql = "SELECT * FROM MEDICINE WHERE INITCAP(MNAME) = INITCAP('" + name.getText() + "')" ;
        int un=0 , want=0;
        want = Integer.parseInt(units.getText());

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                un = Integer.parseInt(rs.getString(3));
                name.setText(rs.getString(1));
            }
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        if(un< want){
            ShowUI.ShowPop("We don't have " + units.getText() + " Units now...","Oops!!");
            return;
        }
        int t = want * Integer.parseInt(price.getText());
        tot += t;
        total.add(new Slip(name.getText(),units.getText(),Integer.toString(t)));

        sql = "UPDATE MEDICINE SET UNITS = " + Integer.toString(un-want) + " WHERE INITCAP(MNAME) = INITCAP('" + name.getText() + "')" ;

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

        ShowUI.ShowPop("Succesfully Extracted.....","WAH!!");
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
        name.setText("");
        units.setText("");
        price.setText("");
        primaryStage.close();
    }

    @FXML
    void backAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.PhrmHome);
    }

    void setMain(Main main) {

        this.main = main;
    }

    @FXML
    void slipAction(ActionEvent event)
    {
        total.add(new Slip("","Total : ",Integer.toString(tot)));
        ShowSlip(total);
        total.clear();
    }

    public void ShowSlip(ArrayList<Slip> Slist){

        TableView<Slip> teamTable = new TableView<>();


        ObservableList<Slip> teams = FXCollections.observableArrayList(Slist);

        double w=600,h=350;
        teamTable.setItems(teams);
        teamTable.setMaxSize(w, h);
        teamTable.setMinSize(w, h);

        String CoumnNames[] = {"Name" , "Units" , "Price"};

        String CoumnPVFNames[] = {"name" , "unit" , "cost"};


        for(int i=0; i<CoumnNames.length; i++){
            TableColumn<Slip, String> colName = new TableColumn<>(CoumnNames[i]);
            colName.setCellValueFactory(new PropertyValueFactory<Slip, String>(CoumnPVFNames[i]));
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

        Scene scene = new Scene(root, w, h);

        primaryStage.setTitle("Slip");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public  class Slip{

        private final SimpleStringProperty name;
        private final SimpleStringProperty unit;
        private final SimpleStringProperty cost;

        public Slip(String nm ,String u ,String c ) {

            this.name= new SimpleStringProperty(nm);
            this.unit = new SimpleStringProperty(u);
            this.cost = new SimpleStringProperty(c);
        }

        public String getName() {
            return name.get();
        }

        public String getUnit() {
            return unit.get();
        }

        public String getCost() {
            return cost.get();
        }
    }
}
