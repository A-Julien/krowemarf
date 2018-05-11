package com.prckt.krowemarf.components.DocumentLibrary;

import com.prckt.krowemarf.components._Component;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface _DocumentLibrary extends _Component {
    /**
     * Insert a _MetaDataDocument in the library
     * @param _MetaDataDocument _MetaDataDocument to insert
     */
    public void add(_MetaDataDocument _MetaDataDocument) throws RemoteException;

    /**
     * Remove a _MetaDataDocument from the library
     * @param _MetaDataDocument _MetaDataDocument to remove
     */
    public void remove(_MetaDataDocument _MetaDataDocument) throws RemoteException, SQLException;

    /**
     * Recover a document by its name, path and extension
     * @param name of the document
     * @param path of the document
     * @param extension of the document
     * @return The document wanted, null if a document with these name,
     * @return path and extension doesn't exist in the library
     */
    public _MetaDataDocument get(String name, String path, String extension) throws RemoteException;


    /**
     * Recover all the list of _MetaDataDocument
     * @return the complete ArrayList
     */
    public ArrayList<_MetaDataDocument> getall() throws RemoteException;

    /**
     * Recover a ArrayList with all documents with the same name
     * @param name of the document
     * @return The list of documents that accomplish the request
     */

    public ArrayList<_MetaDataDocument> filterByName(String name) throws RemoteException;

    /**
     * Recover a ArrayList with all documents with the same extension
     * @param extension of the document
     * @return The list of documents that accomplish the request
     */
    public ArrayList<_MetaDataDocument> filterByExtension(String extension) throws RemoteException;
    /**
     * Recover a ArrayList with all documents with the same path
     * @param path of the document
     * @return The list of documents that accomplish the request
     */
    public ArrayList<_MetaDataDocument> filterByPath(String path) throws RemoteException;
    /**
     * Recover a ArrayList with all documents with a bigger size
     * @param size minimum size
     * @return The list of documents that accomplish the request
     */
    public ArrayList<_MetaDataDocument> filterBySizeSup(float size) throws RemoteException;

    /**
     * Recover a ArrayList with all documents with a lowest size
     * @param size maximum size
     * @return The list of documents that accomplish the request
     */
    public ArrayList<_MetaDataDocument> filterBySizeInf(float size) throws RemoteException;

    /**
     * Recover a ArrayList with all documents with a bounded size
     * @param inf minimum size
     * @param sup maximum size
     * @return The list of documents that accomplish the request
     */
    public ArrayList<_MetaDataDocument> filterBySizeInterval(float inf, float sup) throws RemoteException;

    /**
     * Recover a ArrayList with all documents with the same file's type
     * @param type type of the document
     * @return The list of documents that accomplish the request
     */
    public ArrayList<_MetaDataDocument> filterByType(String type) throws RemoteException;

    public void uploadFile(String pseudo, byte[] buffer,  _MetaDataDocument metaDataDocument) throws IOException;

    public byte[] downloadFile(_MetaDataDocument _MetaDataDocument) throws Exception;
}
