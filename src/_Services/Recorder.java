package _Services;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;


public class Recorder {
	public String fileName;
	public String filePath;
	////////////////////////////////////// type de formating
	public String formating;
	public ComponentType componentType;
	
	
	public void writeRecord(String fileName) throws IOException {
		BufferedWriter bufferW = null;
		FileWriter fileW = null;
		
		try {
			fileW = new FileWriter(fileName);
			bufferW = new BufferedWriter(fileW);
			
			/////// affectation avec le résultat du transfert
			//bw.write(s, off, len);
		}
		finally {
			if(bufferW != null) {
				bufferW.close();
			}
			if(fileW != null) {
				fileW.close();
			}
		}
	}
	
	public void readRecord() throws IOException {
		FileInputStream fileIS = null;
		File fileIn = null;
		
		try {
			fileIS = new FileWriter(fileName);
			bufferW = new BufferedWriter(fileW);
		}
		finally {
			if(bufferR != null) {
				bufferR.close();
			}
		}
	}
}
