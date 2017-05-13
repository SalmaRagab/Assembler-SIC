package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class MUL extends Operation {

	public MUL(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "20";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("mul")) {
			return true;
		}
		return false;
	}
}
