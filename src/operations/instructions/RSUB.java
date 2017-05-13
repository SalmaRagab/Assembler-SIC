package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class RSUB extends Operation {

	public RSUB(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "4c";
		hasOperand = false;
		hasLabel = false;
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("rsub")) {
			return true;
		}
		return false;
	}

}
