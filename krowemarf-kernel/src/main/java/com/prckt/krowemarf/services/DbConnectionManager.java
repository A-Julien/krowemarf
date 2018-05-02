package com.prckt.krowemarf.services;

import com.mysql.jdbc.Driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class DbConnectionManager {

    // init database constants
    private Connection connection;
    private Properties properties;

    public DbConnectionManager() {
        this.properties = new Properties();
        this.properties = this.getProperties();

    }

    // create properties
    private Properties getProperties() {
        if (properties == null) {
            try {
                properties.load(new
                        FileInputStream("BD.properties"));
            } catch (FileNotFoundException e) {
                System.err.println("FileNotFoundException: "
                        + e.getMessage());
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                System.err.println("IOException: " +
                        e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        return properties;
    }

    // connect database
    public Connection connect() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql:mysql-krowemarf.alwaysdata.net/?");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from javaTestDB.test_table;");
       // getResultSet(resultSet);
       // preparedStatement = connection.prepareStatement("insert into javaTestDB.test_table values (default,?)");
        //preparedStatement.setString(1,"insert test from java");
        //preparedStatement.executeUpdate();

        /*
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                this.connection = (Connection) DriverManager.getConnection("jdbc:mysql:mysql-krowemarf.alwaysdata.net");
                       // this.properties.getProperty("database.url"),
                       // this.properties);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }*/
        return connection;
    }

    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
