package addressing;

import operations.Operation;
import tables.symbolTable.Symbol;
import tables.symbolTable.SymbolTable;

public class IndexedAddressing extends AddressingAbstractClass {

	public IndexedAddressing(Operation operation) {
		super(operation);
	}

	@Override
	public boolean isMode(String address) {
		if (address.toLowerCase().endsWith(",x")) {
			return true;
		}
		return false;
	}

	@Override
	public String isCorrect(String address) {
		String err;
		if (address.length() >3) { //check if before ,x is a label
			this.address = address.substring(0, address.length()-2);
			 err = new Symbol().isValidSymbol(this.address);
			if (err == null) { // valid naming conventions
				try {
					Integer.parseInt(this.address.substring(0, 1));
					err = "Indexed addressing cannot start with a number! it must be a label!";
					return err;
				} catch (Exception e) {
					addToSymbolTable(this.symbolTable, this.address);
					return null;
				}
			} else {
				return err;
			}
		} 
		return "The label cannot have only [ ,x ], it needs a label to access x";
	}

	@Override
	public void addToSymbolTable(SymbolTable symbolTable, String operand) {
		String address = operand.substring(0, operand.length());
		try {
			symbolTable.addVariable(address);
		} catch (Exception e) {} // no problem as the label is defined before or used before
		
	}

	@Override
	public String calObjectCode(SymbolTable symbolTable, Operation operation) {
		String operandAddress = "";
		try {
			operandAddress = symbolTable.getAddress(this.address);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//				check that the address not out of memory 
		int operandAddressInInt = Integer.parseInt(operandAddress,16);
		int maxAddress = Integer.parseInt("8000",16);
		if (operandAddressInInt > maxAddress) {
			operation.setCorrect(false);
			operation.setErrorMessage("[ " + operandAddress + "] is OUT OF MEMORY!");
		}
		int generatedAddress = Integer.parseInt(operandAddress, 16) | Integer.parseInt("8000", 16);
		
		
		return Integer.toHexString(generatedAddress);
	}
	
	

}
