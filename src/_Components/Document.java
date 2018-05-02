package _Components;

import java.io.File;
import _FileTypes.*;

public class Document {
	public String name;
	public String extension;
	public float size;
	public String path;
	public String type;
	
	
	/**
	 * Initializes a newly created Document object so that it represents a document named by name.
	 * @param name
	 * @param extension
	 * @param size
	 * @param path
	 */
	public Document(String name, String extension, float size, String path) {		
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
	 * Initializes a newly created Document object so that it represents a document locked at a file.
	 * @param _file
	 */
	public Document (File _file) {
		
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
}
