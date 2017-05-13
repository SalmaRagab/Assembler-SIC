package fileHandling.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import tables.symbolTable.Symbol;
import tables.symbolTable.SymbolTable;

public class SymbolTableWriter {
	
	SymbolTable symbolTable;

	private final int WIDTH = 37-12;
	private final String NEW_LINE = System.getProperty("line.separator"); 
	private final int CELL_WIDTH = 10;
	
	private  String path;
	
	
	
	public SymbolTableWriter(SymbolTable symbolTable, String progName) {
		this.symbolTable = symbolTable;
		
		// create directory to save all project output files
		path = "./outputFiles/" + progName;
		File file = new File(path);
		file.mkdirs(); 
		
		try {
			writeSymbolTable(CreateSymbolTableFile(path));
			
		} catch (Exception e) {}
	}
 

	private void writeSymbolTable(FileWriter fileWriter) throws IOException {
//		table header
		drawTableHeader(fileWriter);
		drawRows(fileWriter);
		
		fileWriter.close();

		
	}


	private void drawRows(FileWriter fileWriter) throws IOException {
		String [] Symbols  = symbolTable.getSymbols();
		String row;
		String word;
		for (String symbol : Symbols) {
			row = "| " + symbol.toUpperCase();
			row += fillSpace(row, symbol);
			try {
				word = symbolTable.getAddress(symbol).toUpperCase();
				while (word.length() < 6) {
					word = "0" + word;
				}
			} catch (Exception e) {
				// no address
				word = "";
			}
			
			row += "| " + word;
			row += fillSpace(row, word);
			row += "|" + NEW_LINE;
			fileWriter.write(row);
			drawLineSeparator(fileWriter, '-');
		}
		
	}


	private String fillSpace(String row, String string) {
		String space = "";
		for (int i = string.length() ; i < CELL_WIDTH; i++) {
			space += " ";
		}
		
		return space;
	}


	private void drawTableHeader(FileWriter fileWriter) throws IOException {
		// 1st line 
		drawLineSeparator(fileWriter, '=');
//		2nd line > col names
		String row = "| Symbol";
		row += fillSpace(row, "Symbol");
//		row += "| Value";
//		row += fillSpace(row, "Value");
		row += "| Address";
		row += fillSpace(row, "Address");
		row +="|";
		row += NEW_LINE;
		fileWriter.write(row);
		drawLineSeparator(fileWriter,'=');
	}


	private void drawLineSeparator(FileWriter fileWriter, char c) throws IOException {
		String row = "";
		for (int i = 0 ; i < WIDTH; i++){
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
