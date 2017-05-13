package fileHandling.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import operations.Operation;

	
public class ListingFileWriter {

	private ArrayList<Operation> operations;
	private String path;
	private int width;
	
	
	private final int LINE_WIDTH = 4;
	private final int ADDRESS_WIDTH = 7;
	private final int LABEL_WIDTH = 8;
	private final int OPERATION_WIDTH = 9;
	private final int OPERAND_WIDTH = 15;
	private final int OPCODE_WIDTH = 15;
	private final int SPACE = 5;
	private final int CELLS_NUBMBER = 6; 
	private final String NEW_LINE = System.getProperty("line.separator"); 
	
	public ListingFileWriter(ArrayList<Operation> operations, String fileName) {
		this.operations = operations;
		width = LABEL_WIDTH + LINE_WIDTH + ADDRESS_WIDTH + OPERAND_WIDTH + OPERATION_WIDTH 
				+ OPCODE_WIDTH + (SPACE +1 ) * CELLS_NUBMBER + CELLS_NUBMBER + 1 ;
		// create directory to save all project output files
		path = "./outputFiles/" + fileName;
		File file = new File(path);
		file.mkdirs(); 
		
		try {
			writeListingFile(CreateListingFile(path));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeListingFile(FileWriter fileWriter) throws IOException {
		drawTableHeader(fileWriter);
		drawRows(fileWriter);
		writeDate(fileWriter);
		
		
		fileWriter.close();
		
	}

	private void writeDate(FileWriter fileWriter) throws IOException {
		String row = NEW_LINE + NEW_LINE + NEW_LINE;
		fileWriter.write(row);
		
	}

	private FileWriter CreateListingFile(String path) throws IOException {
		File file = new File(path + "/listingFile.txt");
		FileWriter fileWriter = new FileWriter(file);
		return fileWriter;
	}

	
	private void drawTableHeader(FileWriter fileWriter) throws IOException {
		// 1st line 
//		drawLineSeparator(fileWriter, '_');
//		2nd line > col names
		String row = "  Line";
		row += fillSpace(row, "Line", LINE_WIDTH);
		row+= fillSpace(row, "", SPACE);
		row += "  Address";
		row += fillSpace(row, "Address", ADDRESS_WIDTH);
		row+= fillSpace(row, "", SPACE);
		row += "  Label";
		row += fillSpace(row, "Label", LABEL_WIDTH);
		row+= fillSpace(row, "", SPACE);
		row += "  Operation";
		row += fillSpace(row, "Operation", OPERATION_WIDTH);
		row+= fillSpace(row, "", SPACE);
		row += "  Operand";
		row += fillSpace(row, "Operand", OPERAND_WIDTH);
		row+= fillSpace(row, "", SPACE);
		row += "  OpCode";
		row += fillSpace(row, "OpCode", OPCODE_WIDTH);
		row+= fillSpace(row, "", SPACE);
		row +=" ";
		row += NEW_LINE;
		fileWriter.write(row);
		drawLineSeparator(fileWriter,'_');
	}


	private void drawRows(FileWriter fileWriter) throws IOException {
		String row = "";
		Operation operation;
		String[] line;
		String address;
		for (int i = 0; i < operations.size(); i++) {
			operation = operations.get(i);
			line = operation.getLine();
			row = writeIndex((i + 1) * 5);
			address = Integer.toHexString(operation.getAddress());
			row += write(address.toUpperCase(), ADDRESS_WIDTH);
			
			if (operation.isComment()) {
				row += write ( operation.getComment(), width);
			} else if (line != null) {
				row += write(line[0], LABEL_WIDTH);
				row += write(line[1], OPERATION_WIDTH);
				row += write(line[2], OPERAND_WIDTH);
				row += write(operation.getOpCode(), OPCODE_WIDTH);
				
			}
			
			fileWriter.write(row);
			
			if (!operation.isCorrect()) {
				row = NEW_LINE;
				fileWriter.write(row);
				row = "  ERROR >>: " + operation.getErrorMessege();
				fileWriter.write(row);
			}
			
			row = NEW_LINE;
			fileWriter.write(row);
			
		}
		
	}

	private String write(String word, int width) {
		String row = "";
		if (word == null) {
			word = "";
		}
		row += word;
		row += fillSpace(row, word, width);
		row += fillSpace(row, "", SPACE);
		row += "  ";
		return row;
	}

	private String writeIndex(int i) {
		String row = "  " + i;
		row += fillSpace(row,row, LINE_WIDTH +2);
		row += fillSpace(row, "", SPACE);
		row += "  ";
		return row;
	}
	

	private String fillSpace(String row, String string, int width) {
		String space = "";
		for (int i = string.length() ; i < width; i++) {
			space += " ";
		}
		
		return space;
	}

	private void drawLineSeparator(FileWriter fileWriter, char c) throws IOException {
		String row = "";
		for (int i = 0 ; i < width; i++){
			row += c;
		}
		row += NEW_LINE;
		fileWriter.write(row);
		
	}


	private FileWriter CreateSymbolTableFile(String path) throws IOException {
		File file = new File(path + "/symbolTable.txt");
		FileWriter fileWriter = new FileWriter(file);
		return fileWriter;
		
		
	}

}
