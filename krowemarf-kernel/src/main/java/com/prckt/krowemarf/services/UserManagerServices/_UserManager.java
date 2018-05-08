package com.prckt.krowemarf.services.UserManagerServices;

import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices.SQLRequest;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface _UserManager extends Remote {
    public  _User connect(String login, String password) throws RemoteException;
    void disconnect(_User user) throws RemoteException;

    /**
     * hash un String
     * @param str
     * @return
     */
    public static String hash(String str) throws RemoteException{
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(str.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            str = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return str;
    }

    public static void createUser(String login, String password) throws RemoteException{
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect();
        try {
            SQLRequest.insertOrUpdateOrDelete(connexion, "INSERT INTO `User`(`login`, `password`) VALUES ('" + login + "','" + _UserManager.hash(password) + "')");
            connexion.close();
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }

    }

    ArrayList<_User> getUserConnected() throws RemoteException;
}
