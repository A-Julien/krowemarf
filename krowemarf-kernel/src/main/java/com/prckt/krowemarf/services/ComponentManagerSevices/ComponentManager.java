package com.prckt.krowemarf.services.ComponentManagerSevices;

import com.prckt.krowemarf.components.Messenger.Messenger;
import com.prckt.krowemarf.components.Messenger._Messenger;
import com.prckt.krowemarf.components._Component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;

/**
 * This class allow to manage component
 * The HashMap ensure that the component name are unique
 */
public class ComponentManager extends UnicastRemoteObject implements _ComponentManager {
    private HashMap<String, _Component> components;
    private int mpNumber = 1;

    /**
     * Constructor of manager
     * initialize the services
     * @throws RemoteException
     */
    public ComponentManager() throws RemoteException {
        super();
        this.components = new HashMap<>();
    }

    /**
     * This method permit to add component
     * @param c the component to add
     * @throws RemoteException
     */
    public void addComponent(_Component c) throws RemoteException {
        this.components.put(c.getName(), c);
    }

    /**
     * Return a component relative at the passed argument
     * @param name name of the component that we want
     * @return the component
     * @throws RemoteException
     */
    @Override
    public _Component getComponantByName(String name) throws RemoteException {
        return this.components.get(name);
    }

    /**
     * Auto add a component to the manager for the private message functionality and return his name
     * @return the name of the new private messenger
     * @throws RemoteException
     */
    @Override
    public String addPrivateMessenger() throws RemoteException {
        System.out.println("adding private messenger");
        _Messenger messenger = new Messenger("chat" + this.mpNumber);
        this.mpNumber++;
        this.components.put(messenger.getName(), messenger);
        return messenger.getName();
    }


    /**
     * Remove a component to the manager
     * @param name the name of the component
     */
    public void removeComponent(String name) {
        this.components.remove(name);
    }

    /**
     * Return a collection of component
     * @return collection of component
     */
    public Collection<_Component> getComponents() {
        return components.values();
    }
}


