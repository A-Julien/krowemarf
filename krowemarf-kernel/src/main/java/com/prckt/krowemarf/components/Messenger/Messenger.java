package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components.TypeMessage;
import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.components.£DefaultMessage;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices.User;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Messenger extends UnicastRemoteObject implements _Messenger{
    private Hashtable<_User, _MessengerClient> users;

    public String name;
    private  Connection dbConnection;


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
            Logger.getGlobal().log(Level.INFO,user.getLogin() +  " connected to chat : " + this.getName());
        }
    }


    @Override
    public void unsubscribe(_User user) throws RemoteException {
        if(this.users.containsKey(user)){
            this.users.remove(user);
            Logger.getGlobal().log(Level.INFO,user.getLogin() +  " unsubscribe to chat : " + this.getName());
            Enumeration<_MessengerClient> e = this.users.elements();
            while (e.hasMoreElements()){
                _MessengerClient clients = e.nextElement();
                clients.onLeave(user);
            }
        }
    }

    @Override
    public void postMessage(_User user, _DefaultMessage message) throws RemoteException {
        Logger.getGlobal().log(Level.INFO,user.getLogin() +  " send message : " + message.toStrings());
        Enumeration<_MessengerClient> e = this.users.elements();
        while (e.hasMoreElements()){
            _MessengerClient clients = e.nextElement();
            clients.onReceive(message);
        }


    }

    @Override
    public void saveMessage(byte[] message) throws RemoteException{
        if(SerializationUtils.deserialize(message) instanceof _DefaultMessage){
            try {
                _DbConnectionManager.serializeJavaObjectToDB(this.dbConnection, message, this.getName(), _Component.messengerTableName);
            } catch (SQLException e1) {
                Logger.getGlobal().log(Level.INFO, "Erreur lors de la sauvegarde du message");
                e1.printStackTrace();
            }
        }else{
            Logger.getGlobal().log(Level.INFO, "Impossible de sauvegarder le message, n'est pas un _DefaultMessage");
        }

    }

    @Override
    public void reLoadMessage(_User user) throws IOException, SQLException, ClassNotFoundException, RemoteException {
        ArrayList<Object> messages = _DbConnectionManager.deSerializeJavaObjectFromDB(this.dbConnection, _Component.messengerTableName, this.getName());
        //Enumeration<_MessengerClient> e = this.users.elements();

        for (Object o: messages) {
            this.users.get(user).onReceive((£DefaultMessage)o);
        }
        Logger.getGlobal().log(Level.INFO, user.getLogin() + " demande un reload du composant " + this.name);
    }


    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public void stop() throws SQLException, RemoteException {
        Logger.getGlobal().log(Level.INFO,"Arrêt du component : " + this.getName());
        _User messaging = new User(this.getName());
        this.postMessage(messaging, new TypeMessage("Your message" + this.getName() +" will be close in few second", messaging));
        this.dbConnection.close();
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
