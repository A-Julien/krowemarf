package com.prckt.krowemarf.struct.Client;

import com.prckt.krowemarf.services.ClientListenerManagerServices._ClientListener;
import com.prckt.krowemarf.services.ClientListenerManagerServices._ClientListenerManager;
import com.prckt.krowemarf.services.ClientListenerManagerServices.£ClientListener;
import com.prckt.krowemarf.services.ComponentManagerSevices._ComponentManager;
import com.prckt.krowemarf.services.ConfigManagerServices.ConfigManager;
import com.prckt.krowemarf.services.UserManagerServices.User;
import com.prckt.krowemarf.services.UserManagerServices._User;
import com.prckt.krowemarf.services.UserManagerServices._UserManager;
import com.prckt.krowemarf.struct._Runnable;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * <h1>Client</h1>
 * Client class that gives access to the server components and allows connection to it.
 * <p>
 * _Runnable allows tu use the run() method to launch the server.
 *
 * @version 1.0-SNAPSHOT
 */
public final class Client extends UnicastRemoteObject implements _Runnable, _Client{
    private int port;
    private String adresse;
    private _User user = null;
    private _ComponentManager componentManager = null;
    private _UserManager userManager = null;
    private _ClientListener clientListener = null;
    private _ClientListenerManager clientListenerManager = null;
    private ArrayList<String> mpList = null;

    /**
     * Constructor how initialize client by given connection information
     * @param port port of the connection server
     * @param adresse address of the server
     * @throws RemoteException
     */
    public Client(int port, String adresse) throws RemoteException {
        super();
        this.port = port;
        this.adresse = adresse;
        this.mpList = new ArrayList<>();
    }


    /**
     * Allow client programme to retrieve the private conversation list that concerns them.
     * @return the private list
     */
    public ArrayList<String> getMpList() {
        return mpList;
    }

    /**
     * Allow client programme to add private messages
     * @param mpList list of private
     */
    public void addMp(String mpList) {
        this.mpList.add(mpList);
    }

    /**
     * This method create the user who will use the client.
     * This method must be call before run() method
     * @param login String login of user
     * @param password String password of user
     * @throws RemoteException
     */
    public void setCredential(String login, String password) throws RemoteException {
        this.user = new User(login,password);
    }

    /**
     * This method need to be call after the setCredential().
     * The credential verification and the connection with RMI distant object are insured by this method
     *
     * The method recovered all distant managers
     * @return return 0 if successful connection, 1 if credential problem.
     * @throws Exception
     */
    @Override
    public int run() throws Exception {

        System.setProperty("java.security.policy", ConfigManager.getConfig("securityManagerProp"));
        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager ( new RMISecurityManager() );
        }

        if(this.user == null) throw new Exception("You must call setCredential() methode before run client");


         Registry registry = LocateRegistry.getRegistry(this.port);
        try {

            Remote remoteUserManager = registry.lookup(this.buildRmiAddr(userManagerName, this.adresse));

            if (!(remoteUserManager instanceof _UserManager)) {
                System.out.println("Credential services unreachable");
                System.exit(1);
            }

            this.userManager = (_UserManager) remoteUserManager;



            if (!this.userManager.connect(user)) {
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

            if (remoteComponentManager instanceof _ComponentManager)
                this.componentManager = (_ComponentManager) remoteComponentManager;

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * This method allow client to add a clientListner for callback (use for mp messaging)
     * The initialisation of this method is not obligatory
     *
     * @param clientListener the clientListener
     * @throws RemoteException
     */
    public void initClientListner(£ClientListener clientListener) throws RemoteException {
        //this.clientListener = clientListener;
        this.clientListenerManager.addListener(this.getUser(), clientListener);
    }

    /**
     * Allows client to get the distant object of the componentManager and use components bind in him
     *
     * @return the distant component manager
     */
    public _ComponentManager getComponentManager() {
            return componentManager;
    }

    /**
     * Permit client to get all user connected to the rmi server by question the userMananger services
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<_User> getAllUsersConnected() throws RemoteException {
      return this.userManager.getUserConnected();
    }

    /**
     * Return the user connected to this client
     * @return user
     * @throws RemoteException
     */
    @Override
    public _User getUser() throws RemoteException {
        return user;
    }

    /**
     * Allows client to start private messenger with one or multiple users
     *
     * @param users ArrayList of user
     * @throws RemoteException
     * @throws SQLException
     */
    @Override
    public void newPrivateMessenger(ArrayList<_User> users) throws RemoteException, SQLException {
        String s = this.componentManager.addPrivateMessenger();
        this.clientListenerManager.initMp(s, users);

    }

    /**
     * This method stop properly the client.
     *
     * @throws RemoteException
     */
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
