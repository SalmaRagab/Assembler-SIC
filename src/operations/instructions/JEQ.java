package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class JEQ extends Operation {

	public JEQ(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "30";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("jeq")) {
			return true;
		}
		return false;
	}


}
