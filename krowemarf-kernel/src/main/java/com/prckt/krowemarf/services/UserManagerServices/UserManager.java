package com.prckt.krowemarf.services.UserManagerServices;

import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices.SQLRequest;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager extends UnicastRemoteObject implements _UserManager {
    private ArrayList<_User> users;

    public UserManager() throws RemoteException {
        super();
        this.users = new ArrayList<>();
    }

    /**
     * Verifier si le login password en paramètre correspond à l'enregistrement en BD
     * @param login
     * @param password
     * @return
     */
    @Override
    public _User connect(String login, String password) throws RemoteException {
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect();
        List<List<Object>> list = null;
        try {
            list = SQLRequest.sqlToListObject(connexion, "SELECT password FROM User WHERE login = '" + login + "'", false);
        } catch (SQLException e) {
            return null;
        }
        dbConnectionManager.close(connexion);

        if (list.size() != 0 && password.equals(list.get(0).get(0).toString())) {
            _User user = new User(login, password);
            if (!this.contain(user)) {
                this.users.add(user);
            }
            return user;
        } else {
            return null;
        }
    }

    private boolean contain( _User uC) throws RemoteException {
        for (_User u:
             this.users) {
            if (u.getLogin().equals(uC.getLogin())) return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void disconnect(_User user) throws RemoteException{
        System.out.println("User " + user.getLogin() + " want disconnect");
        for (_User is: this.users) {
            if(is.getLogin().equals(user.getLogin())){
                this.users.remove(is);
                break;
            }
        }
        /*for (_User is:
                this.users) {
            System.out.println("user connected : " + is.getLogin());
        }*/
    }

    @Override
    public ArrayList<_User> getUserConnected() throws RemoteException{
        return users;
    }

    public void onNewPrivateMessenger(String idComponent, ArrayList<User> users){
        for (_User u:
                users) {

        }
    }
}
