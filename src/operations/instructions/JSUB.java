package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class JSUB extends Operation {

	public JSUB(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "48";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("jsub")) {
			return true;
		}
		return false;
	}

}
