package com.prckt.krowemarf.services.UserManagerServices;

import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager extends UnicastRemoteObject implements _UserManager {
    private static final long serialVersionUID = 1L;
    private ArrayList<_User> users;

    public UserManager() throws RemoteException {
        super();
        this.users = new ArrayList<>();
    }

    /**
     * Verifier si le login password en paramètre correspond à l'enregistrement en BD
     * @param user
     * @return
     */
    @Override
    public boolean connect(_User user) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("userManager");
        List<List<Object>> list;
        try {
            list = _DbConnectionManager.sqlToListObject(connexion, "SELECT password FROM User WHERE login = '" + user.getLogin() + "'", false);
        } catch (SQLException e) {
            return Boolean.FALSE;
        }
        dbConnectionManager.close(connexion);

        for (_User u:
             this.users) {
            System.out.println("m -> " + u.getLogin());
        }
        
        
        if (list.size() != 0 &&  user.compareHash(user,list.get(0).get(0).toString()) == 0){ //password.equals(list.get(0).get(0).toString())) {
            //_User user = new User(login, password);
            if (!this.contain(user)) {
                this.users.add(user);
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }




    private boolean contain( _User uC) throws RemoteException {
        for (_User u: this.users) {
            if (u.compareUser(uC) == 0) return Boolean.TRUE;
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
