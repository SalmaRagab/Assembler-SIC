package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class LDX extends Operation {

	public LDX(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "04";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("ldx")) {
			return true;
		}
		return false;
	}

}
