package com.prckt.krowemarf.components.DocumentLibrary;

import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;
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
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 * @param _MetaDataDocument _MetaDataDocument to insert
	 */
	@Override
	public void add(_MetaDataDocument _MetaDataDocument) { this.metaDataDocumentList.add(_MetaDataDocument); }
	
	/**
	 * Remove a _MetaDataDocument from the library
	 * @param metaDataDocument _MetaDataDocument to remove
	 */
    @Override
	public void remove(_MetaDataDocument metaDataDocument) throws RemoteException, SQLException {
        PreparedStatement pstmt = this.dbConnection.prepareStatement(this.removeQuery, Statement.RETURN_GENERATED_KEYS);
        pstmt.setObject(1, SerializationUtils.serialize(_MetaDataDocument.copy(metaDataDocument)) );
        pstmt.executeUpdate();

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
	public _MetaDataDocument get(String name, String path, String extension) throws RemoteException {
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
	}
	
	/**
	 * Recover all the list of _MetaDataDocument
	 * @return the complete ArrayList
	 */
    @Override
	public ArrayList<_MetaDataDocument> getall() throws RemoteException {
        ArrayList<_MetaDataDocument> allMetaData = new ArrayList<>();

        for (Object o : _DbConnectionManager.deSerializeJavaObjectFromDB(this.dbConnection, _Component.documentLibraryTableName, this.getName())) {
            allMetaData.add((_MetaDataDocument) o);
        }

        Logger.getGlobal().log(Level.INFO,"Requête pour récupérer toutes les méta-datas du composant : " + this.getName());

        return allMetaData;
    }
	
	/**
	 * Recover a ArrayList with all documents with the same name
	 * @param name of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<_MetaDataDocument> filterByName(String name) throws RemoteException {
		ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getName().equals(name)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a ArrayList with all documents with the same extension
	 * @param extension of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<_MetaDataDocument> filterByExtension(String extension) throws RemoteException {
		ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getExtension().equals(extension)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a ArrayList with all documents with the same path
	 * @param path of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<_MetaDataDocument> filterByPath(String path) throws RemoteException {
		ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getPath().equals(path)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a ArrayList with all documents with a bigger size
	 * @param size minimum size
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<_MetaDataDocument> filterBySizeSup(float size) throws RemoteException {
		ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getSize() < size) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a ArrayList with all documents with a lowest size
	 * @param size maximum size
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<_MetaDataDocument> filterBySizeInf(float size) throws RemoteException {
		ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getSize() > size) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a ArrayList with all documents with a bounded size
	 * @param inf minimum size
	 * @param sup maximum size
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<_MetaDataDocument> filterBySizeInterval(float inf, float sup) throws RemoteException {
		ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getSize() > inf && a_MetaDataDocumentList.getSize() < sup) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a ArrayList with all documents with the same file's type
	 * @param type type of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public ArrayList<_MetaDataDocument> filterByType(String type) throws RemoteException {
		ArrayList<_MetaDataDocument> filtredList = new ArrayList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this.metaDataDocumentList) {
            if (a_MetaDataDocumentList.getType().equals(type)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }

		return filtredList;
	}


    /**
     * @param user user uploading the file
     * @param buffer file in byte[] form
     * @param metaDataDocument meta datas concerning the file
     * @throws IOException
     * @throws RemoteException
     */
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
            Logger.getGlobal().log(Level.INFO,"Uploading "+ metaDataDocument.getName() +"  file complete from " + metaDataDocument.getOwner().getLogin());
            Logger.getGlobal().log(Level.INFO,"File saved at : " + this.path +  metaDataDocument.getPath() + metaDataDocument.getName()+ "." + metaDataDocument.getExtension());
        } catch (SQLException e1) {
            Logger.getGlobal().log(Level.INFO,"Can't save folder in component : " + this.getName());
            e1.printStackTrace();
        }
    }

    /**
     * @param metaDataDocument meta data of the file who should be downloaded
     * @return return the file under the form of a byte array
     * @throws RemoteException
     */
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
        Logger.getGlobal().log(Level.INFO,"Téléchargement du fichier : " + this.path +  metaDataDocument.getPath() + metaDataDocument.getName()+ "." + metaDataDocument.getExtension());
        return buffer;
    }

    /**
     * @return the name of the component
     * @throws RemoteException
     */
    @Override
	public String getName() throws RemoteException {
		return this.name;
	}

    /**
     * Stop the component and close the dbConnection of the component
     * @throws SQLException
     * @throws RemoteException
     */
    @Override
    public void stop() throws SQLException, RemoteException {
        Logger.getGlobal().log(Level.INFO,"Arrêt du component : " + this.getName());
        this.dbConnection.close();
    }

    /**
     * @param buffer Buffer in which the file should be written
     * @param path Path of the file
     * @param metaDataDocument Meta date concerning the file
     * @throws IOException
     */
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

    /**
     * @param file file who should be transformed in byte[]
     * @return a byte array representing the file
     */
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
    
 /*   public Right isPermission(Users user) {
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
    
    public ArrayList<Access> isAdmin() {
    	
    	ArrayList<Access> a = new ArrayList<Access>;
    	
    	for (int i = 0; i < access.size; i++) {
    		if (access.get(i).getRight == "admin") {
    			Access acc = new Access(access.get(i).getUser, access.get(i).getRight);
    			a.add(acc);
    		}
    	}
    	return a;
    }
    
    public ArrayList<Access> isUser() {
    	
    	ArrayList<Access> a = new ArrayList<Access>;
    	
    	for (int i = 0; i < access.size; i++) {
    		if (access.get(i).getRight == "user") {
    			Access acc = new Access(access.get(i).getUser, access.get(i).getRight);
    			a.add(acc);
    		}
    	}
    	return a;
    }*/
}
