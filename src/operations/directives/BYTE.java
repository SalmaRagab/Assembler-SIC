package operations.directives;

import operations.Operation;
import operations.operand.value.Char;
import operations.operand.value.Hexa;
import tables.symbolTable.SymbolTable;

public class BYTE extends Operation {
	
	
	public BYTE(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = true;
		opCode = "";
		hasLabel = true;
		CouldContainValue = true;
	}


	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("byte")) {
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean incrementReturnAddress() {
		int len;
		if (isCorrect) {
			len = operand.getOperand().length();
			if (operand.getOperandObject() instanceof Hexa) {
				returnAddress = (int) (address + Math.ceil((len * 0.5))) ;
				return true;
			} else if (operand.getOperandObject() instanceof Char) {
				returnAddress = address + len ;
				return true;
			} else {
				errorMessage = "The operand [ " + operand.getOperand() + " ] is not an hexa or char!";
				return false;
			}
		} else {
			returnAddress = address + 3 ;
			return false;
		}
}
}
