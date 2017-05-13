package addressing;

import operations.Operation;
import tables.symbolTable.Symbol;
import tables.symbolTable.SymbolTable;

public class DirectAddressing extends AddressingAbstractClass{

	public DirectAddressing(Operation operation) {
		super(operation);
	}

	@Override
	public boolean isMode(String address) {
		this.address = address; 
		return true;
	}

	@Override
	public String isCorrect(String address) {
		String err = null;
		err = new Symbol().isValidSymbol(address);
		if (err == null) { // it is label
			addToSymbolTable(this.symbolTable, address);
		} 
		return err;
	}

	@Override
	public String calObjectCode(SymbolTable symbolTable, Operation operation) {
		String operandAddress = "";
		try {
			operandAddress = symbolTable.getAddress(this.address);
			if (operandAddress == null) {
				operation.setCorrect(false);
				operation.setErrorMessage("The label [ " + this.address + " ] does not exist!");
			}
			else { // check out of memory
				int operandAddressInInt = Integer.parseInt(operandAddress,16);
				int maxAddress = Integer.parseInt("8000",16);
				if (operandAddressInInt > maxAddress) {
					operation.setCorrect(false);
					operation.setErrorMessage("[ " + operandAddress + "] is OUT OF MEMORY!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return operandAddress;
	}

	@Override
	public void addToSymbolTable(SymbolTable symbolTable, String operand) {
		try {
			symbolTable.addVariable(operand);
		} catch (Exception e) {} // no problem as the label is defined before or used before
		
		
	}



}
