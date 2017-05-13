package addressing;

import operations.Operation;
import tables.symbolTable.SymbolTable;

public class HexaDecimalAddressing extends AddressingAbstractClass{

	public HexaDecimalAddressing(Operation operation) {
		super(operation);
	}

	@Override
	public boolean isMode(String address) {
		if (address.toLowerCase().startsWith("0x")) {
			return true;
		}
		return false;
	}

	@Override
	public String isCorrect(String address) {
		String err = null;
		if (address.toLowerCase().startsWith("0x")) {
			if ((address.charAt(0) != '0') && (address.toLowerCase().charAt(1) == 'x')) {
				err = "Wrong formatted Hexadecimal address! 'No 0x'";
			}
			try {
				address = address.substring(2, address.length());
			    Integer.parseInt(address, 16);
			    err = null;
			} catch (Exception e) {
				err = "Wrong formatted Hexadecimal address [ " + address + " ]!";
			}
			
		}
		return err;

	}

	@Override
	public void addToSymbolTable(SymbolTable symbolTable, String operand) {}

	@Override
	public String calObjectCode(SymbolTable symbolTable, Operation operation) {
//		 check not out of memory
		int operandAddressInInt = Integer.parseInt(address,16);
		int maxAddress = Integer.parseInt("8000",16);
		if (operandAddressInInt > maxAddress) {
			operation.setCorrect(false);
			operation.setErrorMessage("[ " + address + "] is OUT OF MEMORY!");
		}
		return address;
	}

}
