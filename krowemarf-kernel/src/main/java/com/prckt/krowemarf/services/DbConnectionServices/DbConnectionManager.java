package com.prckt.krowemarf.services.DbConnectionServices;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionManager extends UnicastRemoteObject {

    private static String profile = "D:\\Travail\\Projets_git\\Krowemarf\\krowemarf-kernel\\src\\main\\java\\com\\prckt\\krowemarf\\services\\DbConnectionServices\\BD.properties";
    private Properties prop = new Properties();
    private String jdbcDriver;
    private String dbUrl;
    private String dbName;
    private String username, password;
    private Connection connection;

    public DbConnectionManager() throws RemoteException {
        super();
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

    public Connection connect(String entityName) {

        try {
            // Chargement du driver
            Class.forName("com.mysql.jdbc.Driver");

            // Connexion à la base de données
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.dbUrl + ":3306/" + this.dbName, this.username , this.password);

            System.out.println("Connection to bd open for : " + entityName);

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
    //https://stackoverflow.com/questions/2942788/check-if-table-exists?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    public static boolean tableExist(Connection conn, String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }

}
