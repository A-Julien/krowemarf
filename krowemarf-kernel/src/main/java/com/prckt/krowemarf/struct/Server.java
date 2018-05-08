package com.prckt.krowemarf.struct;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.services.ClientListenerManagerServices.ClientListenerManager;
import com.prckt.krowemarf.services.ComponentManagerSevices.ComponentManager;
import com.prckt.krowemarf.services.UserManagerServices.UserManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server implements _Runnable {
    private int port;
    private String adresse;
    private ComponentManager componentManager;
    private UserManager userManager;
    private ClientListenerManager clientListenerManager;
    private Registry registry;


    public Server(int port, String adresse) throws RemoteException {
        this.port = port;
        this.adresse = adresse;
        LocateRegistry.createRegistry(this.port);
        this.registry = LocateRegistry.getRegistry(this.port);
        this.componentManager = new ComponentManager();
        this.userManager = new UserManager();
        this.clientListenerManager = new ClientListenerManager();
    }

    public void bindComponent(_Component component) throws RemoteException {
        this.componentManager.addComponent(component);
    }

    private void unBindComponent(_Component component){
        this.componentManager.removeComponent(componentManagerName);
    }

    @Override
    public int run() {
        try {
            this.registry.rebind(this.buildRmiAddr(componentManagerName, this.adresse), this.componentManager);
            this.registry.rebind(this.buildRmiAddr(userManagerName,this.adresse), this.userManager);
            this.registry.rebind(this.buildRmiAddr(clientListenerManagerName,this.adresse), this.clientListenerManager);
            System.out.println("server run on port : " + this.port + " at " + this.buildRmiAddr(componentManagerName, this.adresse));
        } catch (RemoteException e) {
            System.out.println("Can't start sever");
            return 1;
        }
        return 0 ;
    }

    @Override
    public void stop() throws RemoteException, NotBoundException {
        this.registry.unbind(this.buildRmiAddr(componentManagerName, this.adresse));
    }
}
