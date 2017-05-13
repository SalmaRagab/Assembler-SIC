package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class TD extends Operation {

	public TD(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "e0";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("td")) {
			return true;
		}
		return false;
	}



}
