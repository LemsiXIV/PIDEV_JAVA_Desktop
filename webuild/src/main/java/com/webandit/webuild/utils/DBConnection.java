package com.webandit.webuild.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnection {

    public String url = "jdbc:mysql://localhost:3306/webuild2";
    public String login = "root";
    public String pwd = "";
    Connection cnx;
    public static DBConnection instance;

    private DBConnection() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connection established!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
            System.out.println("No connection established");
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    //3eme etape
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
