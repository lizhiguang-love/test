package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleConnectionTest {
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@10.4.3.239:1521/BEAUAT", "loan_admin", "lipsadmin");
            String sql="select * from acct_offcr";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String status = resultSet.getString("status");
                System.out.println(status);
                String ver = resultSet.getString("ver");
                System.out.println(ver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
