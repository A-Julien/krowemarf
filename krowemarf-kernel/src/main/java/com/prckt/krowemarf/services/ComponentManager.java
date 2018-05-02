package com.prckt.krowemarf.services;

import com.prckt.krowemarf.components._Component;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;

public class ComponentManager extends UnicastRemoteObject implements _ComponentManager {
    private HashMap<String,_Component> components;

    public ComponentManager() throws RemoteException {
        super();
        this.components = new HashMap<>();
    }

    public void addComponent(_Component c) throws RemoteException {
        this.components.put(c.getName() ,c);
    }

    public _Component getComponantByName(String name) {
        return this.components.get(name);
    }

    public void removeComponent(String name){
        this.components.remove(name);
    }

    public HashMap<String, _Component> getComponents() {
        return components;
    }


    public Iterator getIterator(){
        return this.components.entrySet().iterator();
    }


}
