package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components.TypeMessage;
import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.components.£DefaultMessage;
import com.prckt.krowemarf.services.Access;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices.User;
import com.prckt.krowemarf.services.UserManagerServices._User;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class Messenger extends UnicastRemoteObject implements _Messenger{
    private Hashtable<_User, _MessengerClient> users;

    public String name;
    private  Connection dbConnection;


    public Messenger(String name) throws RemoteException {
        super();
        this.name = name;
        this.users = new Hashtable<>();
        this.dbConnection = new DbConnectionManager().connect(this.getName());
    }

    @Override
    public void subscribe(_MessengerClient messengerClient, _User user) throws RemoteException {
        if(!this.users.containsKey(user)){
            this.users.put(user, messengerClient);
            System.out.println(user.getLogin() +  " connected to chat : " + this.getName());
        }
    }


    @Override
    public void unsubscribe(_User user) throws RemoteException {
        if(this.users.containsKey(user)){
            this.users.remove(user);
            System.out.println(user.getLogin() + " unsubscribe to chat : " + this.getName());
            Enumeration<_MessengerClient> e = this.users.elements();
            while (e.hasMoreElements()){
                _MessengerClient clients = e.nextElement();
                clients.onLeave(user);
            }
        }
    }

    @Override
    public void postMessage(_User user, _DefaultMessage message) throws RemoteException {
        System.out.println();
        System.out.println(user.getLogin() + ":" + message.toStrings());
        Enumeration<_MessengerClient> e = this.users.elements();
        // £DefaultMessage d = (£DefaultMessage) message;
        while (e.hasMoreElements()){
            _MessengerClient clients = e.nextElement();
            clients.onReceive(message);
        }


    }

    @Override
    public void saveMessage(byte[] message) throws RemoteException{
        if(SerializationUtils.deserialize(message) instanceof _DefaultMessage){
            try {
                _DbConnectionManager.serializeJavaObjectToDB(this.dbConnection, message, this.getName(), _Component.messengerTableName);
            } catch (SQLException e1) {
                System.out.println("Error save default message to bd");
                e1.printStackTrace();
            }
        }else{
            System.out.println("Can't save message, because is no good");
        }

    }

    @Override
    public void reLoadMessage(_User user) throws IOException, SQLException, ClassNotFoundException, RemoteException {
        ArrayList<Object> messages = _DbConnectionManager.deSerializeJavaObjectFromDB(this.dbConnection, _Component.messengerTableName, this.getName());
        //Enumeration<_MessengerClient> e = this.users.elements();

        for (Object o: messages) {
            this.users.get(user).onReceive((£DefaultMessage)o);
        }

        System.out.println(messages.size() + " reloaded for " + user.getLogin());
    }


    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public void stop() throws SQLException, RemoteException {
        System.out.println("Component  " + this.getName() + " Shouting down");
        _User messaging = new User(this.getName());
        this.postMessage(messaging, new TypeMessage("Your message" + this.getName() +" will be close in few second", messaging));
        this.dbConnection.close();
    }

    public void addDbComponent() throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Messenger");
        try {
            _DbConnectionManager.insertOrUpdateOrDelete(connexion, "INSERT INTO `Component` (`name`) VALUES ('" + this.getName() + "')");
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeDbComponent() throws RemoteException {
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Messenger");
        try {
            _DbConnectionManager.insertOrUpdateOrDelete(connexion, "DELETE FROM `Component` WHERE `name` = '" + this.getName() + "'");
            connexion.close();
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public String isPermission(User user) throws RemoteException{

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Messenger");
        String right = null;

        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT permission FROM `User` NATURAL JOIN `Access` WHERE `login` = '" + user.getLogin() + "'", false);
            if(!list.isEmpty()) {
                right = list.get(0).get(0).toString();
            }
            connexion.close();

        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }

        return right;
    }

    public void addAccess(Access access) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Messenger");
        try {
            List<List<Object>> idUser = _DbConnectionManager.sqlToListObject(connexion, "SELECT idUser FROM `User` WHERE `login` = '" + access.getUser().getLogin() + "'", false);
            List<List<Object>> idComponent = _DbConnectionManager.sqlToListObject(connexion, "SELECT idComponent FROM `Component` WHERE `name` = '" + this.getName() + "'", false);
            _DbConnectionManager.insertOrUpdateOrDelete(connexion, "INSERT INTO `Access` VALUES ('" + idUser.get(0).get(0).toString() + "','" + idComponent.get(0).get(0).toString() + "','" + access.getRight() + "')");
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAccess(User user, String right) throws RemoteException {
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Messenger");
        try {
            List<List<Object>> idUser = _DbConnectionManager.sqlToListObject(connexion, "SELECT idUser FROM `User`  WHERE `login` = '" + user.getLogin() + "'", false);
            List<List<Object>> idComponent = _DbConnectionManager.sqlToListObject(connexion, "SELECT idComponent FROM `Component` WHERE `name` = '" + this.getName() + "'", false);
            _DbConnectionManager.insertOrUpdateOrDelete(connexion, "INSERT INTO `Access` VALUES ('" + idUser.get(0).get(0).toString() + "','" + idComponent.get(0).get(0).toString() + "','" + right + "')");
            connexion.close();
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public void removeAccess(Access access) throws RemoteException {
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Messenger");
        try {
            List<List<Object>> idUser = _DbConnectionManager.sqlToListObject(connexion, "SELECT idUser FROM `User` WHERE `login` = '" + access.getUser().getLogin() + "'", false);
            List<List<Object>> idComponent = _DbConnectionManager.sqlToListObject(connexion, "SELECT idComponent FROM `Component` WHERE `name` = '" + this.getName() + "'", false);
            if(idUser.size()>0) {
                if(idUser.get(0).size()>0) {
                    _DbConnectionManager.insertOrUpdateOrDelete(connexion, "DELETE FROM `Access` WHERE `idUser` = " + Integer.parseInt( idUser.get(0).get(0).toString() ) + " AND `idComponent` = " + Integer.parseInt( idComponent.get(0).get(0).toString() ) );
                }
            }
            connexion.close();
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public void removeAccess(User user) throws RemoteException {
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Messenger");
        try {
            List<List<Object>> idUser = _DbConnectionManager.sqlToListObject(connexion, "SELECT idUser FROM `User`  WHERE `login` = '" + user.getLogin() + "'", false);
            List<List<Object>> idComponent = _DbConnectionManager.sqlToListObject(connexion, "SELECT idComponent FROM `Component` WHERE `name` = '" + this.getName() + "'", false);
            if(idUser.size()>0) {
                if(idUser.get(0).size()>0) {
                    _DbConnectionManager.insertOrUpdateOrDelete(connexion, "DELETE FROM `Access` WHERE `idUser` = " + Integer.parseInt(idUser.get(0).get(0).toString()) + " AND `idComponent` = " + Integer.parseInt(idComponent.get(0).get(0).toString()));
                }
            }
            connexion.close();
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> isAdmin() throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Posts");
        ArrayList<String> admin = new ArrayList<>();

        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT login FROM `User` NATURAL JOIN `Access` WHERE `permission` = 'admin'", false);

            for (int i = 0; i<list.size(); i++) {
                admin.add(new String(list.get(i).get(0).toString()));
            }
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public ArrayList<String> isUser() throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Posts");
        ArrayList<String> user = new ArrayList<>();

        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT login FROM `User` NATURAL JOIN `Access` WHERE `permission` = 'user'", false);

            for (int i = 0; i<list.size(); i++) {
                user.add(list.get(i).get(0).toString());
            }
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
