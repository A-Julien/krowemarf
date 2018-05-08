package com.prckt.krowemarf.components.Posts;

import com.prckt.krowemarf.components._DefaultMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Posts extends UnicastRemoteObject implements _Posts {
    private ArrayList<_DefaultMessage> posts;
    private String name;

    public Posts(String name) throws RemoteException {
        super();
        this.name = name;
        this.posts = new ArrayList<>();
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
}
