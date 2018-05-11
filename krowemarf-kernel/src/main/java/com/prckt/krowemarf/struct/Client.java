package com.prckt.krowemarf.struct;

import com.prckt.krowemarf.services.ClientListenerManagerServices._ClientListener;
import com.prckt.krowemarf.services.ClientListenerManagerServices._ClientListenerManager;
import com.prckt.krowemarf.services.ClientListenerManagerServices.£ClientListener;
import com.prckt.krowemarf.services.ComponentManagerSevices._ComponentManager;
import com.prckt.krowemarf.services.UserManagerServices._User;
import com.prckt.krowemarf.services.UserManagerServices._UserManager;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client extends UnicastRemoteObject implements _Runnable, _Client{
    private int port;
    private String adresse;
    private String login = null;
    private String password = null;
    private _User user;
    private _ComponentManager componentManager = null;
    private _UserManager userManager = null;
    private _ClientListener clientListener = null;
    private _ClientListenerManager clientListenerManager = null;
    private ArrayList<String> mpList = null;


    public Client(int port, String adresse) throws RemoteException {
        super();
        this.port = port;
        this.adresse = adresse;
        this.mpList = new ArrayList<>();
    }

    public ArrayList<String> getMpList() {
        return mpList;
    }

    public void addMp(String mpList) {
        this.mpList.add(mpList);
    }

    public void setCredential(String login, String password) throws RemoteException {
        this.login = login;
        this.password = _UserManager.hash(password);
    }

    @Override
    public int run() throws Exception {
        if(this.login == null){
            throw new Exception("You must call setCredential() methode before run client");
        }

        Registry registry = LocateRegistry.getRegistry(this.port);
        try {

            Remote remoteUserManager = registry.lookup(this.buildRmiAddr(userManagerName, this.adresse));

            if (!(remoteUserManager instanceof _UserManager)) {
                System.out.println("Credential services unreachable");
                System.exit(1);
            }

            this.userManager = (_UserManager) remoteUserManager;

            this.user = this.userManager.connect(this.login, this.password);

            if (this.user == null) {
                System.out.println("Unknow Login or Password");
                return 1;
            }

            Remote remoteClientListner = registry.lookup(this.buildRmiAddr(clientListenerManagerName, this.adresse));
            if (!(remoteClientListner instanceof _ClientListenerManager)) {
                System.out.println("Listenere Manager services unreachable");
                System.exit(1);
            }
            this.clientListenerManager = (_ClientListenerManager) remoteClientListner;

            Remote remoteComponentManager = registry.lookup(this.buildRmiAddr(componentManagerName, this.adresse));

            if (remoteComponentManager instanceof _ComponentManager){
                this.componentManager = (_ComponentManager) remoteComponentManager;
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void initClientListner(£ClientListener clientListener) throws RemoteException {
        //this.clientListener = clientListener;
        this.clientListenerManager.addListener(this.getUser(), clientListener);
    }

    public _ComponentManager getComponentManager() {
            return componentManager;
    }

    public ArrayList<_User> getAllUsersConnected() throws RemoteException {
      /*  ArrayList<_User> connectedUsers = this.userManager.getUserConnected();
        for(int i = 0; i < connectedUsers.size(); i++){
            if(this.user.getLogin().equals(connectedUsers.get(i).getLogin())){
                connectedUsers.remove(i);
                return connectedUsers;
            }
        }
        return connectedUsers;*/
      return this.userManager.getUserConnected();
    }

    @Override
    public _User getUser() throws RemoteException {
        return user;
    }

    @Override
    public void newPrivateMessenger(ArrayList<_User> users) throws RemoteException, SQLException {
        String s = this.componentManager.addPrivateMessenger();
        this.clientListenerManager.initMp(s, users);

    }

    @Override
    public void stop() throws RemoteException {
        this.componentManager = null;
        this.clientListenerManager.removeListner(this.getUser());
        this.userManager.disconnect(this.user);
        System.gc();
        System.runFinalization();
        System.exit(0);
    }
}
