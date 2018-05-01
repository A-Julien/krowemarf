package com.prckt.krowemarf.services;

import com.mysql.jdbc.Connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionManager {

    // init database constants
    private Connection connection;
    private Properties properties;

    public DbConnectionManager(String PROPFILE, Connection connection, Properties properties) {
        this.connection = connection;
        this.properties = this.getProperties(PROPFILE);

    }

    // create properties
    private Properties getProperties(String PROPFILE) {
        if (properties == null) {
            try {
                properties.load(new
                        FileInputStream(PROPFILE));
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
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(this.properties.getProperty("jdbc.driver"));
                this.connection = (Connection) DriverManager.getConnection(
                        this.properties.getProperty("database.url"),
                        this.properties);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
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
