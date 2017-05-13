package fileHandling.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import operations.NotOperation;
import operations.Operation;
import operations.directives.END;
import operations.directives.RESB;
import operations.directives.RESW;
import operations.directives.START;
import tables.symbolTable.SymbolTable;

public class ObjectCodeWriter {
	
	private ArrayList<Operation> operations;
	private String path;
	private SymbolTable symbolTable;
	
	private final String NEW_LINE = System.getProperty("line.separator"); 
	
	public ObjectCodeWriter(SymbolTable symbolTable, ArrayList<Operation> operations, String fileName) {
		this.operations = operations;
		this.symbolTable = symbolTable;
		// create directory to save all project output files
		path = "./outputFiles/" + fileName;
		File file = new File(path);
		file.mkdirs(); 
		
		try {
			writeObjectFile(CreateObjectFile(path));
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeObjectFile(FileWriter fileWriter) throws IOException {
		drawHeader(fileWriter);
		drawRecords(fileWriter);
		drawEnd(fileWriter);
		
		
		fileWriter.close();
		
	}

	private FileWriter CreateObjectFile(String path) throws IOException {
		File file = new File(path + "/objectFile.txt");
		FileWriter fileWriter = new FileWriter(file);
		return fileWriter;
	}

	
	private void drawHeader(FileWriter fileWriter) throws IOException {
		int startingAddress = 0;
		int endingAddress;
		String header = "H";
		
		for (Operation o : operations) {
			if (o instanceof START) {
				header += o.getLine()[0]; //Program Name
				startingAddress = Integer.parseInt(o.getLine()[2], 16);
				header += fillAddress(o.getLine()[2], 6); //Starting address (OPERAND)				
			}
			if (o instanceof END) {
				endingAddress = Integer.parseInt(Integer.toHexString(o.getAddress()), 16);
				int size = (endingAddress - startingAddress);
				header += fillAddress(Integer.toHexString(size), 6);
			}
		}
		fileWriter.write(header.toUpperCase());
		fileWriter.write(NEW_LINE);
		
	}

	private int drawRecord(FileWriter fileWriter, int i) throws IOException {
		
		String text = "T";
		String startingAddress = "";
		String size = "";
		String opCodes = "";
		String lastAddress = "";
		int j;
		for (j = i; j < operations.size(); j++) {
			
			Operation o = operations.get(j);
			
			if (o instanceof END) {
				lastAddress = String.valueOf(o.getAddress());
			} else if (o instanceof NotOperation) {
				
			} else if (j == 0) { //first time and Start
				
				startingAddress = String.valueOf(o.getAddress());
				
			} else if ((o instanceof RESB) || (o instanceof RESW)) { //end this text record
				lastAddress = String.valueOf(o.getAddress());
				break;
				
			} else if (j == i) { //first time
				
//				startingAddress = fillAddress(String.valueOf(o.getAddress()), 6);
				startingAddress = String.valueOf(o.getAddress());

	
				opCodes += o.getOpCode();
				lastAddress = String.valueOf(o.getAddress());
				
			} else {
				if ((opCodes.length() + o.getOpCode().length()) <= 60) {
					lastAddress = String.valueOf(o.getAddress());
					opCodes += o.getOpCode();
							
				} else {
					lastAddress = String.valueOf(o.getAddress());
					break;
				}
			}
		}
		
		if (opCodes.length() == 0) {
			j++;
			return j;
		}
	
		
		if (size.length() == 0) {
			int theSize = Integer.parseInt(lastAddress) - Integer.parseInt(startingAddress);
			size = String.valueOf(theSize);
		}
		
		int sizeNew = Integer.parseInt(size);
		size = fillAddress(Integer.toHexString(sizeNew), 2);
		
		int stAd = Integer.parseInt(startingAddress);
		startingAddress = fillAddress(Integer.toHexString(stAd), 6);
		
		
		text += startingAddress + size + opCodes;
		
		fileWriter.write(text);
		fileWriter.write(NEW_LINE);
		
		return j;
	}
	
	
	private void drawRecords(FileWriter fileWriter) throws IOException {
		int newIndex = 0;
		
		while (newIndex < operations.size()) {
			newIndex = drawRecord(fileWriter, newIndex);
		}
				
	}

	private void drawEnd(FileWriter fileWriter) throws IOException {
		String end = "E";
		String operand = "";
		for (Operation o : operations) {
			if (o instanceof END) {
				try {
					operand = String.valueOf(Integer.parseInt(o.getLine()[2]));
				} catch (Exception e) {
					//then the operand is a label
					operand = fillAddress(symbolTable.getAddress(o.getLine()[2]), 6);
				}
				end += operand;
			}
		}
		fileWriter.write(end.toUpperCase());
		fileWriter.write(NEW_LINE);		
	}
	
	
	
	private String fillAddress(String opCode, int length) {
		if (opCode == null) {
			opCode = "";
		}
		for (int i = opCode.length(); i < length; i++) {
			opCode = "0" + opCode;
		}
		return opCode;
	}
	
}
