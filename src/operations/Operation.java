package operations;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import addressing.AddressingAbstractClass;
import classLoader.JavaClassLoader;
import operations.operand.Operand;
import operations.operand.OperandAbstract;
import tables.symbolTable.Symbol;
import tables.symbolTable.SymbolTable;

public abstract class Operation {
	
	protected boolean CouldContainValue;
	protected boolean isDirective;
	protected boolean hasOperand;
	protected boolean hasLabel;
	protected String opCode;
	protected String[] line;
	
	protected int address;
	protected int returnAddress;
	protected OperandAbstract operand;
	protected Label label;
	
	protected SymbolTable symbolTable;
	
	protected boolean isCorrect;
	protected boolean isComment;
	protected String errorMessage;
	
	private String[] AddressingMode;
	private String comment;
	private AddressingAbstractClass addressing;


	
	public Operation() {
	}
	
	public Operation getOperation() {
		return this;
	}
	
	public Operation(String comment) {
		this.comment = comment;
		isComment = true;
	}
	
	public Operation(int address, SymbolTable symbolTable) {
		this.address = address;
		this.symbolTable = symbolTable; // to add any new symbol 
		this.returnAddress = address;
		hasOperand = true;
		isCorrect = true;
		hasLabel = false; // by default the label is optional 
		CouldContainValue = false;
	}
	
	public abstract boolean isOperation (String[] line);

	/**
	 * @param line the line to be converted to opcode
	 * @return true if instruction or directive was correct and.
	 * labels  / address was added correctly
	 * @throws Exception 
	 */
	
	/**
	 * Some directives and instructions requires additional 
	 * tasks to be done , this function proceed after CheckOperation
	 */
	public void process() {} // mainly does not do any thing
	
	public void checkOperation (String[] line) {
		this.line = line; // keep the line to be printed later
		if (!(line[3] == null )) { //comment or error
			if (line[3].equals("ERROR")) {
				errorMessage = "The line length exceeded the allowed length!";
				isCorrect = false;
			}
		}
		if (!(line[0] == null)) { // label field exist
			label = new Label(this, symbolTable, line[0], address);
			if (!label.isCorrect()) {
				isCorrect = false;
//				incrementReturnAddress();
			}
		} 
		else {
			if (hasLabel) { // the operation needs a label and it does not exist
				errorMessage = "This operation is missing a label!";
				isCorrect = false;
				incrementReturnAddress();
			}
		}
		if (!(line[2] == null)) { // has operand
			if (hasOperand) { // the operation should  have an operand field
				operand = new Operand(this, line[2], AddressingMode, symbolTable);
				if (!operand.isCorrect()) {
					isCorrect = false;
					incrementReturnAddress();
					return;
				}
			} else { // the user wrote an operand and the instruction cannot have an operand
				errorMessage = "the operation  [ " + line[1] + " ] can not have an operand!";
				isCorrect = false;
				incrementReturnAddress();
				return;
			}
		} else {
			if (hasOperand) { // the operation should have operand and user did not write it
				errorMessage = "the operation  [ " + line[1] + " ] should have an operand!";
				isCorrect = false;
				incrementReturnAddress();
				return;
			}
		}
		
		isCorrect = incrementReturnAddress();
		process();
		return;
	}
	
	protected boolean incrementReturnAddress() {
		returnAddress = address + 3;
		return true;
	}
	
	
	public boolean isDirective() {
		return isDirective;
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public String getErrorMessege() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**	the parser will need it to send it to next instruction
	 * @return the address of this instruction + increment value determined by each operation
	 */
	public int getReturnAddress() {
		return returnAddress;
	}

	public void setAddressingMode(String[] addressingMode) {
		AddressingMode = addressingMode;
	}

	public OperandAbstract getOperand() {
		return operand;
	}

	public void setOperand(OperandAbstract operand) {
		this.operand = operand;
	}

	public boolean couldContainValue() {
		return CouldContainValue;
	}

	public boolean isComment() {
		return isComment;
	}

	public void setComment(boolean isComment) {
		this.isComment = isComment;
		isCorrect = true;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}

	public String[] getLine() {
		if (line == null) { // comment
			return null;
		}
		String[] newLine = new String[line.length];
		for (int i = 0; i < line.length; i++) {
			if (line[i] == null) {
				newLine[i] = "";
			} else {
				newLine[i] = line[i];
			}
			
		}
		return newLine;
	}

	public void setLine(String[] line) {
		this.line = line;
	}
	
	
}
