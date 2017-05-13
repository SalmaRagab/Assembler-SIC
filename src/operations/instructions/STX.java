package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class STX extends Operation {

	public STX(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "10";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("stx")) {
			return true;
		}
		return false;
	}



}
