package operations.instructions;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class ADD extends Operation{


	public ADD(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = true;
		opCode = "18";
	}

	@Override
	public boolean isOperation(String[] line) {
		try {
			if (line[1].equals("add")) {
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println(line[2]);
		}
		return true;
//		if (line[1].equals("add")) {
//			return true;
//		}
//		return false;
	}



}
