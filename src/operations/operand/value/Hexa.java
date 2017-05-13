package operations.operand.value;

import operations.Operation;
import operations.operand.OperandAbstract;
import tables.symbolTable.SymbolTable;

public class Hexa extends OperandAbstract {

	public Hexa(Operation operation, String operand, String[] AddressingMode, SymbolTable symbolTable) {
		super(operation, operand, AddressingMode, symbolTable);
	}

	@Override
	public boolean isType() {
		if (operand.toLowerCase().startsWith("x'")) { //hexadecimal
			if (!operand.endsWith("'")) {
				operation.setErrorMessage("The hexa is missing closing single quote!");
				return false;			
			}
			String temp = operand.substring(2, operand.length()-1);
			if (operand.length() > 0) {
				try {
					Integer.parseInt(temp, 16);
					operand = temp;
					return true;
				} catch (Exception e) {
					operation.setErrorMessage("The hexadecimal [ " + operand + " ] is not correct!");
					return false;
				}
			} else {
				operation.setErrorMessage("The hexa x' ' is empty!");
				return false;
			}
		} 
		return false;
	}

	@Override
	public String calObjectCode(SymbolTable symbolTable) {
		return  this.getOperand();
	}

}
