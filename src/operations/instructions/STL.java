package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class STL extends Operation {

	public STL(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "14";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("stl")) {
			return true;
		}
		return false;
	}



}
