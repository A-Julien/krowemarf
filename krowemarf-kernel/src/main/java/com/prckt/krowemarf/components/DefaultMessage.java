package com.prckt.krowemarf.components;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.GregorianCalendar;

public class DefaultMessage extends UnicastRemoteObject implements _DefaultMessage {

    private String content;
    private String sender;
    private GregorianCalendar date;

    public DefaultMessage( String content, String sender, GregorianCalendar date) throws RemoteException {
        super();
        this.content = content;
        this.sender = sender;
        this.date = date;
    }

    public String getContent()throws RemoteException {
        return content;
    }

    public String getSender()throws RemoteException {
        return sender;
    }

    public String toStrings() throws RemoteException{
        return  this.sender + " : " + this.content ;
    }



}
