package com.prckt.krowemarf.services.ClientListenerManagerServices;

import com.prckt.krowemarf.services.UserManagerServices._User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface _ClientListenerManager extends Remote{


    public void addListener(_User user, _ClientListener clientListener) throws RemoteException;

    public void initMp(String idComponent, ArrayList<_User> usersTargets) throws RemoteException, SQLException;

    public void removeListner(_User user) throws RemoteException;
}
