package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Define callback when receive and leave the messenger component
 *
 * @param <T> the message that extends _DefaultMessage
 */
public interface _MessengerClient<T extends _DefaultMessage> extends Remote {

    /**
     * Callback that which will be redefined in client when he receive a message
     * @param message the message received
     * @throws RemoteException
     */
    public void onReceive(T message) throws RemoteException;

    /**
     * Callback that which will be redefined when a client leave
     * @param user
     * @throws RemoteException
     */
    public void onLeave(_User user) throws RemoteException;
}
