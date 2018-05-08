package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface _Messenger<T extends _DefaultMessage> extends _Component {
    public void subscribe(_MessengerClient messengerClient, _User user) throws RemoteException;
    public void unsubscribe(String pseudo) throws RemoteException;
    public void postMessage(_User user, T message) throws RemoteException;
    public ArrayList<String> getUsersInTheRoom() throws RemoteException;

}
