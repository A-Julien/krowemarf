package com.prckt.krowemarf.components.DocumentLibrary;

import com.prckt.krowemarf.components.DocumentLibrary.FileTypes.Audio;
import com.prckt.krowemarf.components.DocumentLibrary.FileTypes.Image;
import com.prckt.krowemarf.components.DocumentLibrary.FileTypes.Text;
import com.prckt.krowemarf.components.DocumentLibrary.FileTypes.Video;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MetaDataDocument extends UnicastRemoteObject implements _MetaDataDocument {
	private String name;
    private String extension;
    private float size;
    private String path;
    private String type;
	
	
	/**
	 * Initializes a newly created MetaDataDocument object so that it represents a document named by name.
	 * @param name
	 * @param extension
	 * @param size
	 * @param path
	 */
	public MetaDataDocument(String name, String extension, float size, String path) throws RemoteException{
		boolean known = false;
		
		this.name = name;
		this.extension = extension;
		this.size = size;
		this.path = path;
		
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

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    public float getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }
}
