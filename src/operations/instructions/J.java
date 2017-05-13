package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class J extends Operation {

	public J(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "3c";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("j")) {
			return true;
		}
		return false;
	}


}
