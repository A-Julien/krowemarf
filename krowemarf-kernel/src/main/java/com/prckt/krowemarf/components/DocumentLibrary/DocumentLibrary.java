package com.prckt.krowemarf.components.DocumentLibrary;

import com.prckt.krowemarf.services.Access;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;


public class DocumentLibrary extends UnicastRemoteObject implements _DocumentLibrary {
	private LinkedList<_MetaDataDocument> _MetaDataDocumentList;
	private String path;
	private String name;
	private LinkedList<Access> access;
	
	/**
	 * Initializes a newly created DocumentLibrary object so that it represents a library of document.
	 */
	public DocumentLibrary(String name, String path) throws RemoteException {
	    super();
		this._MetaDataDocumentList = new LinkedList<>();
		this.path = path;
		this.name = name;
		this.access = new LinkedList<Access>();
	}
	
	/**
	 * Insert a _MetaDataDocument in the library
	 * @param _MetaDataDocument _MetaDataDocument to insert
	 */
	@Override
	public void add(_MetaDataDocument _MetaDataDocument) { this._MetaDataDocumentList.add(_MetaDataDocument); }
	
	/**
	 * Remove a _MetaDataDocument from the library
	 * @param _MetaDataDocument _MetaDataDocument to remove
	 */
    @Override
	public void remove(_MetaDataDocument _MetaDataDocument) {	this._MetaDataDocumentList.remove(_MetaDataDocument); }
	
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
		
		while (this._MetaDataDocumentList.size()>i) {
			if(this._MetaDataDocumentList.get(i).getName().equals(name)
			&& this._MetaDataDocumentList.get(i).getPath().equals(path)
			&& this._MetaDataDocumentList.get(i).getExtension().equals(extension)) {
				return this._MetaDataDocumentList.get(i);
			}
			i++;
		}
		return null;
	}
	
	
	/**
	 * Recover all the list of _MetaDataDocument
	 * @return the complete LinkedList
	 */
    @Override
	public LinkedList<_MetaDataDocument> getall(){ return this._MetaDataDocumentList;	}
	
	/**
	 * Recover a LinkedList with all documents with the same name
	 * @param name of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public LinkedList<_MetaDataDocument> filterByName(String name) throws RemoteException {
		LinkedList<_MetaDataDocument> filtredList = new LinkedList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this._MetaDataDocumentList) {
            if (a_MetaDataDocumentList.getName().equals(name)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with the same extension
	 * @param extension of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public LinkedList<_MetaDataDocument> filterByExtension(String extension) throws RemoteException {
		LinkedList<_MetaDataDocument> filtredList = new LinkedList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this._MetaDataDocumentList) {
            if (a_MetaDataDocumentList.getExtension().equals(extension)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with the same path
	 * @param path of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public LinkedList<_MetaDataDocument> filterByPath(String path) throws RemoteException {
		LinkedList<_MetaDataDocument> filtredList = new LinkedList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this._MetaDataDocumentList) {
            if (a_MetaDataDocumentList.getPath().equals(path)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with a bigger size
	 * @param size minimum size
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public LinkedList<_MetaDataDocument> filterBySizeSup(float size) throws RemoteException {
		LinkedList<_MetaDataDocument> filtredList = new LinkedList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this._MetaDataDocumentList) {
            if (a_MetaDataDocumentList.getSize() < size) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with a lowest size
	 * @param size maximum size
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public LinkedList<_MetaDataDocument> filterBySizeInf(float size) throws RemoteException {
		LinkedList<_MetaDataDocument> filtredList = new LinkedList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this._MetaDataDocumentList) {
            if (a_MetaDataDocumentList.getSize() > size) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with a bounded size
	 * @param inf minimum size
	 * @param sup maximum size
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public LinkedList<_MetaDataDocument> filterBySizeInterval(float inf, float sup) throws RemoteException {
		LinkedList<_MetaDataDocument> filtredList = new LinkedList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this._MetaDataDocumentList) {
            if (a_MetaDataDocumentList.getSize() > inf && a_MetaDataDocumentList.getSize() < sup) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with the same file's type
	 * @param type type of the document
	 * @return The list of documents that accomplish the request
	 */
    @Override
	public LinkedList<_MetaDataDocument> filterByType(String type) throws RemoteException {
		LinkedList<_MetaDataDocument> filtredList = new LinkedList<>();

        for (_MetaDataDocument a_MetaDataDocumentList : this._MetaDataDocumentList) {
            if (a_MetaDataDocumentList.getType().equals(type)) {
                filtredList.add(a_MetaDataDocumentList);
            }
        }

		return filtredList;
	}

    @Override
    public void uploadFile(String pseudo, byte[] buffer, _MetaDataDocument _MetaDataDocument) throws IOException, RemoteException {
        System.out.println("upload");
        System.out.println(this.path +  _MetaDataDocument.getPath() + _MetaDataDocument.getName()+ "." + _MetaDataDocument.getExtension());

        BufferedOutputStream outputStream = new BufferedOutputStream( new FileOutputStream(this.path +  _MetaDataDocument.getPath() + _MetaDataDocument.getName()+ "." + _MetaDataDocument.getExtension()));
        outputStream.write(buffer,0,buffer.length);
        outputStream.flush();
        outputStream.close();
        
        this.add(_MetaDataDocument);
        
        System.out.println("end transfere");
    }

    @Override
    public byte[] downloadFile(_MetaDataDocument metaDataDocument) throws Exception {
        File file = new File(metaDataDocument.getPath() + metaDataDocument.getName() + "." + metaDataDocument.getExtension());
        byte buffer[] = new byte[(int) file.length()];
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file.getPath()));
        inputStream.read(buffer,0,buffer.length);
        inputStream.close();
        return buffer;
    }

    @Override
	public String getName() throws RemoteException {
		return this.name;
	}

	public static File writeFile(byte[] buffer, String path) throws IOException {
        File file = new File(path);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
        outputStream.write(buffer, 0, buffer.length);
        outputStream.flush();
        outputStream.close();
        return file;
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
