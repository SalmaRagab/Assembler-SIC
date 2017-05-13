package operations.directives;

import operations.Operation;
import operations.operand.value.Int;
import tables.symbolTable.SymbolTable;

public class START extends Operation {

	public START(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = true;
		opCode = null;
		CouldContainValue = true;
	}

	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals("start")) {
			if(line[2] != null) {
				hasOperand = true;
			} else {
				hasOperand = false;
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean incrementReturnAddress() {
		returnAddress = address;
		return true;
	}
	
	@Override 
	public void process() {
		if (operand.getOperandObject() instanceof Int) { // address written in integer
			address = Integer.parseInt((operand.getOperand()), 16);
			incrementReturnAddress();
		} else {
			isCorrect = false;
			errorMessage = "Start could only take integers as address!";
		}
	}

}
