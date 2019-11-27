package HDBMS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.ArrayList;

public class AddNurseController {

   private Main main;

    @FXML
    private Button view;

    @FXML
    private Button edit;

    @FXML
    private Button pback;

    @FXML
    private Button add;

    @FXML
    private Button resetp;

    @FXML
    private Button delete;

    @FXML
    private Button signout;

    @FXML
    private TextField eid;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;

    @FXML
    private TextField age;

    @FXML
    private DatePicker jdate;

    @FXML
    private ComboBox ntype;

    @FXML
    private ComboBox dept;

    @FXML
    private TextField email;

    @FXML
    private TextField sal;

    @FXML
    private TextField tdept;

    @FXML
    private TextField jtype;

    @FXML
    private TextField degree;


    public void init(){

        ArrayList<String> DP  = new ArrayList<>();

        String sql = "SELECT DISTINCT DEPT FROM ROOMS";

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

        dept.getItems().addAll(DP);

        DP.clear();


        sql = "SELECT DISTINCT TYPE FROM NURSE ORDER BY TYPE ASC";

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

        ntype.getItems().addAll(DP);

    }

    boolean check(String TABLE , String VAL , String COL){
        boolean ret=true;

        String sql = "SELECT COUNT(*) FROM " + TABLE +  " WHERE " + COL +"='"+ VAL +"'";


        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                if(rs.getString(1).equals("0")==false)ret = false;
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return ret;
    }

    @FXML
    void deleteAction(ActionEvent event) {

        if (eid.getText().isEmpty() == true) {
            ShowUI.ShowPop("Please Enter EID..... ", "Oops!!");
            return;
        }
        String ed = eid.getText();

        try {
            CallableStatement callSt = null;
            Connection con = new DBMSConnection().getConnection();
            callSt = con.prepareCall("{?= call DELETE_NURSE(?)}");
            callSt.setInt(2,Integer.parseInt(ed));
            callSt.registerOutParameter(1, Types.VARCHAR);
            callSt.execute();
            String output = callSt.getString(1);

            if(output.equals("Nurse") == false){
                ShowUI.ShowPop("It's a " + output +"'s ID....","Oops!!");
                ed="-";
            }



            callSt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            ed="-";
        }

        if(ed.equals("-")==false){
            ShowUI.ShowPop("Successfully Deleted..... ","WAH!!");
        }


        /*
        String  sql = "SELECT PROFESSION FROM EMPLOYEE WHERE EID = "+ ed;

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){

                if(rs.getString(1).equals("Nurse") == false){
                    ShowUI.ShowPop("It's a " + rs.getString(1)+"'s ID....","Oops!!");
                    return;
                }
            }
            else {
                ShowUI.ShowPop("EID Dosen't exist ....","Oops!!");
                return;
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        sql = "DELETE FROM NURSE WHERE EID = "+ ed;

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

        sql = "DELETE FROM EMPLOYEE WHERE EID = "+ ed;

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
        */
    }

    @FXML
    void viewAction(ActionEvent event)
    {

        if(eid.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter EID..... ","Oops!!");
            return;
        }

        String sql = "SELECT E.EID , E.NAME, E.ADDRESS, E.PHONE, E.AGE , TO_CHAR(E.JOIN_DATE,'DD-MM-YYYY')," +
                " E.EMAIL,E.SALARY,E.DEPT,E.DEGREE, E.PROFESSION, N.TYPE FROM EMPLOYEE E JOIN NURSE N ON E.EID=N.EID WHERE E.EID = "+ eid.getText();

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){

                if(rs.getString(11).equals("Nurse") == false){
                    ShowUI.ShowPop("It's a " + rs.getString(11)+"'s ID....","Oops!!");
                    return;
                }

                eid.setText(rs.getString(1));
                name.setText(rs.getString(2));
                address.setText(rs.getString(3));
                phone.setText(rs.getString(4));
                age.setText(rs.getString(5));

                jdate.setValue(ShowUI.LOCAL_DATE(rs.getString(6)));
                email.setText(rs.getString(7));
                sal.setText(rs.getString(8));
                tdept.setText(rs.getString(9));
                degree.setText(rs.getString(10));
                jtype.setText(rs.getString(11));
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void resetpAction(ActionEvent event){
        reset();
    }

    @FXML
    void addAction(ActionEvent event){

        String sql = "SELECT MAX(EID+1) FROM EMPLOYEE " , ed="";


        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                ed = rs.getString(1);
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        eid.setText(ed);

        if(name.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Name..... ","Oops!!");
            return;
        }

        if(address.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Address..... ","Oops!!");
            return;
        }

        if(phone.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Phone No..... ","Oops!!");
            return;
        }

        if(age.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Age..... ","Oops!!");
            return;
        }

        if(email.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Email..... ","Oops!!");
            return;
        }

        if(sal.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter Salary..... ","Oops!!");
            return;
        }


        if(ntype.getValue()==null){
            ShowUI.ShowPop("Please Select Speciality..... ","Oops!!");
            return;
        }

        if(jdate.getValue()==null){
            ShowUI.ShowPop("Please Select Join_Date..... ","Oops!!");
            return;
        }

        if(dept.getValue()==null){
            ShowUI.ShowPop("Please Select Department..... ","Oops!!");
            return;
        }

        sql = "INSERT INTO EMPLOYEE (EID,NAME,ADDRESS,PHONE,AGE,PROFESSION,SALARY,GENDER,JOIN_DATE,EMAIL,DEPT,DEGREE)\n" +
                " VALUES('" + ed + "','" + name.getText() + "','" + address.getText() + "','" + phone.getText() + "','" +
                age.getText() + "','Nurse','" + sal.getText() + "','Female'," +
                "TO_DATE('" + jdate.getValue() + "','YYYY-MM-DD')" + ",'" + email.getText() + "','" + dept.getValue() + "','" + degree.getText() + "')";

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

        sql = "INSERT INTO NURSE (EID,TYPE) VALUES('" + ed + "','" + ntype.getValue() + "')";

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



        ShowUI.ShowPop("Successfully Added..... ","WAH!!");
    }

    @FXML
    void editAction(ActionEvent event)
    {
        if(eid.getText().isEmpty()==true){
            ShowUI.ShowPop("Please Enter EID..... ","Oops!!");
            return;
        }

        String sql ,SAL,DEPT,SPEC,DEG ,EID , GSAL , GDEPT , GDEG, GSPEC;

        GSAL = sal.getText();

        if(dept.getValue()==null)GDEPT="";
        else GDEPT = (String) dept.getValue();

        if(ntype.getValue()==null)GSPEC="";
        else GSPEC = (String) ntype.getValue();

        sql = SAL = DEPT = DEG = SPEC = "";
        EID = eid.getText();

        sql = "SELECT E.SALARY,E.DEPT, N.TYPE FROM EMPLOYEE E JOIN NURSE N ON E.EID=N.EID WHERE E.EID = " + EID ;

        try
        {
            Connection con = new DBMSConnection().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                SAL = rs.getString(1);
                DEPT = rs.getString(2);
                SPEC = rs.getString(3);
            }

            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }


        boolean yup=false;

        if(GSAL.isEmpty()==false ) {
            if (GSAL.equals(SAL) == false) {

                sql = "UPDATE EMPLOYEE  SET SALARY = '" + GSAL + "'";
                yup = true;
            }
        }
        if( GDEPT.isEmpty()==false ) {
            if (GDEPT.equals(DEPT) == false) {

                if (yup) sql = ", DEPT = '" + GDEPT + "'";
                else sql = "UPDATE EMPLOYEE  SET DEPT = '" + GDEPT + "'";
                yup = true;
            }
        }


        if(yup){

            sql += " WHERE EID = " + EID;

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

        }
        boolean yey=false;

        if( GSPEC.isEmpty()==false ) {
            if (GSPEC.equals(SPEC) == false) {
                sql = "UPDATE NURSE  SET TYPE = '" + GSPEC + "' WHERE EID = " + EID;
                yey=true;
            }
        }


        if(yup || yey){

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

        }

        if(yup){
            ShowUI.ShowPop("Succesfully Updated.....","WAH!!");
        }
    }




    @FXML
    void pbackAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.AdminHomeScene);
    }

    @FXML
    void signoutAction(ActionEvent event)
    {
        reset();
        ShowUI.stage.setScene(ShowUI.scene1);
    }


    void reset(){

        eid.setText("");
        name.setText("");
        address.setText("");
        phone.setText("");
        age.setText("");
        tdept.setText("");
        sal.setText("");
        email.setText("");
        jtype.setText("");
        degree.setText("");
    }

    void setMain(Main main) {

        this.main = main;
        init();
    }

}

