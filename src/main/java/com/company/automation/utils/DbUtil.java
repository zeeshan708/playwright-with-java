package com.company.automation.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

    private static String dbUrl;

    private static Connection dbConnection;

    public DbUtil(String hostname, String username, String password, String databaseName, String port) {
        this.dbUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + databaseName + "?user=" + username + "&password=" + password;
       if(dbConnection==null)
             dbConnection = getSQLDbConnection();
    }

    public static Connection getSQLDbConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection dbConnection = DriverManager.getConnection(dbUrl);
            LogUtils.info("Db Connection is successful - "+dbUrl);
            return dbConnection;
        } catch (ClassNotFoundException | SQLException ex) {
            LogUtils.info("Error while connecting to Database " + ex.getMessage());
        }
        return dbConnection;
    }

    public static Connection getDbConnection() {
        return dbConnection;
    }

    public String getDbUrl() {
        return this.dbUrl;
    }

}
