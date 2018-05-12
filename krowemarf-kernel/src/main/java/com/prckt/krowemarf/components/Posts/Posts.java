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
import java.util.ArrayList;

//TODO un post est composé de Default message ayant un Titre
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
    public ArrayList<_DefaultMessage> loadPost() throws IOException, SQLException, ClassNotFoundException {
        ArrayList<_DefaultMessage> banane = new ArrayList<>();
        for (Object o : _DbConnectionManager.deSerializeJavaObjectFromDB(this.dbConnection, _Component.postTableName, this.getName())) {
            banane.add((_DefaultMessage) o);
        }
        return banane;
    }

    //TODO remoter cette metode in abstract class
    @Override
    public void addPost(byte[] message) throws RemoteException {
        if(SerializationUtils.deserialize(message) instanceof _DefaultMessage){
            try {
                _DbConnectionManager.serializeJavaObjectToDB(this.dbConnection, message, this.getName());
            } catch (SQLException e1) {
                System.out.println("Error save default message to bd");
                e1.printStackTrace();
            }
        }else{
            System.out.println("Can't save message in post, because is no good");
        }
    }

    //TODO tester cette methode
    @Override
    public void removePost(_DefaultMessage post) throws RemoteException, SQLException {
        String q = "DELETE FROM posts_krowemarf WHERE serialized_object = ?";
        PreparedStatement pstmt = this.dbConnection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
        pstmt.setObject(1, SerializationUtils.serialize(post));
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
