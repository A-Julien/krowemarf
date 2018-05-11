package com.prckt.krowemarf.services.ClientListenerManagerServices;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.BiConsumer;

public class ClientListenerManager extends UnicastRemoteObject implements _ClientListenerManager{
    private Hashtable<_User, _ClientListener> ListenerClient;

    public ClientListenerManager() throws RemoteException {
        super();
        ListenerClient = new Hashtable<>();
    }

    @Override
    public void addListener(_User user, _ClientListener clientListener) throws RemoteException{
        this.ListenerClient.put(user,clientListener);
    }

    @Override
    public void initMp(String idComponent, ArrayList<_User> usersTargets) throws RemoteException, SQLException {
        for (_User u : usersTargets) {
            ListenerClient.get(u).onNewPrivateMessenger(idComponent);
        }
    }

    @Override
    public void removeListner(_User user) throws RemoteException {
        this.ListenerClient.remove(user);
        this.ListenerClient.forEach(new BiConsumer<_User, _ClientListener>() {
            @Override
            public void accept(_User user, _ClientListener clientListener) {
                try {
                    System.out.println("KJLUGGUIGUGUGUIKGIUOGIOHIGOIOLHIOGHIGOH : " + user.getLogin());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
