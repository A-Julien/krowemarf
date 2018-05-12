package com.prckt.krowemarf.components;


import com.prckt.krowemarf.services.UserManagerServices.User;
import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public abstract class £DefaultMessage extends UnicastRemoteObject implements _DefaultMessage {
    static final long serialVersionUID = 1L;
    private String content;
    private _User sender;
    private Date date;

    public £DefaultMessage(String content, _User sender, Date date) throws RemoteException {
        super();
        this.content = content;
        this.sender = new User(sender.getLogin());
        this.date = date;
    }


    public String getContent()throws RemoteException {
        return content;
    }

    public _User getSender()throws RemoteException {
        return sender;
    }

    public String toStrings() throws RemoteException{
        return  this.sender.getLogin() + " : " + this.content ;
    }

    public abstract String getDataToSave() throws RemoteException;

    public Date getDate() throws RemoteException {
        return date;
    }
}
