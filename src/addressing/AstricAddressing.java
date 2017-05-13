package addressing;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class AstricAddressing extends AddressingAbstractClass {

	public AstricAddressing(Operation operation) {
		super(operation);
	}

	@Override
	public boolean isMode(String address) {
		if (address.equals("*")) {
			return true;
		}
		return false;
	}

	@Override
	public String isCorrect(String address) {
		this.address = Integer.toHexString(operation.getAddress());
		return null;
	}

	@Override
	public void addToSymbolTable(SymbolTable symbolTable, String operand) {}

	@Override
	public String calObjectCode(SymbolTable symbolTable, Operation operation) {
		return this.address;
	}

}
