package _Components;
import java.util.LinkedList;


public class DocumentLibrary {
	private LinkedList<Document> documentList;
	
	/**
	 * Initializes a newly created DocumentLibrary object so that it represents a library of document.
	 * @param addr
	 */
	public DocumentLibrary(String addr) {
		this.documentList = new LinkedList<Document>();
	}
	
	/**
	 * Insert a document in the library
	 * @param The document to insert
	 */
	public void add(Document document) { this.documentList.add(document); }
	
	/**
	 * Remove a document from the library
	 * @param The document to remove
	 */
	public void remove(Document document) {	this.documentList.remove(document); }
	
	/**
	 * Recover a document by its name, path and extension
	 * @param The name of the document
	 * @param The path of the document
	 * @param The extension of the document
	 * @return The document wanted, null if a document with these name,
	 * @return path and extension doesn't exist in the library
	 */
	public Document get(String name, String path, String extension) {
		int i = 0;
		
		while (this.documentList.size()>i) {
			if(this.documentList.get(i).name.equals(name)
			&& this.documentList.get(i).path.equals(path)
			&& this.documentList.get(i).extension.equals(extension)) {
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
	 * Recover a LinkedList with all documents with the same name
	 * @param The name of the document
	 * @return The list of documents that accomplish the request
	 */
	
	public LinkedList<Document> filterByName(String name) {
		LinkedList<Document> filtredList = new LinkedList<Document>();
		
		for(int i=0; i<this.documentList.size(); i++) {
			if(documentList.get(i).name.equals(name)) {
				filtredList.add(documentList.get(i));
			}
		}
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with the same extension
	 * @param The extension of the document
	 * @return The list of documents that accomplish the request
	 */
	public LinkedList<Document> filterByExtension(String extension) {
		LinkedList<Document> filtredList = new LinkedList<Document>();
		
		for(int i=0; i<this.documentList.size(); i++) {
			if(documentList.get(i).extension.equals(extension)) {
				filtredList.add(documentList.get(i));
			}
		}
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with the same path
	 * @param The path of the document
	 * @return The list of documents that accomplish the request
	 */
	public LinkedList<Document> filterByPath(String path) {
		LinkedList<Document> filtredList = new LinkedList<Document>();
		
		for(int i=0; i<this.documentList.size(); i++) {
			if(documentList.get(i).path.equals(path)) {
				filtredList.add(documentList.get(i));
			}
		}
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with a bigger size
	 * @param The minimum size
	 * @return The list of documents that accomplish the request
	 */
	public LinkedList<Document> filterBySizeSup(float size) {
		LinkedList<Document> filtredList = new LinkedList<Document>();
		
		for(int i=0; i<this.documentList.size(); i++) {
			if(documentList.get(i).size < size) {
				filtredList.add(documentList.get(i));
			}
		}
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with a lowest size
	 * @param The maximum size
	 * @return The list of documents that accomplish the request
	 */
	public LinkedList<Document> filterBySizeInf(float size) {
		LinkedList<Document> filtredList = new LinkedList<Document>();
		
		for(int i=0; i<this.documentList.size(); i++) {
			if(documentList.get(i).size > size) {
				filtredList.add(documentList.get(i));
			}
		}
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with a bounded size
	 * @param The minimum size
	 * @param The maximum size
	 * @return The list of documents that accomplish the request
	 */
	public LinkedList<Document> filterBySizeInterval(float inf, float sup) {
		LinkedList<Document> filtredList = new LinkedList<Document>();
		
		for(int i=0; i<this.documentList.size(); i++) {
			if(documentList.get(i).size > inf && documentList.get(i).size < sup) {
				filtredList.add(documentList.get(i));
			}
		}
		
		return filtredList;
	}
	
	/**
	 * Recover a LinkedList with all documents with the same file's type
	 * @param The type of the document
	 * @return The list of documents that accomplish the request
	 */
	public LinkedList<Document> filterByType(String type) {
		LinkedList<Document> filtredList = new LinkedList<Document>();
		
		for(int i=0; i<this.documentList.size(); i++) {
			if(documentList.get(i).type.equals(type)) {
				filtredList.add(documentList.get(i));
			}
		}

		return filtredList;
	}
	
}
