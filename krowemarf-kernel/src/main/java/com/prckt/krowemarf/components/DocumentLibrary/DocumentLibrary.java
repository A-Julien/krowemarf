package com.prckt.krowemarf.components.DocumentLibrary;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.services.Access;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices.User;
import com.prckt.krowemarf.services.UserManagerServices._User;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//TODO changer touts les filters par des requettes sql
public class DocumentLibrary extends UnicastRemoteObject implements _DocumentLibrary {
	private ArrayList<_MetaDataDocument> metaDataDocumentList;
	private String path;
	private String name;
    private Connection dbConnection;
    private final String insertQuery =
            "INSERT INTO " + _Component.documentLibraryTableName + "(name, extension, size, path, Composant_Name, serialized_object) VALUES (?,?,?,?,?,?)";
    private final String removeQuery =
            "DELETE FROM " + _Component.documentLibraryTableName + " WHERE serialized_object = ?";

	/**
	 * Initializes a newly created DocumentLibrary object so that it represents a library of document.
	 */
	public DocumentLibrary(String name, String path) throws RemoteException {
	    super();
		this.metaDataDocumentList = new ArrayList<>();
		this.path = path + "/";
		this.name = name;
		this.dbConnection = new DbConnectionManager().connect(this.getName());
	}
	
	/**
	 * Insert a _MetaDataDocument in the library
	 * @param metaDataDocument _MetaDataDocument to insert
	 */
	@Override
	public void add(_MetaDataDocument metaDataDocument) throws RemoteException {
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        try {
            _DbConnectionManager.insertOrUpdateOrDelete(connexion, "INSERT INTO `documentLibrary_krowemarf` VALUES ('" + metaDataDocument.getName() + "', '" + metaDataDocument.getExtension() + "', '" + metaDataDocument.getSize() + "', '" + metaDataDocument.getPath() + "', '" + this.getName() + "', '" + SerializationUtils.serialize(_MetaDataDocument.copy(metaDataDocument)) + "')");
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //(`name`, `extension`, `size`, `path`, `Composant_Name`)

        // this.metaDataDocumentList.add(_MetaDataDocument);
    }
	
	/**
	 * Remove a _MetaDataDocument from the library
	 * @param metaDataDocument _MetaDataDocument to remove
	 */
    @Override
	public void remove(_MetaDataDocument metaDataDocument) throws RemoteException, SQLException {
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        try {
            _DbConnectionManager.insertOrUpdateOrDelete(connexion, "DELETE FROM `documentLibrary_krowemarf` WHERE `name` = '" + metaDataDocument.getName() + "' AND `extension` = '" + metaDataDocument.getExtension() + "' AND `path` = '" + metaDataDocument.getPath() + "'");
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        PreparedStatement pstmt = this.dbConnection.prepareStatement(this.removeQuery, Statement.RETURN_GENERATED_KEYS);
        pstmt.setObject(1, SerializationUtils.serialize(_MetaDataDocument.copy(metaDataDocument)) );
        pstmt.executeUpdate();
*/
        //this.metaDataDocumentList.remove(_MetaDataDocument);
    }
	
	/**
	 * Recover a document by its name, path and extension
	 * @param name of the document
	 * @param path of the document
	 * @param extension of the document
	 * @return The document wanted, null if a document with these name,
	 * @return path and extension doesn't exist in the library
	 */
    @Override
	public MetaDataDocument get(String name, String path, String extension) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        MetaDataDocument doc = null;
        try {
            User user = new User("Seb","mdp");
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT name, extension, size, path, Composant_Name, serialized_object FROM `documentLibrary_krowemarf`  WHERE name = '" + name + "' AND path = '" + path + "' AND extension = '" + extension + "' AND Composant_Name = '" +  this.getName() + "'", false);
            if (!list.isEmpty()) {
                doc = new MetaDataDocument(user, list.get(0).get(0).toString(), list.get(0).get(1).toString(), Float.parseFloat(list.get(0).get(2).toString()), list.get(0).get(3).toString());
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doc;
        /*
        int i = 0;
		
		while (this.metaDataDocumentList.size()>i) {
			if(this.metaDataDocumentList.get(i).getName().equals(name)
			&& this.metaDataDocumentList.get(i).getPath().equals(path)
			&& this.metaDataDocumentList.get(i).getExtension().equals(extension)) {
				return this.metaDataDocumentList.get(i);
			}
			i++;
		}
		return null;
		*/
	}
	
	/**
	 * Recover all the list of _MetaDataDocument
	 * @return the complete ArrayList
	 */
    @Override
	public ArrayList<MetaDataDocument> getall() throws RemoteException {
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        ArrayList<MetaDataDocument> all = new ArrayList<>();
        User user = new User("Seb","mdp");
        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT name, extension, size, path, Composant_Name, serialized_object FROM `documentLibrary_krowemarf`  WHERE `Composant_Name` = '" +  this.getName() + "'", false);
            if (!list.isEmpty()) {
                for (int i = 0; i<list.size(); i++) {
                    MetaDataDocument doc = new MetaDataDocument(user, list.get(i).get(0).toString(), list.get(i).get(1).toString(), Float.parseFloat(list.get(i).get(2).toString()), list.get(i).get(3).toString());
                    all.add(i, doc);
                }
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return all;
        /*
        ArrayList<_MetaDataDocument> banane = new ArrayList<>();

        for (Object o : _DbConnectionManager.deSerializeJavaObjectFromDB(this.dbConnection, _Component.documentLibraryTableName, this.getName())) {
            banane.add((_MetaDataDocument) o);
        }
        return banane;
        */
    }
	
	/**
	 * Recover a ArrayList with all documents with the same name
	 * @param name of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<MetaDataDocument> filterByName(String name) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        ArrayList<MetaDataDocument> filtredList = new ArrayList<>();
        User user = new User("Seb","mdp");
        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT name, extension, size, path, Composant_Name, serialized_object FROM `documentLibrary_krowemarf`  WHERE `Composant_Name` = '" +  this.getName() + "' AND name = '" + name + "'", false);
            for (int i = 0; i<list.size(); i++) {
                filtredList.add( new MetaDataDocument(user, list.get(i).get(0).toString(), list.get(i).get(1).toString(), Float.parseFloat(list.get(i).get(2).toString()), list.get(i).get(3).toString()) );
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filtredList;
        /*
        ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getName().equals(name)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
        */
	}
	
	/**
	 * Recover a ArrayList with all documents with the same extension
	 * @param extension of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<MetaDataDocument> filterByExtension(String extension) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        ArrayList<MetaDataDocument> filtredList = new ArrayList<>();
        User user = new User("Seb","mdp");
        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT name, extension, size, path, Composant_Name, serialized_object FROM `documentLibrary_krowemarf`  WHERE `Composant_Name` = '" +  this.getName() + "' AND extension = '" + extension + "'", false);
            for (int i = 0; i<list.size(); i++) {
                filtredList.add( new MetaDataDocument(user, list.get(i).get(0).toString(), list.get(i).get(1).toString(), Float.parseFloat(list.get(i).get(2).toString()), list.get(i).get(3).toString()) );
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filtredList;

        /*
        ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getExtension().equals(extension)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
        */
	}
	
	/**
	 * Recover a ArrayList with all documents with the same path
	 * @param path of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<MetaDataDocument> filterByPath(String path) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        ArrayList<MetaDataDocument> filtredList = new ArrayList<>();
        User user = new User("Seb","mdp");
        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT name, extension, size, path, Composant_Name, serialized_object FROM `documentLibrary_krowemarf`  WHERE `Composant_Name` = '" +  this.getName() + "' AND path = '" + path + "'", false);
            for (int i = 0; i<list.size(); i++) {
                filtredList.add( new MetaDataDocument(user, list.get(i).get(0).toString(), list.get(i).get(1).toString(), Float.parseFloat(list.get(i).get(2).toString()), list.get(i).get(3).toString()) );
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filtredList;

        /*
        ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getPath().equals(path)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
        */
	}
	
	/**
	 * Recover a ArrayList with all documents with a bigger size
	 * @param size minimum size
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<MetaDataDocument> filterBySizeSup(float size) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        ArrayList<MetaDataDocument> filtredList = new ArrayList<>();
        User user = new User("Seb","mdp");
        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT name, extension, size, path, Composant_Name, serialized_object FROM `documentLibrary_krowemarf`  WHERE `Composant_Name` = '" +  this.getName() + "' AND size > '" + size + "'", false);
            for (int i = 0; i<list.size(); i++) {
                filtredList.add( new MetaDataDocument(user, list.get(i).get(0).toString(), list.get(i).get(1).toString(), Float.parseFloat(list.get(i).get(2).toString()), list.get(i).get(3).toString()) );
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filtredList;

    /*
		ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getSize() < size) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
		*/
	}
	
	/**
	 * Recover a ArrayList with all documents with a lowest size
	 * @param size maximum size
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<MetaDataDocument> filterBySizeInf(float size) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        ArrayList<MetaDataDocument> filtredList = new ArrayList<>();
        User user = new User("Seb","mdp");
        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT name, extension, size, path, Composant_Name, serialized_object FROM `documentLibrary_krowemarf`  WHERE `Composant_Name` = '" +  this.getName() + "' AND size < '" + size + "'", false);
            for (int i = 0; i<list.size(); i++) {
                filtredList.add( new MetaDataDocument(user, list.get(i).get(0).toString(), list.get(i).get(1).toString(), Float.parseFloat(list.get(i).get(2).toString()), list.get(i).get(3).toString()) );
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filtredList;

        /*
        ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getSize() > size) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;

        */
	}
	
	/**
	 * Recover a ArrayList with all documents with a bounded size
	 * @param inf minimum size
	 * @param sup maximum size
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<MetaDataDocument> filterBySizeInterval(float inf, float sup) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        ArrayList<MetaDataDocument> filtredList = new ArrayList<>();
        User user = new User("Seb","mdp");
        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT name, extension, size, path, Composant_Name, serialized_object FROM `documentLibrary_krowemarf`  WHERE `Composant_Name` = '" +  this.getName() + "' AND `size` BETWEEN '" + inf + "' AND '" + sup + "'", false);
            for (int i = 0; i<list.size(); i++) {
                filtredList.add( new MetaDataDocument(user, list.get(i).get(0).toString(), list.get(i).get(1).toString(), Float.parseFloat(list.get(i).get(2).toString()), list.get(i).get(3).toString()) );
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filtredList;

        /*
        ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getSize() > inf && a_MetaDataDocumentList.getSize() < sup) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;

        */
	}
	
	/**
	 * Recover a ArrayList with all documents with the same file's type
	 * @param type type of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<MetaDataDocument> filterByType(String type) throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        ArrayList<MetaDataDocument> filtredList = new ArrayList<>();
        User user = new User("Seb","mdp");
        try {
            List<List<Object>> list = _DbConnectionManager.sqlToListObject(connexion, "SELECT name, extension, size, path, Composant_Name, serialized_object FROM `documentLibrary_krowemarf`  WHERE `Composant_Name` = '" +  this.getName() + "'", false);
            for (int i = 0; i<list.size(); i++) {
                MetaDataDocument doc = new MetaDataDocument(user, list.get(i).get(0).toString(), list.get(i).get(1).toString(), Float.parseFloat(list.get(i).get(2).toString()), list.get(i).get(3).toString());
                if(doc.getType().equals(type)) {
                    filtredList.add(doc);
                }
            }
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filtredList;

        /*
        ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getType().equals(type)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }

		return filtredList;
        */
	}

    @Override
    public void uploadFile(_User user, byte[] buffer, _MetaDataDocument metaDataDocument) throws IOException, RemoteException {

        String completePath = this.path +  metaDataDocument.getPath() + metaDataDocument.getName()+ "." + metaDataDocument.getExtension();
        System.out.println("PATH -> " + completePath);
        try {
            PreparedStatement pstmt = this.dbConnection.prepareStatement(this.insertQuery, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, metaDataDocument.getName());
            pstmt.setString(2, metaDataDocument.getExtension());
            pstmt.setFloat(3, metaDataDocument.getSize());
            pstmt.setString(4, metaDataDocument.getPath());
            pstmt.setString(5, this.getName());
            pstmt.setObject(6, SerializationUtils.serialize(_MetaDataDocument.copy(metaDataDocument)));
            pstmt.executeUpdate();

            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(completePath));
            outputStream.write(buffer,0,buffer.length);
            outputStream.flush();
            outputStream.close();
            System.out.println("Uploading "+ metaDataDocument.getName() +"  file complete from " + metaDataDocument.getOwner().getLogin());
        } catch (SQLException e1) {
            System.out.println("Can't save folder");
            e1.printStackTrace();
        }


        System.out.println(this.path +  metaDataDocument.getPath() + metaDataDocument.getName()+ "." + metaDataDocument.getExtension());



    }

    @Override
    public byte[] downloadFile(_MetaDataDocument metaDataDocument) throws RemoteException {
        File file = new File(metaDataDocument.getPath() + metaDataDocument.getName() + "." + metaDataDocument.getExtension());
        byte buffer[] = new byte[(int) metaDataDocument.getSize()]; //new byte[(int) file.length()];
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(
                    this.path + metaDataDocument.getPath() +
                            metaDataDocument.getName() + "." +
                            metaDataDocument.getExtension()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            inputStream.read(buffer,0,buffer.length);
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
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

    public static void writeFile(byte[] buffer, String path, _MetaDataDocument metaDataDocument) throws IOException {
       // File file = new File(path + metaDataDocument.getName() + "." + metaDataDocument.getExtension());
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(
                path +
                        metaDataDocument.getName() + "." +
                        metaDataDocument.getExtension()));
        outputStream.write(buffer, 0, buffer.length);
        outputStream.flush();
        outputStream.close();
    }

    public static byte[] fileToBytes(File file){
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutput out = new  ObjectOutputStream(bos)) {
                out.writeObject(file);
                bos.close();
                out.close();
                return bos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    public void addDbComponent() throws RemoteException {

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        try {
            _DbConnectionManager.insertOrUpdateOrDelete(connexion, "INSERT INTO `Component` (`name`) VALUES ('" + this.getName() + "')");
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeDbComponent() throws RemoteException {
        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
        try {
            _DbConnectionManager.insertOrUpdateOrDelete(connexion, "DELETE FROM `Component` WHERE `name` = '" + this.getName() + "'");
            connexion.close();
        } catch (SQLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    public String isPermission(User user) throws RemoteException{

        DbConnectionManager dbConnectionManager = new DbConnectionManager();
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
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
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
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
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
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
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
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
        Connection connexion = dbConnectionManager.connect("DocumentLibrary");
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
