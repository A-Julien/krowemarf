package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._DefaultMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Hashtable;

public class Messenger extends UnicastRemoteObject implements _Messenger {
    private Hashtable<String, _MessengerClient> users;

    public String name;

    public Messenger(String name) throws RemoteException {
        super();
        this.name = name;
        this.users = new Hashtable<>();
    }

    @Override
    public void subscribe(_MessengerClient user, String pseudo) throws RemoteException {
        if(!this.users.containsKey(pseudo)){
            this.users.put(pseudo, user);
            System.out.println(pseudo +  "connected");
        }
    }

    @Override
    public void unsubscribe(String pseudo) throws RemoteException {
        if(this.users.containsKey(pseudo)){
            this.users.remove(pseudo);
            System.out.println(pseudo + "unsubscribe");
        }
    }

    @Override
    public void postMessage(String pseudo, _DefaultMessage message) throws RemoteException {
        System.out.println(pseudo + ":" + message);
        Enumeration<_MessengerClient> e = this.users.elements();
        while (e.hasMoreElements()){
            _MessengerClient user = e.nextElement();
            user.onReceive(message);
        }
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }
}
