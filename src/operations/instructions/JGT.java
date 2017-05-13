package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class JGT extends Operation {

	public JGT(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "34";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("jgt")) {
			return true;
		}
		return false;
	}


}
