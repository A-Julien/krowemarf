package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._DefaultMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Hashtable;

public class Messenger extends UnicastRemoteObject implements _Messenger {
    private Hashtable<String, _MessengerClient> users;

    public String name;
    private LinkedList<Access> access;

    public Messenger(String name) throws RemoteException {
        super();
        this.name = name;
        this.users = new Hashtable<>();
        this.access = new LinkedList<Access>;
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
    }
}
