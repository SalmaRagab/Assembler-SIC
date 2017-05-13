package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class TIX extends Operation {

	public TIX(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "2c";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("tix")) {
			return true;
		}
		return false;
	}


}
