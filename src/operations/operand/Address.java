package operations.operand;

import addressing.AddressingAbstractClass;
import addressing.HexaDecimalAddressing;
import classLoader.JavaClassLoader;
import operations.Operation;
import tables.symbolTable.SymbolTable;

public class Address extends OperandAbstract {
	
	private AddressingAbstractClass addressing;
	private boolean isHexa;

	public Address(Operation operation, String operand, String[] AddressingMode, SymbolTable symbolTable) {
		super(operation, operand, AddressingMode, symbolTable);
	}

	@Override
	public boolean isType() {
		addressing = findAddressingMode();	
		addressing.setSymbolTable(symbolTable);
		String err = addressing.isCorrect(operand);
		if (err != null) {
			operation.setErrorMessage(err);
			return false;
		} else {
			operand = addressing.getAddress();
			return true;
		}
		
	}


	private AddressingAbstractClass findAddressingMode() {
//		load addressing class
		 AddressingAbstractClass addressing = null;
		 JavaClassLoader classLoader = new  JavaClassLoader();
		 Class loadedClass;
		 boolean found = false;
		 int tempDirect = 0;
		 for (int i = 0; i < AddressingMode.length ; i++) {
			 if (AddressingMode[i].equals("DirectAddressing") && i != AddressingMode.length-1){
				 tempDirect = i;
				 continue;
				 } // skip 
			 if (!AddressingMode[i].equals("AddressingAbstractClass")) { // if it was abstract skip it
				 loadedClass = classLoader.loadAddressingClass(AddressingMode[i]);
				 try {
					addressing = (AddressingAbstractClass) loadedClass.getDeclaredConstructor(Operation.class).newInstance(operation);
					if(addressing.isMode(operand)) {
//						found addressing mode
						found = true;
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			 }
		 }
		 if (!found) {
//				try temp (direct) 
				 loadedClass = classLoader.loadAddressingClass(AddressingMode[tempDirect]);
				 try {
					addressing = (AddressingAbstractClass) loadedClass.getDeclaredConstructor(Operation.class).newInstance(operation);
					addressing.isMode(operand);
				} catch (Exception e) {}
		 }
		 
		 if (addressing instanceof HexaDecimalAddressing) {
			 isHexa = true;
		 }
		 
		 return addressing;
	}


	public String getOperand() {
		return operand;
	}

	public boolean isHexa() {
		return isHexa;
	}

	public AddressingAbstractClass getAddressing() {
		return addressing;
	}

	@Override
	public String calObjectCode(SymbolTable symbolTable) {
		return addressing.calObjectCode(symbolTable, operation);
	}

}
