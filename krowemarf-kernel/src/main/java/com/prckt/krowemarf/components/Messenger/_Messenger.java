package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.UserManagerServices._User;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * The Messenger component allow client to send instant _DefaultMessage
 * to other client
 * @param <T> all message that extends _DefaultMessage
 */
public interface _Messenger<T extends _DefaultMessage> extends _Component {

    /**
     * This method allow client subscribe to the component.
     * When subscribe, client give to him the two callback (onReceive and onLeave)
     *
     * @param messengerClient Callback given by client
     * @param user user
     * @throws RemoteException
     */
    public void subscribe(_MessengerClient messengerClient, _User user) throws RemoteException;

    /**
     * Unsubscribe a user to this component
     * @param user user to unsubscribe
     * @throws RemoteException
     */
    public void unsubscribe(_User user) throws RemoteException;

    /**unsubscribe
     * This method allow client to post message on the component
     * When a user post a message, the server call all callback onRecieve of
     * each client who subscribed of this component
     *
     * @param user user who post the message
     * @param message the message
     * @throws RemoteException
     */
    public void postMessage(_User user, T message) throws RemoteException, SQLException;

    /**
     * This method allow client to save conversation to the bd.
     * To pass the message in RMI the message need to be serialized
     * @param message serialized message
     * @throws RemoteException
     */
    public void saveMessage(byte[] message) throws RemoteException;

    /**
     * This method allow client when he started to use a compenent that he use in the past to reload
     * all history of conversation
     *
     * @param user user who want reload history of the messenger component
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws RemoteException
     */
    public void reLoadMessage(_User user) throws IOException, SQLException, ClassNotFoundException, RemoteException;
}
