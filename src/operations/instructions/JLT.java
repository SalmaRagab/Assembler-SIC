package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class JLT extends Operation {

	public JLT(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "38";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("jlt")) {
			return true;
		}
		return false;
	}



}
