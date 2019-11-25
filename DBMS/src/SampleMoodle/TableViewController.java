package SampleMoodle;

import java.util.List;

import SampleMoodle.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewController
{

    private Main main;
    @FXML
    private TableView tableView;

    ObservableList<User> data;

    @FXML
    private Button button;

    private boolean init = true;

    private void initializeColumns()
    {
        TableColumn<User, String> userNameCol = new TableColumn<>("username");
        userNameCol.setMinWidth(100);
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        //firstNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
//        firstNameCol.setOnEditCommit(
//                (TableColumn.CellEditEvent<SampleMoodle.User, String> t) ->
//                t.getTableView().getItems().get(t.getTablePosition().getRow()).setUserName(t.getNewValue())
//        );

        TableColumn<User, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setMinWidth(100);
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        //lastNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        /*lastNameCol.setOnEditCommit(
         (TableColumn.CellEditEvent<Person, String> t) ->
         t.getTableView().getItems().get(t.getTablePosition().getRow()).setLastName(t.getNewValue())
         );*/

        TableColumn<User, String> fullNameCol = new TableColumn<>("Full Name");
        fullNameCol.setMinWidth(200);
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        //emailCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
        /*emailCol.setOnEditCommit(
         (TableColumn.CellEditEvent<Person, String> t) ->
         t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmail(t.getNewValue())
         );*/

        tableView.getColumns().addAll(userNameCol, passwordCol, fullNameCol);
    }

    public void load()
    {
        if (init)
        {
            initializeColumns();
            init = false;
        }

        data = FXCollections.observableArrayList();

        List<List<String>> userDataList = new Users().getAllUsers();
        for (List<String> row : userDataList)
        {
            data.add(new User(row.get(0), row.get(1), row.get(2)));
        }

//        data = FXCollections.observableArrayList(
//                new SampleMoodle.User("Jacob", "2312", "Jacob Smith"),
//                new SampleMoodle.User("Isabella", "546t5", "Isabella Johnson"),
//                new SampleMoodle.User("Ethan", "fg565", "Ethan Williams"),
//                new SampleMoodle.User("Emma", "56564", "Emma Jones"),
//                new SampleMoodle.User("Michael", "gh5456", "Michael Brown")
//        );
        tableView.setEditable(true);
        tableView.setItems(data);
        //data.get(0).setFirstName("Jacob2");

    }

    @FXML
    void buttonAction(ActionEvent event)
    {
        try
        {
            main.showLoginPage();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    void setMain(Main main)
    {
        this.main = main;
    }

    public void refreshTable()
    {
        for (int i = 0; i < tableView.getColumns().size(); i++)
        {
            ((TableColumn) (tableView.getColumns().get(i))).setVisible(false);
            ((TableColumn) (tableView.getColumns().get(i))).setVisible(true);
        }
    }
}
