package com.prckt.krowemarf.services.DbConnectionServices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

public class DbConnectionManager {

    private static String profile = "D:\\Travail\\Projets_git\\Krowemarf\\krowemarf-kernel\\src\\main\\java\\com\\prckt\\krowemarf\\services\\DbConnectionServices\\BD.properties";
    private Properties prop = new Properties();
    private String jdbcDriver;
    private String dbUrl;
    private String dbName;
    private String username, password;
    private Connection connection;

    public DbConnectionManager() {
        try {
            prop = new Properties();
            prop.load(new FileInputStream(this.profile));
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        this.jdbcDriver = prop.getProperty("jdbc.driver");
        this.dbUrl = prop.getProperty("database.url");
        this.dbName = prop.getProperty("database.name");
        this.username = prop.getProperty("database.username");
        this.password = prop.getProperty("database.password");
    }

    public Connection connect() {

        try {
            // Chargement du driver
            Class.forName("com.mysql.jdbc.Driver");

            // Connexion à la base de données
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.dbUrl + ":3306/" + this.dbName, this.username , this.password);

            System.out.println("La connexion à la base de données est ouverte");

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return this.connection;
    }

    public void close(Connection conn){
        try {
            this.connection.close();
            System.out.println("La connexion à la base de données est close()");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
