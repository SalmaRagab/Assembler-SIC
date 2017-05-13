package operations.directives;

import operations.Operation;
import operations.operand.Operand;
import operations.operand.value.Hexa;
import operations.operand.value.Int;
import tables.symbolTable.SymbolTable;

public class RESB extends Operation {

	private int size;
	
	public RESB(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = true;
		hasOperand = true;
		opCode = null;
		hasLabel = true;
		CouldContainValue = true;

	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("resb")) {
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
			returnAddress = address +  number;
			return true;
		} else {
			errorMessage = "The operand [ " + operand.getOperand() + " ] is not an integer!";
			return false;
		}
}

}
