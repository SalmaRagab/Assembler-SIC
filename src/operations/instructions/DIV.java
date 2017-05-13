package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class DIV extends Operation {

	public DIV(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		
		isDirective = false;
		opCode = "24";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("div")) {
			return true;
		}
		return false;
	}


}
