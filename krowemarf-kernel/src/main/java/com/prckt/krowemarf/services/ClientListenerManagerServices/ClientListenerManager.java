package com.prckt.krowemarf.services.ClientListenerManagerServices;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manage all the clientLisner of each client.
 * He link a connected user to his clienListener
 *
 * The hashmap ensure that one client (user) can have just one clientListener
 */
public class ClientListenerManager extends UnicastRemoteObject implements _ClientListenerManager{
    private Hashtable<_User, _ClientListener> ListenerClient;

    /**
     * Initialize the clientListener
     * @throws RemoteException
     */
    public ClientListenerManager() throws RemoteException {
        super();
        ListenerClient = new Hashtable<>();
    }

    /**
     * Allows client to initialize the client listener.
     *
     * @param user the user how add the listener
     * @param clientListener the listener of the client
     * @throws RemoteException
     */
    @Override
    public void addListener(_User user, _ClientListener clientListener) throws RemoteException{
        this.ListenerClient.put(user,clientListener);
    }

    /**
     * Allows client to initialize private messenger
     *
     * @param idComponent the component id
     * @param usersTargets the list of the targeted user for the private message
     * @throws RemoteException
     * @throws SQLException
     */
    @Override
    public void initMp(String idComponent, ArrayList<_User> usersTargets) throws RemoteException, SQLException {
        for (_User u : usersTargets) {
            ListenerClient.get(u).onNewPrivateMessenger(idComponent);
        }
    }

    /**
     * Remove a client listner in the list
     *
     * @param user the user that that will be remove
     * @throws RemoteException
     */
    @Override
    public void removeListner(_User user) throws RemoteException {
        this.ListenerClient.remove(user);
        this.ListenerClient.forEach(new BiConsumer<_User, _ClientListener>() {
            @Override
            public void accept(_User user, _ClientListener clientListener) {
                try {
                    Logger.getGlobal().log(Level.INFO,"User " + user.getLogin() + " leave the private chat");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
