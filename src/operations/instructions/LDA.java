package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class LDA extends Operation {

	public LDA(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		opCode = "00";
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("lda")) {
			return true;
		}
		return false;
	}


}
