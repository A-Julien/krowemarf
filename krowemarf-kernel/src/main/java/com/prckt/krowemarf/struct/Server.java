package com.prckt.krowemarf.struct;

import com.prckt.krowemarf.components.DefaultMessage;
import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.services.ClientListenerManagerServices.ClientListenerManager;
import com.prckt.krowemarf.services.ComponentManagerSevices.ComponentManager;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices.UserManager;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.GregorianCalendar;

public class Server extends £Server implements _Runnable {
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
        this.dbConnection = new DbConnectionManager().connect();
    }

    //TODO ta
    public void bindComponent(_Component component) throws RemoteException {
        this.componentManager.addComponent(component);
    }

    private void unBindComponent(_Component component){
        this.componentManager.removeComponent(componentManagerName);
    }

    @Override
    public int run() throws SQLException, IOException, ClassNotFoundException {

        if(!DbConnectionManager.tableExist(this.dbConnection,this.messengerTableName)){
          this.dbConnection.createStatement().executeUpdate(sqlTable(this.messengerTableName));
        }

        DefaultMessage d =  new DefaultMessage("coucou","bite", new GregorianCalendar());

        DbConnectionManager.serializeJavaObjectToDB(this.dbConnection,d,"INSERT INTO messenger_krowemarf(Composant_Name, serialized_message) VALUES (?, ?)");
        
        
       DefaultMessage r = (DefaultMessage) DbConnectionManager.deSerializeJavaObjectFromDB(this.dbConnection);
        System.out.println(r.toStrings());
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
    public void stop() throws RemoteException, NotBoundException, SQLException {
        this.registry.unbind(this.buildRmiAddr(componentManagerName, this.adresse));
        this.dbConnection.close();
    }
}
