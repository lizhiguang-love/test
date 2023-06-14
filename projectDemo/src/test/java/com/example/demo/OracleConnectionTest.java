package com.example.demo;

import java.sql.*;

public class OracleConnectionTest {
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@10.4.3.169:1521/blmgprd", "loan_admin", "lipsadmin");
            String sql="select * from userLzg where age =? ";
            PreparedStatement psmt = connection.prepareStatement(sql);
//            Statement statement = connection.createStatement();
            psmt.setInt(1,50);
            System.out.println(sql);
            ResultSet resultSet = psmt.executeQuery();
//            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                String status = resultSet.getString("EMAIL");
                System.out.println(status);
                String ver = resultSet.getString("NAME");
                System.out.println(ver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
