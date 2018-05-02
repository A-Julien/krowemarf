package com.prckt.krowemarf.struct.server;

import com.prckt.krowemarf.components.Component;
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
    private final String componentManagerName = "componentManager";

    public Server(int port, String adresse) throws RemoteException {
        this.port = port;
        this.adresse = adresse;
        LocateRegistry.createRegistry(this.port);
        this.registry = LocateRegistry.getRegistry(this.port);
        this.componentManager = new ComponentManager();
    }

    private String buildRmiAddr(String name){
        return "rmi://" + this.adresse + "/"+ name;
    }

    public void bindComponent(Component component) {
        this.componentManager.addComponent(component);
    }

    private void unBindComponent(Component component){
        this.componentManager.removeComponent(componentManagerName);
    }

    @Override
    public void run() throws RemoteException {
        this.registry.rebind(this.buildRmiAddr(componentManagerName), this.componentManager);
    }

    @Override
    public void stop() throws RemoteException, NotBoundException {
        this.registry.unbind(this.buildRmiAddr(componentManagerName));
    }
}
