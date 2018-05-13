package com.prckt.krowemarf.components;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Calendar;

/**
 * This class extend the abstract class £DefaultMessage to give an example of Messaging
 */
public class TypeMessage extends £DefaultMessage {

    public static final long serialVersionUID = 1L;


    /**
     * First constructor of this message.
     *
     * @param content String content
     * @param sender User Sender
     * @param date Date of the message
     * @throws RemoteException
     */
    public TypeMessage(String content, _User sender, Date date) throws RemoteException {
        super(content, sender, date);
    }

    /**
     * Second constructor of this message
     * The difference of the first, the date is auto calculated
     *
     * @param content
     * @param sender
     * @throws RemoteException
     */
    public TypeMessage(String content, _User sender) throws RemoteException {
        super(content, sender, Calendar.getInstance().getTime());
    }


    @Override
    public String toStrings() throws RemoteException {
        return super.toStrings() + " " + super.getDate();
    }

    @Override
    public String getDataToSave() throws RemoteException {
        return this.toStrings() + " " + super.getDate();
    }
}
