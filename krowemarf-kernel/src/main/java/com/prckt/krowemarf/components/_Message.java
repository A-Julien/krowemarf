package com.prckt.krowemarf.components;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

public interface _Message extends _Component {

    public String getContent() throws RemoteException;

    public String getUser() throws RemoteException;

    public GregorianCalendar getDate() throws RemoteException;

    public void setContent(String content) throws RemoteException;

    public void setUser(String user) throws RemoteException;

    public void setDate(GregorianCalendar date) throws RemoteException;

    public void setName(String name) throws RemoteException;

}
