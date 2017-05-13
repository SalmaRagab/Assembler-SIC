package operations;

import tables.symbolTable.Symbol;
import tables.symbolTable.SymbolTable;

public class Label {
	
	private Operation operation;
	private SymbolTable symbolTable;
	private String label;
	private int address;
	
	

	
	public Label(Operation operation, SymbolTable symbolTable, String label, int address) {
		this.operation = operation;
		this.symbolTable = symbolTable;
		this.label = label;
		this.address = address;
	}




	public boolean isCorrect() {
		String err = new Symbol().isValidSymbol(label);
		if (err != null) {// label  is not correct
			operation.errorMessage = err;
			 return false;
		} else { // valid var try add it to symbol table
			try {
				symbolTable.addVariable(label);
				symbolTable.addAddress(label, Integer.toHexString(address));
				return true;
			} catch (Exception e) { // duplicate --> check if it has an address
				if (symbolTable.getAddress(label) == null) { // it was used before but not defined ->> no problem
					symbolTable.addAddress(label,Integer.toHexString(address));
					return true;
				} else {
					operation.errorMessage = "The label [ " + label + " ] already defined at address " + 
					symbolTable.getAddress(label).toUpperCase();
					return false;
				}
			}
		}
	}
}
