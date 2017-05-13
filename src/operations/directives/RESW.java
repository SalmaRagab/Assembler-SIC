package operations.directives;

import operations.Operation;
import operations.operand.Operand;
import operations.operand.value.Int;
import tables.symbolTable.SymbolTable;

public class RESW extends Operation {

	public RESW(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = true;
		hasOperand = true;
		opCode = null;
		hasLabel = true;
		CouldContainValue = true;

	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("resw")) {
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean incrementReturnAddress() {
		int number;
			if (operand.getOperandObject() instanceof Int) {
				number = Integer.parseInt(operand.getOperand());
				if (operand.getOperand().length() > 4) {
					
					errorMessage = "The value [ " + number + " ] is more than 4 decimal digits ( "
							+ operand.getOperand().length() + " )";
					return false;
				}
				returnAddress = address + 3 * number;
				return true;
			} else {
				errorMessage = "The operand [ " + operand.getOperand() + " ] is not an integer!";
				return false;
			}
	}
			
}
