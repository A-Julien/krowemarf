package com.prckt.krowemarf.services.ComponentManagerSevices;
import com.prckt.krowemarf.components._Component;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface _ComponentManager extends Remote {

    /**
     * Return a component relative at the passed argument
     * @param name name of the component that we want
     * @return the component
     * @throws RemoteException
     */
    public _Component getComponantByName(String name) throws RemoteException;

    /**
     * Auto add a component to the manager for the private message functionality and return his name
     * @return the name of the new private messenger
     * @throws RemoteException
     */
    public String addPrivateMessenger() throws RemoteException;
}
