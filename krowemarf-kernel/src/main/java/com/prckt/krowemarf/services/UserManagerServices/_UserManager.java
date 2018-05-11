package com.prckt.krowemarf.services.UserManagerServices;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface _UserManager extends Remote {
    boolean connect(_User user) throws RemoteException;

    void disconnect(_User user) throws RemoteException;


    /*public static void createUser(String login, String password) throws RemoteException{
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("userManager");
        try {
            SQLRequest.insertOrUpdateOrDelete(connexion, "INSERT INTO `User`(`login`, `password`) VALUES ('" + login + "','" + _UserManager.hash(password) + "')");
            connexion.close();
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }

    }

    public static void deleteUser(String login) throws RemoteException{
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect();
        try {
            SQLRequest.insertOrUpdateOrDelete(connexion, "DELETE FROM `Access` WHERE `idUser` = (SELECT idUser FROM `User` WHERE `login` = " + login + ")");
            SQLRequest.insertOrUpdateOrDelete(connexion, "DELETE FROM `User` WHERE `login` = " + login);
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/

    ArrayList<_User> getUserConnected() throws RemoteException;
}
