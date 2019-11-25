package SampleMoodle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class query {


    public static void main(String[] args) throws SQLException
    {
        // TODO code application logic here
        String sql = "SELECT * FROM NURSE_ASSIGN";

        try
        {
            Connection con = new OracleDBMS().getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            //pst.setString(1, "1505001");
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                System.out.println(rs.getString("EID") + " , " + rs.getString(2) + " , " + rs.getString(3));
            }
            pst.close();
            con.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
//        try
//        {
//            Connection con = new OracleDBUtil().getConnection();
//            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setString(1, "1505002");
//            ResultSet rs = pst.executeQuery();
//            while (rs.next())
//            {
//                System.out.println(rs.getString(1) + " , " + rs.getString(2) + " , " + rs.getString(3));
//            }
//            pst.close();
//            con.close();
//        } catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
    }

}
