package com.prckt.krowemarf.services.UserManagerServices;

import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManager extends UnicastRemoteObject implements _UserManager {

    private static final long serialVersionUID = 1L;
    private ArrayList<_User> users;


    public UserManager() throws RemoteException {
        super();
        this.users = new ArrayList<>();
    }

    /**
     * Check if the user is allowed connected to the server
     * Verifier si le login password en paramètre correspond à l'enregistrement en BD
     *
     * @param user that must be checked
     * @return True if successful connection, False if denied
     */
    @Override
    public boolean connect(_User user) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("userManager");
        List<List<Object>> list;
        try {
            list = _DbConnectionManager.sqlToListObject(connexion, "SELECT password FROM User WHERE login = '" + user.getLogin() + "'", false);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.INFO,"Echec de la connexion de l'utilisateur : " + user.getLogin());
            return Boolean.FALSE;
        }
        dbConnectionManager.close(connexion);


        if (list.size() != 0 &&  user.compareHash(user,list.get(0).get(0).toString()) == 0){ //password.equals(list.get(0).get(0).toString())) {
            //_User user = new User(login, password);
            if (!this.contain(user)) {
                this.users.add(user);
            }
            Logger.getGlobal().log(Level.INFO,"Connexion de l'utilisateur : " + user.getLogin());
            return Boolean.TRUE;
        }

        Logger.getGlobal().log(Level.INFO,"Echec de la connexion de l'utilisateur : " + user.getLogin());
        return Boolean.FALSE;

    }


    /**
     * Private method to know if the are already register like connected
     * @param uC the user to compare
     * @return true if already register like connected, false if user are not connected
     * @throws RemoteException
     */
    private boolean contain( _User uC) throws RemoteException {
        for (_User u: this.users) {
            if (u.compareUser(uC) == 0) return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Disconnect user be removing him into the manager
     *
     * @param user user that will be disconnected
     * @throws RemoteException
     */
    @Override
    public void disconnect(_User user) throws RemoteException{
        for (_User is: this.users) {
            if(is.getLogin().equals(user.getLogin())){
                this.users.remove(is);
                Logger.getGlobal().log(Level.INFO,"Déconnexion de l'utilisateur : " + user.getLogin());
                break;
            }
        }
    }

    /**
     * Allows client to know all user connected
     * @return ArrayList of all user connected
     * @throws RemoteException
     */
    @Override
    public ArrayList<_User> getUserConnected() throws RemoteException{
        return users;
    }

}
