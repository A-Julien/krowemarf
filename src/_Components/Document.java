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
		this.name = name;
		this.extension = extension;
		this.size = size;
		this.path = path;
		
		for (Text ext : Text.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Text";
			}
		}
		for (Image ext : Image.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Image";
			}
		}
		for (Audio ext : Audio.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Audio";
			}
		}
		for (Video ext : Video.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Video";
			}
		}
	}
		
	public Document (File f) {
		String _nameAndExt = f.getName();
		String[] _sepNameExt = _nameAndExt.split(".");
		
		this.name = _sepNameExt[0];
		this.extension = _sepNameExt[1];
		this.size = f.length();
		this.path = f.getPath();
		
		for (Text ext : Text.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Text";
			}
		}
		for (Image ext : Image.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Image";
			}
		}
		for (Audio ext : Audio.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Audio";
			}
		}
		for (Video ext : Video.values()) {
			if (ext.toString().equals(extension)) {
				this.type = "Video";
			}
		}
	}
}


