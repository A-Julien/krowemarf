package com.prckt.krowemarf.struct;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.services.ClientListenerManagerServices.ClientListenerManager;
import com.prckt.krowemarf.services.ComponentManagerSevices.ComponentManager;
import com.prckt.krowemarf.services.ConfigManagerServices.ConfigManager;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices.UserManager;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.SQLException;

public final class Server extends £Server implements _Runnable {
    private int port;
    private String adresse;
    private ComponentManager componentManager;
    private UserManager userManager;
    private ClientListenerManager clientListenerManager;
    private Registry registry;
    private Connection dbConnection;


    public Server(int port, String adresse) throws RemoteException {
        this.port = port;
        this.adresse = adresse;
        LocateRegistry.createRegistry(this.port);
        this.registry = LocateRegistry.getRegistry(this.port);
        this.componentManager = new ComponentManager();
        this.userManager = new UserManager();
        this.clientListenerManager = new ClientListenerManager();
        this.dbConnection = new DbConnectionManager().connect("Server");
    }

    public void bindComponent(_Component component) throws RemoteException {
        this.componentManager.addComponent(component);
    }

    private void unBindComponent(_Component component){
        this.componentManager.removeComponent(componentManagerName);
    }

    @Override
    public int run() throws IOException, ClassNotFoundException {
        try {
            System.setProperty("java.security.policy", ConfigManager.getConfig("bdProp"));//"/Users/julien/Documents/MIAGE/Projet-Framework/krowemarf/krowemarf-kernel/src/main/java/com/prckt/krowemarf/services/security.policy");
            if (System.getSecurityManager() == null)
            {
                System.setSecurityManager ( new RMISecurityManager() );
            }

            if(!DbConnectionManager.tableExist(this.dbConnection,_Component.messengerTableName)) this.dbConnection.createStatement().executeUpdate(sqlTable(_Component.messengerTableName));
            if(!DbConnectionManager.tableExist(this.dbConnection,_Component.postTableName)) this.dbConnection.createStatement().executeUpdate(sqlTable(_Component.postTableName));
            if(!DbConnectionManager.tableExist(this.dbConnection,_Component.documentLibraryTableName))this.dbConnection.createStatement().executeUpdate(sqlTable(_Component.documentLibraryTableName));
            this.dbConnection.close();
        }catch (SQLException e) {
            System.out.println("Connection to bd failed");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            this.registry.rebind(this.buildRmiAddr(componentManagerName, this.adresse), this.componentManager);
            this.registry.rebind(this.buildRmiAddr(userManagerName,this.adresse), this.userManager);
            this.registry.rebind(this.buildRmiAddr(clientListenerManagerName,this.adresse), this.clientListenerManager);
            System.out.println("server run on port : " + this.port + " at " + this.buildRmiAddr(componentManagerName, this.adresse));
        } catch (RemoteException e) {
            System.out.println("Server failed to start");
            System.exit(1);
        }
        return 0 ;
    }

    @Override
    public void stop() throws RemoteException, NotBoundException, SQLException {
        for (_Component component:
             this.componentManager.getComponents()) {
            component.stop();
        }
        this.registry.unbind(this.buildRmiAddr(componentManagerName, this.adresse));
    }
}
