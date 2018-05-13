package com.prckt.krowemarf.components;


import com.prckt.krowemarf.services.UserManagerServices.User;
import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;


/**
 * This abstract class is a template of message that can be exchange by the Messenger/Post Component
 *
 */
public abstract class £DefaultMessage extends UnicastRemoteObject implements _DefaultMessage {

    static final long serialVersionUID = 1L;

    private String content;
    private _User sender;
    private Date date;


    £DefaultMessage(String content, _User sender, Date date) throws RemoteException {
        super();
        this.content = content;
        this.sender = new User(sender.getLogin());
        this.date = date;
    }


    /**
     * Return the content of the message un String
     *
     * @return String message
     * @throws RemoteException
     */
    public String getContent()throws RemoteException {
        return content;
    }

    /**
     * return the sender of the message
     *
     * @return user who send the message
     * @throws RemoteException
     */
    public _User getSender()throws RemoteException {
        return sender;
    }

    /**
     * Return trace of message and sendre
     *
     * @return String
     * @throws RemoteException
     */
    public String toStrings() throws RemoteException{
        return  this.sender.getLogin() + " : " + this.content ;
    }

    /**
     * this method define what log will be save
     * @return String
     * @throws RemoteException
     */
    public abstract String getDataToSave() throws RemoteException;

    /**
     * Return the date of the message
     * @return Date
     * @throws RemoteException
     */
    public Date getDate() throws RemoteException {
        return date;
    }
}
