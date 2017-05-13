package operations.operand.value;

import operations.Operation;
import operations.operand.Operand;
import operations.operand.OperandAbstract;
import tables.symbolTable.SymbolTable;

public class Char extends OperandAbstract {

	public Char(Operation operation, String operand, String[] AddressingMode, SymbolTable symbolTable) {
		super(operation, operand, AddressingMode, symbolTable);
	}

	@Override
	public boolean isType() {
		if (operand.toLowerCase().startsWith("c'")) { // charcter
			if (!operand.endsWith("'")) {
				operation.setErrorMessage("The character is missing closing single quote!");
				return false;
			}
			String temp = operand.substring(2, operand.length()-1);
			if (operand.length() > 0) {
				operand = temp;
				return true;
			} else {
				operation.setErrorMessage("The character c' ' is empty!");
				return false;
			}
		}
		return false;
	}

	@Override
	public String calObjectCode(SymbolTable symbolTable) {
		String operandAddress = this.getOperand();
		String convertedAddress = "";
		char c;
		int code;
		for (int i = 0; i < operandAddress.length(); i++) {
			code = (int) operandAddress.charAt(i);
			convertedAddress += Integer.toHexString(code);
		}
		
		return convertedAddress;
	}

	
}
