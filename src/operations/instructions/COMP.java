package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class COMP extends Operation {

	public COMP(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "28";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("comp")) {
			return true;
		}
		return false;
	}


}
