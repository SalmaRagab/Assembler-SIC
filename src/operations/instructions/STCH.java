package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class STCH extends Operation {

	public STCH(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "54";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("stch")) {
			return true;
		}
		return false;
	}



}
