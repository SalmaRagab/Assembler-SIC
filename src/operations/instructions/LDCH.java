package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class LDCH extends Operation {

	public LDCH(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "50";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("ldch")) {
			return true;
		}
		return false;
	}

}
