package fileHandling.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import operations.NotOperation;
import operations.Operation;
import tables.symbolTable.SymbolTable;

public class IntermediateFileWriter {
	
	ArrayList<Operation> operations;
	
	private  String path;

	private final int WIDTH = 120;
	private final String NEW_LINE = System.getProperty("line.separator"); 
	private final int CELL_WIDTH = 20;
	
	public IntermediateFileWriter(ArrayList<Operation> operations, String progName) {
		this.operations = operations;
		// create directory to save all project output files
		path = "./outputFiles/" + progName;
		File file = new File(path);
		file.mkdirs(); 
		try {
			writeIntermediateFile(createIntermediateFile(path));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void writeIntermediateFile(FileWriter fileWriter) throws IOException {
//		table header
		drawTableHeader(fileWriter);
		drawRows(fileWriter);
		
		fileWriter.close();		
	}

	private void drawRows(FileWriter fileWriter) throws IOException {
		int counter = 1;
		String row;
		String errorMessage = "";
		Operation o;
		
		for (int i = 0; i < operations.size(); i++) {
			o = operations.get(i);
			row = "";
			row += String.valueOf(counter) +
					drawSpaces((CELL_WIDTH - String.valueOf(counter).length()));
			
			if ((o.isComment())) { //comment line
				
				row += drawSpaces(CELL_WIDTH) +  o.getComment();
				
			} else if (o instanceof NotOperation) { //wrong line
				row += drawSpaces(CELL_WIDTH);
				for (int j = 0; j < o.getLine().length; j++) {
					if (o.getLine()[j] == null) {
						row += drawSpaces(CELL_WIDTH);
					} else {
						row += o.getLine()[j] + drawSpaces(CELL_WIDTH - (String.valueOf(o.getLine()[j]).length()));
					}
					errorMessage = drawSpaces(CELL_WIDTH * 2) + o.getErrorMessege();
				}
				
			} else {
				row += writeLocation(row, fileWriter, o) +
						drawSpaces((CELL_WIDTH - (String.valueOf(o.getReturnAddress()).length())));
				
				row += writeLabel(row, fileWriter, o) +
						drawSpaces(CELL_WIDTH - (String.valueOf(o.getLine()[0]).length()));
				
				row += writeMnemonic(row, fileWriter, o) + 
						drawSpaces(CELL_WIDTH - (String.valueOf(o.getLine()[1]).length()));
				
				row += writeOperand(row, fileWriter, o) +
						drawSpaces(CELL_WIDTH - (String.valueOf(o.getLine()[2]).length()));
				
				row += writeComment(row, fileWriter, o) +
						drawSpaces(CELL_WIDTH - (String.valueOf(o.getLine()[2]).length()));
			
			}
			
			if (o.getErrorMessege() != null) { //there is an error
				errorMessage = drawSpaces(CELL_WIDTH * 2) + "***Error! " + o.getErrorMessege();
				
			}
			
			fileWriter.write(row);
			fileWriter.write(NEW_LINE);
			
			if (errorMessage.length() != 0) {
				fileWriter.write(errorMessage);
				fileWriter.write(NEW_LINE);
				errorMessage = "";
			}
			
			counter++;
		}
			
	}


	private String writeComment(String row, FileWriter fileWriter, Operation o) {
		String comment = "";
		comment = o.getLine()[3];
		return comment.toUpperCase();
	}


	private String drawSpaces(int spacesLength) {
		String spaces = "";
		for (int i = 0; i < spacesLength; i++) {
			spaces += " ";
		}
		return spaces;
	}
	
	private String writeOperand(String row, FileWriter fileWriter, Operation o) {
		String operand = "";
		operand = o.getLine()[2];
		return operand.toUpperCase();
	}
	
	
	private String writeMnemonic(String row, FileWriter fileWriter, Operation o) {
		String mnemonic = "";
		mnemonic = o.getLine()[1];
		return mnemonic.toUpperCase();
	}


	private String writeLabel(String row, FileWriter fileWriter, Operation o) {
		String label = "";
		label = o.getLine()[0];
		if (label == null) {
			label = "";
		}
		return label.toUpperCase();
	}


	private String writeLocation(String row, FileWriter fileWriter, Operation o) {
		String location = "";
		location = Integer.toHexString(o.getAddress());
		return location.toUpperCase();
		
	}


	private String writeLineNo(String row, FileWriter fileWriter, int lineNo) throws IOException {
		row = row + fillSpace(row, "Number ") + String.valueOf(lineNo);
		return row;
	}
	
	private void drawTableHeader(FileWriter fileWriter) throws IOException {

		String row = "Line no.";
		row += fillSpace(row, "Line no.");
		row += "Location";
		row += fillSpace(row, "Location");
		row += "Label";
		row += fillSpace(row, "Label");
		row += "Mnemonic";
		row += fillSpace(row, "Mnemonic");
		row += "Operand";
		row += fillSpace(row, "Operand");
		row += "Comment";
		row += fillSpace(row, "Comment");
		row += NEW_LINE;
		fileWriter.write(row);
		drawLineSeparator(fileWriter,'-');		
	}

	private void drawLineSeparator(FileWriter fileWriter, char c) throws IOException {
		String row = "";
		for (int i = 0 ; i < WIDTH; i++){
			row += c;
		}
		row += NEW_LINE;
		fileWriter.write(row);
		
	}

	private String fillSpace(String row, String string) {
		String space = "";
		for (int i = string.length() ; i < CELL_WIDTH; i++) {
			space += " ";
		}
		
		return space;
	}


	private FileWriter createIntermediateFile(String path2) throws IOException {
		File file = new File(path + "/intermediateFile.txt");
		FileWriter fileWriter = new FileWriter(file);
		return fileWriter;
	}
	
	
	

}