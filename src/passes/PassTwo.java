package passes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import fileHandling.writer.IntermediateFileWriter;
import fileHandling.writer.ListingFileWriter;
import fileHandling.writer.ObjectCodeWriter;
import operations.Operation;
import operations.directives.BYTE;
import operations.operand.Address;
import operations.operand.Operand;
import operations.operand.OperandAbstract;
import operations.operand.value.Char;
import operations.operand.value.Hexa;
import operations.operand.value.Int;
import tables.symbolTable.SymbolTable;

public class PassTwo {
	
	private ArrayList<Operation> operations;
	private SymbolTable symbolTable;
	private String fileName;
	private boolean proceed;

	public PassTwo(ArrayList<Operation> operations, SymbolTable symbolTable, String fileName, boolean proceed) {
		this.proceed = proceed;
		this.operations = operations;
		this.symbolTable = symbolTable;
		this.fileName = fileName;
		
		if(this.proceed) {
			generateOpCode();
			ListingFileWriter listingFileWriter = new ListingFileWriter(operations, fileName);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			System.out.println("Listing file created at : " + dtf.format(now));
		}
		if (this.proceed) {
			ObjectCodeWriter objectCodeWriter = new ObjectCodeWriter(symbolTable, operations, fileName);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			System.out.println("Object file created at : " + dtf.format(now));
		}
	}



	private void generateOpCode() {
		String opCode;
		OperandAbstract operand;
		
		String operandAddress = null;
		for (Operation operation : operations) {
			operandAddress = "";
			opCode = "";
			opCode = operation.getOpCode();
			operand = operation.getOperand();
			operandAddress = getOperandAddress(operand, operation); // in hexa
			if (!operation.isCorrect() && !operation.isComment()) { // out of memory or not defined label
				proceed = false;
			}
			calculateOpCode(opCode, operandAddress, operation);
			
			
		}
		
	}
	
	private void calculateOpCode(String opCode, String operandAddress, Operation operation) {
		int generatedCode;
		if (opCode != null) {
			if (operandAddress == null) {operandAddress = "";}
			int length = opCode.length() + operandAddress.length();

			opCode = fillOpCode(opCode,6);	
			operandAddress = fillAddress(operandAddress,length);
			int tempLength = operandAddress.length();
			generatedCode = Integer.parseInt(opCode, 16) | Integer.parseInt(operandAddress, 16);
			opCode = Integer.toHexString(generatedCode);
			if (! (operation instanceof BYTE)) {
				opCode = fillAddress(opCode, 6);
			}
			opCode = fillAddress(opCode, tempLength);
			operation.setOpCode(opCode);
		}
	}


	private String getOperandAddress(OperandAbstract operand, Operation operation) {
		String operandAddress = "";
		OperandAbstract object;
		if (operand != null) {
			object = operand.getOperandObject();
			operandAddress = object.calObjectCode(symbolTable);
		}
		return operandAddress; // in hexa decimal
		
	}


	private String fillOpCode(String opCode, int length) {
		if (opCode == null) {
			opCode = "";
		}
		for (int i = opCode.length(); i < length; i++) {
			opCode += "0";
		}
		return opCode;
	}
	
	private String fillAddress(String opCode, int length) {
		if (opCode == null) {
			opCode = "";
		}
		for (int i = opCode.length(); i < length; i++) {
			opCode = "0" + opCode;
		}
		return opCode;
	}


}
