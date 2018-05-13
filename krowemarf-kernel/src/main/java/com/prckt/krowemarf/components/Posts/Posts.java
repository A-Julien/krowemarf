package com.prckt.krowemarf.components.Posts;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The post component is used to create deferred messages.
 * Connected to the database to add, or suppress messages.
 *
 */
public class Posts extends UnicastRemoteObject implements _Posts {

    private String name;
    private Connection dbConnection;


    /**
     * Constructor will be use by server to initiate un new component Post
     * @param name String name of the component
     * @throws RemoteException
     */
    public Posts(String name) throws RemoteException {
        super();
        this.name = name;
        this.dbConnection = new DbConnectionManager().connect(this.getName());

    }

    /**
     * This method call the database and mounts in memory all messages to give at the client
     * All message are identified by a id. The id is very util for de remove method
     *
     * @return HasMap of messages
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public HashMap<Integer,_DefaultMessage> loadPost() throws IOException, SQLException, ClassNotFoundException {
        return _DbConnectionManager.getHMPosts(this.dbConnection, this.getName());
    }

    /**
     * Add a messages to a post. This message need to be serialized to pass it in RMI.
     *
     * @param message byte[] serialized message
     * @throws RemoteException
     */
    @Override
    public void addMessage(byte[] message) throws RemoteException {
        Logger.getGlobal().log(Level.INFO,"Ajout d'un message dans le composant post de nom : " + this.getName());
        if(SerializationUtils.deserialize(message) instanceof _DefaultMessage){
            try {
                _DbConnectionManager.serializeJavaObjectToDB(this.dbConnection, message, this.getName(), _Component.postTableName);
            } catch (SQLException e1) {
                System.out.println("Error save default message to bd");
                e1.printStackTrace();
            }
        }else{
            System.out.println("Can't save message in post, because is no good");
        }
    }

    /**
     * Remove a message from post by id
     * @param id int id
     * @throws RemoteException
     * @throws SQLException
     */
    @Override
    public void removePost(int id) throws RemoteException, SQLException {
        String q = "DELETE FROM posts_krowemarf WHERE id = ?";
        PreparedStatement pstmt = this.dbConnection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    /**
     * Return name of the component
     * @return
     * @throws RemoteException
     */
    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    /**
     * Stop the component en close the bd connection
     * @throws SQLException
     * @throws RemoteException
     */
    @Override
    public void stop() throws SQLException, RemoteException {
        System.out.println("Component  " + this.getName() + " Shouting down");
        this.dbConnection.close();
    }

}
