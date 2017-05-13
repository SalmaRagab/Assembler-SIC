package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class SUB extends Operation {

	public SUB(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "1c";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("sub")) {
			return true;
		}
		return false;
	}



}
