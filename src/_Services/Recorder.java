package _Services;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Recorder {
	public String fileName;
	public String filePath;
	////////////////////////////////////// type de formating
	public String formating;
	public ComponentType componentType;
	
	
	public void writeRecord(String fileName) throws IOException {
		File fileOut = null;
		FileOutputStream fileOS = null;
		
		try {
			fileOut = new File("filename");
			fileOS = new FileOutputStream(fileOut);
		}
		finally {
			if(fileOS != null) {
				fileOS.close();
			}
		}
	}
	
	public void readRecord() throws IOException {
		File fileIn = null;
		FileInputStream fileIS = null;
		
		try {
			fileIn = new File("filename");
			fileIS = new FileInputStream(fileIn);
		}
		finally {
			if(fileIS != null) {
				fileIS.close();
			}
		}
	}
}
