package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class AND extends Operation {

	public AND(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "40";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("and")) {
			return true;
		} 
		return false;
	}

}
