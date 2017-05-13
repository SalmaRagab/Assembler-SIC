package operations.operand;

import addressing.AddressingAbstractClass;
import classLoader.JavaClassLoader;
import operations.Operation;
import tables.symbolTable.Symbol;
import tables.symbolTable.SymbolTable;

public class Operand extends OperandAbstract{
	
	public Operand(Operation operation, String operand,String[] AddressingMode, SymbolTable symbolTable) {
		super(operation, operand, AddressingMode, symbolTable);
	}



	@Override
	public boolean isType() { // has no effect
		return false;
	}



	@Override
	public String calObjectCode(SymbolTable symbolTable) {
		return null;
	}

	




}
