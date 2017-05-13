package addressing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public abstract class AddressingAbstractClass {
	
	protected String address;
	protected SymbolTable symbolTable;
	protected Operation operation;
	
	
	
	
	public AddressingAbstractClass(Operation operation) {
		this.operation = operation;
	}

	public abstract boolean isMode(String address);
	
	public abstract String isCorrect(String address);
	
	public abstract void addToSymbolTable(SymbolTable symbolTable, String operand);

	public String getAddress() {
		return address;
	}

	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}
	
	public abstract String calObjectCode(SymbolTable symbolTable, Operation operation);
	
	

		
	

}
