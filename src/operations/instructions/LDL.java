package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class LDL extends Operation {

	public LDL(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "08";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("ldl")) {
			return true;
		}
		return false;
	}

}
