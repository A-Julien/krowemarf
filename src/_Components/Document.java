package _Components;

import java.io.File;
import _FileTypes.*;

public class Document {
	public String name;
	public String extension;
	public float size;
	public String path;
	public String type;
	
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
		
	public Document (File f) {
		
		boolean known = false;
		String _nameAndExt = f.getName();
		String[] _sepNameExt = _nameAndExt.split(".");
		
		this.name = _sepNameExt[0];
		this.extension = _sepNameExt[1];
		this.size = f.length();
		this.path = f.getPath();
		
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


