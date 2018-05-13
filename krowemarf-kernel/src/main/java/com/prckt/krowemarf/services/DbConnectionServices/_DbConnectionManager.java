package com.prckt.krowemarf.services.DbConnectionServices;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import org.apache.commons.lang3.SerializationUtils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Interface of dbManager
 * give access to tools method to facilitate access to the database
 *
 */
public interface _DbConnectionManager extends Remote {

    /**
     * This is build to serialize object and insert into database with the typical table schema
     * @param connection the connection to the bd
     * @param message the object will be serialized
     * @param name name of the component
     * @param tableName the table name
     * @throws SQLException
     * @throws RemoteException
     */
    public static void serializeJavaObjectToDB(Connection connection, byte[] message, String name, String tableName) throws SQLException, RemoteException {

        PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO "+ tableName +"(Composant_Name, serialized_object) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, name);
        pstmt.setObject(2, message);
        pstmt.executeUpdate();

        System.out.println("Java object serialized to database. Object: " + message);
    }

    /**
     * Deserialize an object from the data base
     *
     * @param connection the connection to the data base
     * @param tableName the table name
     * @param composentName the name of the composent
     * @return ArrayList of object
     * @throws RemoteException
     */
    public static ArrayList<Object> deSerializeJavaObjectFromDB(Connection connection, String tableName, String composentName) throws  RemoteException {
        ArrayList<Object> listMessage = new ArrayList<>();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                     "SELECT serialized_object FROM " + tableName +" WHERE Composant_Name = '"+ composentName +"'");
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                do{
                    listMessage.add(SerializationUtils.deserialize(resultSet.getBytes(1)));//ois.readObject());
                }while(resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  listMessage;
    }

    /**
     * Execute a query for insert in bd
     * @param connexion the connection to the bd
     * @param query the query
     * @throws SQLException
     */
    public static void insertOrUpdateOrDelete(Connection connexion, String query) throws SQLException {
        Statement statement = connexion.createStatement();

        System.out.println("Requête : " + query);
        /* Exécution d'une requête */
        statement.executeUpdate(query);
    }

    /**
     * Execute une requete sql et return la réponse en une liste d'object.
     * @param connexion
     * @param query
     * @param withColumnNames
     * @return List of list of object
     * @throws SQLException
     */
    public static List<List<Object>> sqlToListObject(Connection connexion, String query, boolean withColumnNames) throws SQLException {
        Statement statement = connexion.createStatement();

        System.out.println("Requête : " + query);
        /* Exécution d'une requête de lecture */
        ResultSet resultat = statement.executeQuery(query);

        // To contain all rows.
        List<List<Object>> list = new ArrayList<List<Object>>();
        ResultSetMetaData metaData = resultat.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        List<Object> columnNames = new ArrayList<Object>();

        // Get the column names
        if(withColumnNames){
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.add(metaData.getColumnLabel(column + 1));
            }
            list.add(columnNames);
        }

        while (resultat.next()) {
            List<Object> newRow = new ArrayList<Object>();

            for (int i = 1; i <= numberOfColumns; i++) {
                newRow.add(resultat.getObject(i));
            }

            list.add(newRow);
        }

        resultat.close();

        return list;
    }


    /**
     *
     * @param connection
     * @param composentName
     * @return
     * @throws RemoteException
     */
    public static HashMap<Integer,_DefaultMessage> getHMPosts(Connection connection, String composentName) throws  RemoteException {
        HashMap<Integer,_DefaultMessage> messagesInBD = new HashMap<>();

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "SELECT id, serialized_object FROM " + _Component.postTableName +" WHERE Composant_Name = '"+ composentName +"'");
            ResultSet resultSet = pstmt.executeQuery();

            if(resultSet.next()){
                do{
                    messagesInBD.put(resultSet.getInt(1),SerializationUtils.deserialize(resultSet.getBytes(2)));
                }while(resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  messagesInBD;
    }


}