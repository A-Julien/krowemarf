package com.prckt.krowemarf.services.DbConnectionServices;

import com.prckt.krowemarf.services.ConfigManagerServices.ConfigManager;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Give access to the db for all server part
 */
public class DbConnectionManager extends UnicastRemoteObject {

    private static String profile = ConfigManager.getConfig("bdProp");
    private Properties prop = new Properties();
    private String jdbcDriver;
    private String dbUrl;
    private String dbName;
    private String username, password;
    private Connection connection;

    /**
     * By instantiate a dbConnection manager you are able to send end receive query from bd
     * The connection it initiate by get the bd information in BD.properties
     *
     * @throws RemoteException
     */
    public DbConnectionManager() throws RemoteException {
        super();
        try {
            prop = new Properties();
            prop.load(new FileInputStream(this.profile));
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
            e.printStackTrace();
            Logger.getGlobal().log(Level.INFO,"Impossible de trouver le fichier BD properties");
            return;
        } catch (IOException e) {
            Logger.getGlobal().log(Level.INFO,"IO Error in DBConnection manager");
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

    /**
     * This method return a connection to the bd
     *
     * @param entityName Pass a string of the name of entity that will use the connection (for the logs)
     * @return the connection to the bd
     */
    public Connection connect(String entityName) {

        try {
            // Chargement du driver
            Class.forName("com.mysql.jdbc.Driver");

            // Connexion à la base de données
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.dbUrl + ":3306/" + this.dbName, this.username , this.password);

            Logger.getGlobal().log(Level.INFO,"Connection to bd open for BD " + entityName);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return this.connection;
    }

    /**
     * Close the connection to the bd
     *
     * @param conn the connection that will be closed
     */
    public void close(Connection conn){
        try {
            this.connection.close();
            Logger.getGlobal().log(Level.INFO,"Fermeture d'une connexion à la BD");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode that check if a table exist by checking the tableName
     *
     * @param conn the connection for ask the bd
     * @param tableName the name to check
     * @return True if the table exist on BD, False if not
     * @throws SQLException
     */
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
