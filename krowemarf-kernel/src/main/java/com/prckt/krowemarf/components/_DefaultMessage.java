package com.prckt.krowemarf.components;

import java.rmi.RemoteException;

public interface _DefaultMessage extends _Component {

    public String getContent()throws RemoteException ;

    public String getSender()throws RemoteException ;

    public String toStrings() throws RemoteException;
}
