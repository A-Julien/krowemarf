package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.UserManagerServices._User;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface _Messenger<T extends _DefaultMessage> extends _Component {
    public void subscribe(_MessengerClient messengerClient, _User user) throws RemoteException;

    public void unsubscribe(_User user) throws RemoteException;

    public void postMessage(_User user, T message) throws RemoteException, SQLException;

    public void saveMessage(byte[] message) throws RemoteException;

    public void reLoadMessage(_User user) throws IOException, SQLException, ClassNotFoundException, RemoteException;
}
