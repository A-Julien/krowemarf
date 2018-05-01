package _Services;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Recorder {
	public String fileName;
	public String filePath;
	/////////////////////////////////////////////////////////
	public String formating;
	
	
	public ComponentType componentType;
	
	public void writeRecord(String fileName) throws IOException {
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			
			/////// affectation avec le résultat du transfert
			//bw.write(s, off, len);
		}
		finally {
			if(bw != null) {
				bw.close();
			}
			if(fw != null) {
				fw.close();
			}
		}
	}
	
	public void readRecord() {
		
	}
}
