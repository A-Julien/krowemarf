package com.prckt.krowemarf.components.Posts;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.Access;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices.User;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Posts extends UnicastRemoteObject implements _Posts {

    private String name;
    private Connection dbConnection;
    private final String query =
            "INSERT INTO "+ _Component.postTableName +"(Composant_Name, serialized_object) VALUES (?, ?)";

    public Posts(String name) throws RemoteException {
        super();
        this.name = name;
        this.dbConnection = new DbConnectionManager().connect(this.getName());

    }

    @Override
    public HashMap<Integer,_DefaultMessage> loadPost() throws IOException, SQLException, ClassNotFoundException {
        return _DbConnectionManager.getHMPosts(this.dbConnection, this.getName());
    }

    @Override
    public void addPost(byte[] message) throws RemoteException {
        Logger.getGlobal().log(Level.INFO,"Ajout d'un message dans le composant post de nom : " + this.getName());
        if(SerializationUtils.deserialize(message) instanceof _DefaultMessage){
            try {
                _DbConnectionManager.serializeJavaObjectToDB(this.dbConnection, message, this.getName(), _Component.postTableName);
            } catch (SQLException e1) {
                System.out.println("Error save default message to bd");
                e1.printStackTrace();
            }
        }else{
            System.out.println("Can't save message in post, because is no good");
        }
    }

    @Override
    public void removePost(int id) throws RemoteException, SQLException {
        String q = "DELETE FROM posts_krowemarf WHERE id = ?";
        PreparedStatement pstmt = this.dbConnection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }


    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public void stop() throws SQLException, RemoteException {
        System.out.println("Component  " + this.getName() + " Shouting down");
        this.dbConnection.close();
    }


    public void addDbComponent() throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Posts");
        try {
            _DbConnectionManager.insertOrUpdateOrDelete(connexion, "INSERT INTO `Component` (`name`) VALUES ('" + this.getName() + "')");
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeDbComponent() throws RemoteException {
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Posts");
        try {
            _DbConnectionManager.insertOrUpdateOrDelete(connexion, "DELETE FROM `Component` WHERE `name` = '" + this.getName() + "'");
            connexion.close();
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public String isPermission(User user) throws RemoteException{

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("Posts");
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
        Connection connexion = dbConnectionManager.connect("Posts");
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
        Connection connexion = dbConnectionManager.connect("Posts");
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
        Connection connexion = dbConnectionManager.connect("Posts");
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
        Connection connexion = dbConnectionManager.connect("Posts");
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
