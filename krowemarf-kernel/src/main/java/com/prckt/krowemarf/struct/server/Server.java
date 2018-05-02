package com.prckt.krowemarf.struct.server;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.services.ComponentManager;
import com.prckt.krowemarf.struct._Runnable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server implements _Runnable {
    private int port;
    private String adresse;
    private ComponentManager componentManager;
    private Registry registry;

    public Server(int port, String adresse) throws RemoteException {
        this.port = port;
        this.adresse = adresse;
        LocateRegistry.createRegistry(this.port);
        this.registry = LocateRegistry.getRegistry(this.port);
        this.componentManager = new ComponentManager();
    }

    public void bindComponent(_Component component) throws RemoteException {
        this.componentManager.addComponent(component);
    }

    private void unBindComponent(_Component component){
        this.componentManager.removeComponent(componentManagerName);
    }

    @Override
    public void run() throws RemoteException {
        this.registry.rebind(this.buildRmiAddr(componentManagerName, this.adresse), this.componentManager);
        System.out.println("server starting at : " + this.buildRmiAddr(componentManagerName, this.adresse));
    }

    @Override
    public void stop() throws RemoteException, NotBoundException {
        this.registry.unbind(this.buildRmiAddr(componentManagerName, this.adresse));
    }
}
