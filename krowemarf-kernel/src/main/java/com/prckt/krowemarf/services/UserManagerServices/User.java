package com.prckt.krowemarf.services.UserManagerServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class User extends UnicastRemoteObject implements _User {

    private String login;
    private String hash = null;

    public User(String login) throws RemoteException {
        super();
        this.login = login;
    }

    public User(String login, String hash) throws RemoteException {
        this.login = login;
        this.hash = hash;
    }

    @Override
    public String getToken() throws RemoteException{
        return hash;
    }

    @Override
    public String getLogin() throws RemoteException {
        return login;
    }
}
