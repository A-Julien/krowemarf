package com.prckt.krowemarf.components.Posts;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * The post component is used to create deferred messages.
 * Connected to the database to add, or suppress messages.
 *
 *
 * @param <T> All Messages that extends the interface _DefaultMessage
 */
public interface _Posts<T extends _DefaultMessage> extends _Component {
    /**
     * This method call the database and mounts in memory all messages to give at the client
     * All message are identified by a id. The id is very util for de remove method
     *
     * @return HasMap of messages
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     *
     */
    public HashMap<Integer,T> loadPost() throws IOException, SQLException, ClassNotFoundException;

    /**
     * Add a messages to a post. This message need to be serialized to pass it in RMI.
     *
     * @param message byte[] serialized message
     * @throws RemoteException
     */
    public void addMessage(byte[] message) throws RemoteException;

    /**
     * Remove a message from post by id
     * @param id int id
     * @throws RemoteException
     * @throws SQLException
     */
    void removePost(int id) throws RemoteException, SQLException;
}
