package HDBMS;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DoctAppointments {



    public void ShowDoctApp(ArrayList<AppController.DoctApp> Slist){

        Stage primaryStage = new Stage();

        TableView<AppController.DoctApp> teamTable = new TableView<>();


        ObservableList<AppController.DoctApp> teams = FXCollections.observableArrayList(Slist);

        double w=800,h=500;
        teamTable.setItems(teams);
        teamTable.setMaxSize(w, h);
        teamTable.setMinSize(w, h);



        String CoumnNames[] = {"EID", "Name" , "Specialization" , "Type" , "Degree" , "Visit" , "App_Date" , "App_Time"};

        String CoumnPVFNames[] = {"did", "name" , "special" , "dtype" , "degree" , "visit" , "apdate" , "aptime"};


        for(int i=0; i<CoumnNames.length; i++){
            TableColumn<AppController.DoctApp, String> colName = new TableColumn<>(CoumnNames[i]);
            colName.setCellValueFactory(new PropertyValueFactory<AppController.DoctApp, String>(CoumnPVFNames[i]));
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

        primaryStage.setTitle("Doctors Appointment Detail.");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public  class DoctApp{

        private final SimpleStringProperty did;
        private final SimpleStringProperty name;
        private final SimpleStringProperty special;
        private final SimpleStringProperty dtype;
        private final SimpleStringProperty degree;
        private final SimpleStringProperty visit;
        private final SimpleStringProperty apdate;
        private final SimpleStringProperty aptime;

        public DoctApp(String di ,String name ,String sp , String dtype,String degree ,String visit ,String apdate ,String aptime) {

            this.did = new SimpleStringProperty(di);
            this.name = new SimpleStringProperty(name);
            this.special = new SimpleStringProperty(sp);
            this.dtype = new SimpleStringProperty(dtype);
            this.degree = new SimpleStringProperty(degree);
            this.visit = new SimpleStringProperty(visit);
            this.apdate = new SimpleStringProperty(apdate);
            this.aptime = new SimpleStringProperty(aptime);
        }

        public String getDid() {
            return did.get();
        }

        public String getName() {
            return name.get();
        }

        public String getSpecial() {
            return special.get();
        }

        public String getDtype() {
            return dtype.get();
        }

        public String getDegree() {
            return degree.get();
        }

        public String getVisit() {
            return visit.get();
        }

        public String getApdate() {
            return apdate.get();
        }

        public String getAptime() {
            return aptime.get();
        }
    }
}
