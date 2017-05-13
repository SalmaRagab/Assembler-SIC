package fileHandling.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Reader {

	private String strLine;
	private FileInputStream fstream;
	private BufferedReader br;
	private String fileName;
	
	private ArrayList<String> fileLine;
	
	public ArrayList<String> getFileLines() {
		return fileLine;
	}
	
	public Reader(String fileName) {
		this.fileName = fileName + ".txt";
		fileLine = new ArrayList<>();
		try {
//			fstream = new FileInputStream(fileName);
			fstream = new FileInputStream(this.fileName);
			br = new BufferedReader(new InputStreamReader(fstream));

		} catch (FileNotFoundException e) {}
	}
			
	public void readFile() throws IOException {
		//Read File Line By Line
		try {
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
				fileLine.add(strLine); //each strLine is a line in the program
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		br.close();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}