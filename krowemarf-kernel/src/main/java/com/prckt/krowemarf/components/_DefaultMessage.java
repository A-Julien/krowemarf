package com.prckt.krowemarf.components;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * Interface of all messages
 */
public interface _DefaultMessage extends Remote, Serializable {

    public static final long serialVersionUID = 1L;

    /**
     * Return the content of the message un String
     *
     * @return String message
     * @throws RemoteException
     */
    public String getContent()throws RemoteException ;

    /**
     * return the sender of the message
     *
     * @return user who send the message
     * @throws RemoteException
     */
    public _User getSender()throws RemoteException ;

    /**
     * Return trace of message and sendre
     *
     * @return String
     * @throws RemoteException
     */
    public String toStrings() throws RemoteException;

    /**
     * Return the date of the message
     * @return Date
     * @throws RemoteException
     */
    public Date getDate() throws RemoteException;

    /**
     * this method define what log will be save
     * @return String
     * @throws RemoteException
     */
    public String getDataToSave() throws RemoteException;

}
