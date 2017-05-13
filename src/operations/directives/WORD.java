package operations.directives;

import operations.Operation;
import operations.operand.value.Int;
import tables.symbolTable.SymbolTable;

public class WORD extends Operation {

	public WORD(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = true;
		hasOperand = true;
		opCode = "";
		hasLabel = true;
		CouldContainValue = true;

	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("word")) {
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean incrementReturnAddress() {
		if (operand.getOperandObject() instanceof Int) {
			if (operand.getOperand().length() > 4) {
				errorMessage = "The value [ " + operand.getOperand() + " ] is more than 4 decimal digits ( "
						+ operand.getOperand().length() + " )";
				return false;
			}
			returnAddress = address + 3 ;
			return true;
		} else {
			errorMessage = "The operand [ " + operand.getOperand() + " ] is not an integer!";
			return false;
		}
}
	

}
