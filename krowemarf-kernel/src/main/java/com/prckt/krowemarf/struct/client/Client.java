package com.prckt.krowemarf.struct.client;

import com.prckt.krowemarf.services.ComponentManager;
import com.prckt.krowemarf.services._ComponentManager;
import com.prckt.krowemarf.struct._Runnable;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client implements _Runnable {
    private int port;
    private String adresse;
    private Registry registry;
    private _ComponentManager componentManager = null;

    public Client(int port, String adresse) {
        this.port = port;
        this.adresse = adresse;
    }

    @Override
    public void run() throws RemoteException {
        this.registry = LocateRegistry.getRegistry(this.port);
        try {
            
            Remote remote = registry.lookup(this.buildRmiAddr(componentManagerName, this.adresse));

            System.out.println("cc");

            if (remote instanceof _ComponentManager){
                System.out.println("cc");
                this.componentManager = (_ComponentManager) remote;
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public _ComponentManager getComponentManager() {
        return componentManager;
    }

    @Override
    public void stop() {
        this.componentManager = null;
        System.gc();
        System.runFinalization();
    }
}
