package com.prckt.krowemarf.services.DbConnectionServices;

import com.prckt.krowemarf.components._Component;
import org.apache.commons.lang3.SerializationUtils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface _DbConnectionManager extends Remote {

    public static void serializeJavaObjectToDB(Connection connection, byte[] message, String name) throws SQLException, RemoteException {

        PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO "+ _Component.messengerTableName +"(Composant_Name, serialized_object) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, name);
        pstmt.setObject(2, message);
        pstmt.executeUpdate();

        System.out.println("Java object serialized to database. Object: " + message);
    }

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
     * Execute une requete
     * @param connexion
     * @param query
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
     * @return List<List<Object>>
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
}



















       /* ResultSet rs = pstmt.getGeneratedKeys();
        int serialized_id = -1;
        if (rs.next()) {
            serialized_id = rs.getInt(1);
        }
        rs.close();
        pstmt.close();*/

//List<List<Object>> list = SQLRequest.sqlToListObject(
       /* ObjectInputStream ois = null;
        byte[] t;
        for (List o : list) {
            t = SerializationUtils.serialize((Serializable) o.get(0));
            if (o.get(0) != null) {
                ois = new ObjectInputStream(new ByteArrayInputStream(t));
                listMessage.add((£DefaultMessage) ois.readObject());
            }
        }*/
// return listMessage;


      /*  PreparedStatement pstmt = connection
                .prepareStatement("SELECT serialized_message FROM messenger_krowemarf WHERE  id= ?");
        pstmt.setLong(1, 10);
        ResultSet rs = pstmt.executeQuery();
        rs.next();

        // Object object = rs.getObject(1);

        byte[] buf = rs.getBytes(1);
        ObjectInputStream objectIn = null;
        if (buf != null)
            objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

        Object deSerializedObject = objectIn.readObject();

        rs.close();
        pstmt.close();

        System.out.println("Java object de-serialized from database. Object: "
                + deSerializedObject + " Classname: "
                + deSerializedObject.getClass().getName());
        return deSerializedObject;*/