package com.prckt.krowemarf.components.Posts;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface _Posts<T extends _DefaultMessage> extends _Component {
    public ArrayList<T> loadPost() throws IOException, SQLException, ClassNotFoundException;
    public void addPost(byte[] message) throws RemoteException;
    public void removePost(T post) throws RemoteException;
}
