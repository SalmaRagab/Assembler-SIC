package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class OR extends Operation {

	public OR(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "44";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("or")) {
			return true;
		} 
		return false;
	}

	
}
