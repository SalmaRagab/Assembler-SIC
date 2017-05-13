package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class RD extends Operation {

	public RD(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "d8";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("rd")) {
			return true;
		}
		return false;
	}


}
