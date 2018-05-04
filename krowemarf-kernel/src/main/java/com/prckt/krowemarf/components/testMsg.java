package com.prckt.krowemarf.components;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

public class testMsg extends DefaultMessage{

    public int i;

    public testMsg(String content, String sender, int i) throws RemoteException {
        super(content, sender, new GregorianCalendar());
        this.i = i;
    }

    @Override
    public String toStrings() throws RemoteException {
        return super.toStrings() + " " + this.i;
    }
}
