package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class STA extends Operation {

	public STA(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "0c";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("sta")) {
			return true;
		}
		return false;
	}



}
