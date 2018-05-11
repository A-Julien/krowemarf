package com.prckt.krowemarf.components.Posts;

import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.Access;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;

public class Posts extends UnicastRemoteObject implements _Posts {
    private ArrayList<_DefaultMessage> posts;
    private String name;
    private LinkedList<Access> access;

    public Posts(String name) throws RemoteException {
        super();
        this.name = name;
        this.posts = new ArrayList<>();
        this.access = new LinkedList<>();
    }

    @Override
    public ArrayList<_DefaultMessage> loadPost() throws RemoteException {
        return this.posts;
    }

    @Override
    public void addPost(_DefaultMessage post) throws RemoteException {
        this.posts.add(post);
    }

    @Override
    public void removePost(_DefaultMessage post) throws RemoteException {
        this.posts.remove(post);
    }


    @Override
    public String getName() throws RemoteException {
        return null;
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
