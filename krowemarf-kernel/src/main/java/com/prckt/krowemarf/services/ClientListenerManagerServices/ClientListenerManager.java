package com.prckt.krowemarf.services.ClientListenerManagerServices;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;

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
    public void initMp(String idComponent, ArrayList<_User> usersTargets) throws RemoteException {
        System.out.println("DJFOQESFPOEFJSE" + idComponent);
        for (_User u : usersTargets) {
            System.out.println("avant ");
            for (_User ut:
                 usersTargets) {
                System.out.println("test : " + ut.getLogin());
            }
            ListenerClient.get(u).onNewPrivateMessenger(idComponent);
            System.out.println("apres");
        }
    }
}
