package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class WD extends Operation {

	public WD(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "dc";
		CouldContainValue = false;
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("wd")) {
			return true;
		}
		return false;
	}
}
