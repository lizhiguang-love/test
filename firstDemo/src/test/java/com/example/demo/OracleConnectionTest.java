package com.example.demo;

import java.sql.*;
import java.util.Date;

public class OracleConnectionTest {
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@10.4.3.169:1521/blmgprd", "loan_admin", "lipsadmin");
            String sql="update userlzg set age=? where id=? ";
            PreparedStatement psmt = connection.prepareStatement(sql);
//            Statement statement = connection.createStatement();
            psmt.setInt(1,66);
            psmt.setString(2,"dfasfdsa");


            System.out.println(sql);
            psmt.executeUpdate();
//            ResultSet resultSet = psmt.executeQuery();
//            ResultSet resultSet = statement.executeQuery(sql);

//            while (resultSet.next()){
//                String status = resultSet.getString("EMAIL");
//                System.out.println(status);
//                String ver = resultSet.getString("NAME");
//                System.out.println(ver);
////                System.out.println(resultSet.getInt(1));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
