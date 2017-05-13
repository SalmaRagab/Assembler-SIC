package operations.operand;

import addressing.AddressingAbstractClass;
import addressing.HexaDecimalAddressing;
import operations.Operation;
import operations.operand.value.Char;
import operations.operand.value.Hexa;
import operations.operand.value.Int;
import tables.symbolTable.Symbol;
import tables.symbolTable.SymbolTable;

public abstract class OperandAbstract {
	
	protected Operation operation;
	protected String operand;
//	protected boolean isAddress;
	protected String[] AddressingMode;
	protected SymbolTable symbolTable;
	protected OperandAbstract operandObject;
	

	
	public OperandAbstract(Operation operation, String operand,String[] AddressingMode, SymbolTable symbolTable) {
		this.operation = operation;
		this.operand = operand;
//		isAddress = false;
		this.AddressingMode = AddressingMode;
		this.symbolTable = symbolTable;
	}

	
	public abstract boolean isType();
	public boolean isCorrect() {
		
		if (isAddress()) { // check addressing modes 
			return true;
		} 
//		if (isLabel()) {
//			operation.setErrorMessage(null); 
//			return true;
//		}
		if (operation.couldContainValue()) { // check values classes
			if (isValue()) {
				operation.setErrorMessage(null); // remove err msg from value
				return true;
			}
		} 
		return false;
		
		
	}
	
	private boolean isValue() {
		
//		value classes >> char - hexa - int
		operandObject = new Int(operation, operand, AddressingMode, symbolTable);
		if (operandObject.isType()) {
			return true;
		}
		operandObject = new  Char(operation, operand, AddressingMode, symbolTable);
		if (operandObject.isType()) {
			operand = operandObject.getOperand();
			return true;
		}
		operandObject = new Hexa(operation, operand, AddressingMode, symbolTable);
		if (operandObject.isType()) {
			operand = operandObject.getOperand();
			return true;
		}
		
		return false;
	}
	
	private boolean isAddress() {
		
		operandObject = new Address(operation, operand, AddressingMode, symbolTable);
		if (operandObject.isType()){
			operand = operandObject.getOperand();
			return true;
		}
		
		
		return false;
	}
	
	private boolean isLabel() {
		
		String err = new Symbol().isValidSymbol(operand);
		if (err == null) {
			try {
				symbolTable.addVariable(operand);
			} catch (Exception e) {} // no problem as the label is defined before or used before
			return true;
		} else {
			operation.setErrorMessage(err);
			return false;
		}
		
		
	}

	public String getOperand() {
		return operand;
	}


	public OperandAbstract getOperandObject() {
		return operandObject;
	}

	public abstract String calObjectCode(SymbolTable symbolTable);
	
	
	
		
}
