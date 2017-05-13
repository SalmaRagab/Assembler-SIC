package operations.directives;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class END extends Operation {

	/*
	 line[0] >> label ---- null if it is empty
	 line[1] >> code
	 line[2] >> operand
	 line[3] >> comment
	 */
	
	public END(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = true;
		opCode = null;
	}

	@Override
	public boolean isOperation(String[] line) {
		
		if (line[1].equals("end")) {
			if (line[2] == null) { //it has an operand
				hasOperand = false;
			}
			return true;
		}
		return false;
	}


}
