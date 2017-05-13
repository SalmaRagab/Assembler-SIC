package operations.operand.value;

import operations.Operation;
import operations.operand.OperandAbstract;
import tables.symbolTable.SymbolTable;

public class Int extends OperandAbstract{

	public Int(Operation operation, String operand, String[] AddressingMode, SymbolTable symbolTable) {
		super(operation, operand, AddressingMode, symbolTable);
	}

	@Override
	public boolean isType() {
		try {
			Integer.parseInt(operand);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String calObjectCode(SymbolTable symbolTable) {
		return Integer.toHexString(Integer.parseInt((this.getOperand())));
	}

}
