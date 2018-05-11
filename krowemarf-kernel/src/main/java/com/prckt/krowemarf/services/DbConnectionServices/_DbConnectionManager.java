package com.prckt.krowemarf.services.DbConnectionServices;

import com.prckt.krowemarf.components._Component;
import org.apache.commons.lang3.SerializationUtils;

import java.io.ObjectInputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;

//TODO descendre les methodes dans la classe
public interface _DbConnectionManager extends Remote {

    public static void serializeJavaObjectToDB(Connection connection, byte[] message, String name) throws SQLException, RemoteException {

        PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO "+ _Component.messengerTableName +"(Composant_Name, serialized_object) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, name);
        pstmt.setObject(2, message);
        pstmt.executeUpdate();

        System.out.println("Java object serialized to database. Object: " + message);
    }

    //TODO ENLEVER NOM COLLONE EN DURE faire une abstract aves les nom dedans ?
    public static ArrayList<Object> deSerializeJavaObjectFromDB(Connection connection, String tableName, String composentName) throws  RemoteException {
        ArrayList<Object> listMessage = new ArrayList<>();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                     "SELECT serialized_object FROM " + tableName +" WHERE Composant_Name = '"+ composentName +"'");
            System.out.println("query ->" + "SELECT serialized_object FROM " + tableName +" WHERE Composant_Name = '"+ composentName +"'");
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            ObjectInputStream ois;
            do{
                listMessage.add(SerializationUtils.deserialize(resultSet.getBytes(1)));//ois.readObject());
            }while(resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  listMessage;
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