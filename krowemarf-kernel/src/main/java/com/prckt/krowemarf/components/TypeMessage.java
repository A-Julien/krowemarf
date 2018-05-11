package com.prckt.krowemarf.components;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Calendar;

public class TypeMessage extends £DefaultMessage {

    public TypeMessage(String content, _User sender, Date date) throws RemoteException {
        super(content, sender, date);
    }

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
