package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

//TODO changer String en _User
public class Messenger extends UnicastRemoteObject implements _Messenger {
    private Hashtable<String, _MessengerClient> users;

    public String name;

    public Messenger(String name) throws RemoteException {
        super();
        this.name = name;
        this.users = new Hashtable<>();
    }

    @Override
    public void subscribe(_MessengerClient messengerClient, _User user) throws RemoteException {
        System.out.println("BANDE DE CONNARD");
        if(!this.users.containsKey(user.getLogin())){
            this.users.put(user.getLogin(), messengerClient);
            System.out.println(user.getLogin() +  "connected");
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
    public void postMessage(_User user, _DefaultMessage message) throws RemoteException {
        System.out.println(user.getLogin() + ":" + message.toStrings());
        Enumeration<_MessengerClient> e = this.users.elements();
        while (e.hasMoreElements()){
            _MessengerClient clients = e.nextElement();
            clients.onReceive(message);
        }
    }


    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public ArrayList<String> getUsersInTheRoom() {
        ArrayList<String> list = new ArrayList<>();
        for (Object o : this.users.entrySet()) {
            Map.Entry m = (Map.Entry) o;
            list.add((String) m.getKey());
        }
        return list;
    }
}
