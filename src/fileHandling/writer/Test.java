package fileHandling.writer;

import tables.symbolTable.SymbolTable;

public class Test {
	
	public static void main(String[] args) throws Exception {
		SymbolTable symbolTable = new SymbolTable();
		symbolTable.addVariable("length");
		symbolTable.addVariable("width");
		symbolTable.addVariable("height");
		symbolTable.addVariable("area");
		
		symbolTable.addAddress("length", "005E");
		symbolTable.addAddress("width", "0078E");
		symbolTable.addAddress("height", "00A9");
		symbolTable.addAddress("area", "0AFB");
		SymbolTableWriter symbolTableWriter = new SymbolTableWriter(symbolTable, "test");
		
	}

}
