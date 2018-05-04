package com.prckt.krowemarf.components.Posts;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface _Posts<T extends _DefaultMessage> extends _Component {
    public ArrayList<T> loadPost() throws RemoteException;
    public void addPost(T post) throws RemoteException;
    public void removePost(T post) throws RemoteException;
}
