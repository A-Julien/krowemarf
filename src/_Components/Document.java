package _Components;

public class Document {
	public String name;
	public String extension;
	public float size;
	public String path;
	
	public boolean accomplishRequest(String request) {
		String [] requestTab = request.split(request);
		
		/*
		for(int i=0; i<requestTab.length; i++) {
			if() {
				return false;
			}
		}*/
		
		return true;
	}
}
