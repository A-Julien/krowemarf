package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.components.£DefaultMessage;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices._User;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Messenger extends UnicastRemoteObject implements _Messenger{
    private Hashtable<_User, _MessengerClient> users;

    public String name;
    private  Connection dbConnection;
    private final String query =
            "INSERT INTO "+ _Component.messengerTableName +"(Composant_Name, serialized_object) VALUES (?, ?)";


    public Messenger(String name) throws RemoteException {
        super();
        this.name = name;
        this.users = new Hashtable<>();
        this.dbConnection = new DbConnectionManager().connect(this.getName());
    }

    @Override
    public void subscribe(_MessengerClient messengerClient, _User user) throws RemoteException {
        if(!this.users.containsKey(user)){
            this.users.put(user, messengerClient);
            System.out.println(user.getLogin() +  " connected to chat : " + this.getName());
        }
    }


    @Override
    public void unsubscribe(_User user) throws RemoteException {
        if(this.users.containsKey(user)){
            this.users.remove(user);
            System.out.println(user.getLogin() + " unsubscribe to chat : " + this.getName());
            Enumeration<_MessengerClient> e = this.users.elements();
            while (e.hasMoreElements()){
                _MessengerClient clients = e.nextElement();
                clients.onLeave(user);
            }
        }
    }

    @Override
    public void postMessage(_User user, _DefaultMessage message) throws RemoteException {
        System.out.println();
        System.out.println(user.getLogin() + ":" + message.toStrings());
        Enumeration<_MessengerClient> e = this.users.elements();
        // £DefaultMessage d = (£DefaultMessage) message;
        while (e.hasMoreElements()){
            _MessengerClient clients = e.nextElement();
            clients.onReceive(message);
        }


    }

    @Override
    public void saveMessage(byte[] message) throws RemoteException{
        if(SerializationUtils.deserialize(message) instanceof _DefaultMessage){
            try {
                _DbConnectionManager.serializeJavaObjectToDB(this.dbConnection, message, this.getName(),this.query);
            } catch (SQLException e1) {
                System.out.println("Error save default message to bd");
                e1.printStackTrace();
            }
        }else{
            System.out.println("Can't save message, because is no good");
        }

    }

    @Override
    public void reLoadMessage(_User user) throws IOException, SQLException, ClassNotFoundException, RemoteException {
       /* ArrayList<Object> messages = _DbConnectionManager.deSerializeJavaObjectFromDB(
                this.dbConnection,
                "messenger_krowemarf",
                this.getName());
        for (Object message : messages ) { postMessage(user,(_DefaultMessage) message); }
        */
        ArrayList<Object> banane = _DbConnectionManager.deSerializeJavaObjectFromDB(this.dbConnection, "messenger_krowemarf", this.getName());
        Enumeration<_MessengerClient> e = this.users.elements();

        for (Object o: banane) {
            this.users.get(user).onReceive((£DefaultMessage)o);
        }

        System.out.println(banane.size() + " reloaded for " + user.getLogin());
    }


    @Override
    public String getName() throws RemoteException {
        return this.name;
    }
/*
    public Right isPermission(Users user) {
    	for (int i = 0; i < access.size; i++) {
    		if (access.get(i).getUser == user) {
    			return access.get(i).getRight();
    		}
    	}
    	return null;
    }

    public void addAccess(Access a) {
    	access.add(a);
    }

    public void addAccess(Users user, String right) {
    	Access a = new Access(user, right);
    	access.add(a);
    }

    public void removeAccess(Access a) {
    	access.remove(a);
    }

    public void removeAccess(Users user, String right) {
    	Access a = new Access(user, right);
    	access.remove(a);
    }

    public LinkedList<Access> isAdmin() {

    	LinkedList<Access> a = new LinkedList<Access>;

    	for (int i = 0; i < access.size; i++) {
    		if (access.get(i).getRight == "admin") {
    			Access acc = new Access(access.get(i).getUser, access.get(i).getRight);
    			a.add(acc);
    		}
    	}
    	return a;
    }

    public LinkedList<Access> isUser() {

    	LinkedList<Access> a = new LinkedList<Access>;

    	for (int i = 0; i < access.size; i++) {
    		if (access.get(i).getRight == "user") {
    			Access acc = new Access(access.get(i).getUser, access.get(i).getRight);
    			a.add(acc);
    		}
    	}
    	return a;
    }*/
}
