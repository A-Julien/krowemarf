package com.prckt.krowemarf.components.DocumentLibrary;

import com.prckt.krowemarf.components.DocumentLibrary.FileTypes.Audio;
import com.prckt.krowemarf.components.DocumentLibrary.FileTypes.Image;
import com.prckt.krowemarf.components.DocumentLibrary.FileTypes.Text;
import com.prckt.krowemarf.components.DocumentLibrary.FileTypes.Video;
import com.prckt.krowemarf.services.Access;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices.User;
import com.prckt.krowemarf.services.UserManagerServices._User;

import java.io.File;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
// import java.util.LinkedList;
import java.util.List;

public class MetaDataDocument extends UnicastRemoteObject implements _MetaDataDocument, Serializable {
	private String name;
    private String extension;
    private float size;
    private String path;
    private String type;
    private _User owner;
//  private LinkedList<Access> access;
	
	
	/**
	 * Initializes a newly created MetaDataDocument object so that it represents a document named by name.
	 * @param name
	 * @param extension
	 * @param size
	 * @param path
	 */
	public MetaDataDocument(_User owner , String name, String extension, float size, String path) throws RemoteException{
		boolean known = false;
		
		this.name = name;
		this.extension = extension;
		this.size = size;
		this.path = path;
		this.owner = new User(owner.getLogin());
//		this.access = new LinkedList<>();
		
		for (Text ext : Text.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Text";
				known = true;
			}
		}
		for (Image ext : Image.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Image";
				known = true;
			}
		}
		for (Audio ext : Audio.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Audio";
				known = true;
			}
		}
		for (Video ext : Video.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Video";
				known = true;
			}
		}
		if(!known) {
			this.type = "Unknown";
		}
	}
	
	
	/**
	 * 
	 * Initializes a newly created MetaDataDocument object so that it represents a document locked at a file.
	 * @param _file
	 */
	public MetaDataDocument(File _file)  throws RemoteException{
		
		boolean known = false;
		String _nameAndExt = _file.getName();
		String[] _sepNameExt = _nameAndExt.split(".");
		
		this.name = _sepNameExt[0];
		this.extension = _sepNameExt[1];
		this.size = _file.length();
		this.path = _file.getPath();
		
		for (Text ext : Text.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Text";
				known = true;
			}
		}
		for (Image ext : Image.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Image";
				known = true;
			}
		}
		for (Audio ext : Audio.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Audio";
				known = true;
			}
		}
		for (Video ext : Video.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Video";
				known = true;
			}
		}
		if(!known) {
			this.type = "Unknown";
		}
	}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public float getSize() throws RemoteException{
        return size;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
	public _User getOwner() throws RemoteException{
		return owner;
	}

	public void addDbComponent() throws RemoteException {

		DbConnectionManager dbConnectionManager = new DbConnectionManager();
		Connection connexion = dbConnectionManager.connect("MetaDataDocument");
		try {
			_DbConnectionManager.insertOrUpdateOrDelete(connexion, "INSERT INTO `Component` (`name`) VALUES ('" + this.getName() + "')");
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeDbComponent() throws RemoteException {
		DbConnectionManager dbConnectionManager = new DbConnectionManager();
		Connection connexion = dbConnectionManager.connect("MetaDataDocument");
		try {
			_DbConnectionManager.insertOrUpdateOrDelete(connexion, "DELETE FROM `Component` WHERE `name` = '" + this.getName() + "'");
			connexion.close();
		} catch (SQLException  e) {
			e.printStackTrace();
		}
	}

	public String isPermission(User user) throws RemoteException{

		DbConnectionManager dbConnectionManager = new DbConnectionManager();
		Connection connexion = dbConnectionManager.connect("MetaDataDocument");
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
		Connection connexion = dbConnectionManager.connect("MetaDataDocument");
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
		Connection connexion = dbConnectionManager.connect("MetaDataDocument");
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
		Connection connexion = dbConnectionManager.connect("MetaDataDocument");
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
		Connection connexion = dbConnectionManager.connect("MetaDataDocument");
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
