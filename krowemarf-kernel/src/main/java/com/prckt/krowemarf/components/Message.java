package com.prckt.krowemarf.components;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.GregorianCalendar;

public class Message extends UnicastRemoteObject implements _Message {
    private String content;
    private String user;
    private GregorianCalendar date;
    private String name;

    public Message() throws RemoteException {
    }

    public Message(String name , String content, String user, GregorianCalendar date) throws RemoteException {
        this.name = name;
        this.content = content;
        this.user = user;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public String getUser() {
        return user;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }
}
