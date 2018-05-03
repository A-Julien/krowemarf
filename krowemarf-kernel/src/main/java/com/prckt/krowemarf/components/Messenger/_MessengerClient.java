package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components._Component;

import java.rmi.RemoteException;

public interface _MessengerClient extends _Component {
    public void displayMessage(String message) throws RemoteException;
}
