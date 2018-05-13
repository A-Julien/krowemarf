package com.prckt.krowemarf.components.Posts;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
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
                Logger.getGlobal().log(Level.SEVERE,"Error save default message to bd in component : " + this.getName());
                e1.printStackTrace();
            }
        }else{
            Logger.getGlobal().log(Level.INFO,"Impossible de sauvegarder ce message, n'est pas un _DefaultMessage dans le component : " + this.getName());
        }
    }

    @Override
    public void removePost(int id) throws RemoteException, SQLException {
        Logger.getGlobal().log(Level.INFO,"Suppression d'un message dans le composant post de nom : " + this.getName());
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
        Logger.getGlobal().log(Level.INFO,"Arrêt du component : " + this.getName());
        System.out.println("Component  " + this.getName() + " Shouting down");
        this.dbConnection.close();
    }


    /*
    public Right isPermission(Users user) {
    	for (int i = 0; i < access.size; i++) {
    		if (access.get(i).getUser == user) {
    			return access.get(i).getRight();
    		}
    	}
    	return null;
    }
    
    public void addAccess(Access a) {
    	access.add(a);
    }
    
    public void addAccess(Users user, String right) {
    	Access a = new Access(user, right);
    	access.add(a);
    }
    
    public void removeAccess(Access a) {
    	access.remove(a);
    }
    
    public void removeAccess(Users user, String right) {
    	Access a = new Access(user, right);
    	access.remove(a);
    }
    
    public LinkedList<Access> isAdmin() {
    	
    	LinkedList<Access> a = new LinkedList<Access>;
    	
    	for (int i = 0; i < access.size; i++) {
    		if (access.get(i).getRight == "admin") {
    			Access acc = new Access(access.get(i).getUser, access.get(i).getRight);
    			a.add(acc);
    		}
    	}
    	return a;
    }
    
    public LinkedList<Access> isUser() {
    	
    	LinkedList<Access> a = new LinkedList<Access>;
    	
    	for (int i = 0; i < access.size; i++) {
    		if (access.get(i).getRight == "user") {
    			Access acc = new Access(access.get(i).getUser, access.get(i).getRight);
    			a.add(acc);
    		}
    	}
    	return a;
    }*/
}
