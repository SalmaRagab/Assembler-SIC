package passes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import fileHandling.reader.Reader;
import fileHandling.writer.IntermediateFileWriter;
import fileHandling.writer.SymbolTableWriter;
import operations.Operation;
import parser.Parser;
import tables.symbolTable.SymbolTable;

public class PassOne {

	private Reader reader;
	
	private Parser parser;
	
	private SymbolTable symbolTable;
	
	private ArrayList<String> lines;
	
	private ArrayList<Operation> operations;
	
	private String fileName;
	private boolean proceed;
	
	public PassOne(String fileName) throws Exception {
		this.fileName = fileName;
		parser = new Parser();
		symbolTable = new SymbolTable();
		reader = new Reader(fileName);
		operations = parser.getOperations();
		
		openFile();
		sendToParser();
		SymbolTableWriter symbolTableWriter = new SymbolTableWriter(symbolTable, fileName);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println("Symbol table created at : " + dtf.format(now));
		IntermediateFileWriter imFileWriter = new IntermediateFileWriter(operations, fileName);
		System.out.println("Intermediate file created at : " + dtf.format(now));
		
		
	}
	
	private void openFile() {
		
		try {
			reader.readFile();
			lines = reader.getFileLines();

		} catch (IOException e) {}
	}
	private void sendToParser() throws Exception {
		try {
			int index = 0;
			parser.setSize(lines.size());
			while (index < lines.size()) {
				parser.setSymbolTable(symbolTable);
				parser.parse(lines.get(index));
				index++;
			}
			operations = parser.getOperations();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	public ArrayList<Operation> getOperations() {
		return operations;
	}

	public boolean isProceed() {
		return parser.isProceed();
	}
	
}	
