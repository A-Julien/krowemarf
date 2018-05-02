package _Components;
import java.util.LinkedList;

import _Services.User;

public class DocumentLibrary {
	LinkedList<Document> documentList = new LinkedList<Document>();
	
	///////////login à réaliser plus haut
	public DocumentLibrary(String addr, User user) {
		
	}
	
	
	/**
	 * Insert a document in the list
	 * @param document
	 */
	public void add(Document document) { this.documentList.add(document); }
	
	
	/**
	 * Remove a document from the list
	 * @param document
	 */
	public void remove(Document document) {	this.documentList.remove(document); }
	
	
	/**
	 * Recover a document by its name
	 * @param name
	 * @return the document named by name
	 * @return null if a document with that name doesn't exist in the list
	 */
	public Document get(String name, String path) {
		int i = 0;
		
		while (this.documentList.size()>i) {
			if(this.documentList.get(i).name.concat("." + this.documentList.get(i).extension).equals(name)) {
				return this.documentList.get(i);
			}
			i++;
		}
		return null;
	}
	
	/**
	 * Recover all the list of Document
	 * @return the complete LinkedList
	 */
	public LinkedList<Document> getall(){ return this.documentList;	}
	
	/**
	 * Recover a LinkedList with all documents with the same name(=request) (different extension)
	 * @param request
	 * @return The list of documents that accomplish the request
	 */
	public LinkedList<Document> filterByName(String request) {
		LinkedList<Document> filtredList = new LinkedList<Document>();
		
		for(int i=0; i<this.documentList.size(); i++) {
			if(documentList.get(i).name.equals(request)) {
				filtredList.add(documentList.get(i));
			}
		}
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with the extension request
	 * @param request
	 * @return The list of documents that accomplish the request
	 */
	public LinkedList<Document> filterByExtension(String request) {
		LinkedList<Document> filtredList = new LinkedList<Document>();
		
		for(int i=0; i<this.documentList.size(); i++) {
			if(documentList.get(i).extension.equals(request)) {
				filtredList.add(documentList.get(i));
			}
		}
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with the extension request
	 * @param request
	 * @return The list of documents that accomplish the request
	 */
	public LinkedList<Document> filterByFileType(String request) {
		LinkedList<Document> filtredList = new LinkedList<Document>();
		
		for(int i=0; i<this.documentList.size(); i++) {
			if(documentList.get(i).extension.equals(request)) {
				filtredList.add(documentList.get(i));
			}
		}
		
		return filtredList;
	}
}
