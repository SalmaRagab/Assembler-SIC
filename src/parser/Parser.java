package parser;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import classLoader.JavaClassLoader;
import operations.NotOperation;
import operations.Operation;
import operations.directives.END;
import operations.directives.START;
import operations.instructions.ADD;
import tables.symbolTable.SymbolTable;

public class Parser {
	/*
			1–8 label
			9 blank
			10–15 operation code
			16–17 blank
			18–35 operand
			36–66 comment
	 */
	
	private Operation operation;
	
	private JavaClassLoader loader;
	
	private String[] line;
	
	private int address;
	
	private SymbolTable symbolTable;
	
	private ArrayList<Operation> operations;

	private boolean comment;
	private boolean startChecked; //To check if the first line -After a comment- is START
	private boolean endChecked; //To check if the first line -After a comment- is START
	private boolean startAvailable; //To check if the first line -After a comment- is START

	
	private boolean proceed;
	
	private String[] addressingMode;
	private String[] instructionClassesNames;
	private String[] directiveClassesNames;

	private int size;
	
	
	
	public Operation getOperation() {
		return operation;
	}
	
	public ArrayList<Operation> getOperations() {
		return operations;
	}
	
	public Parser() throws Exception {
		proceed = true;
		loader = new JavaClassLoader();
		operations = new ArrayList<>();
		comment = false;
		startChecked = false;
		endChecked = false;
		startAvailable = true;
		address = 0;
		addressingMode = getListOfAdressingClass();
		instructionClassesNames = getListOfInstructionClasses();
		directiveClassesNames = getListOfDirectiveClasses();
		
	}
	
	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	
	public void parse(String lineToParse) throws Exception {
		splitLine(lineToParse);
		if (!comment) {
			if (line[1] == null) {
				operation = new NotOperation(lineToParse);
				operation.setCorrect(false);
				operation.setErrorMessage("There is no operation in the operation field");
				operations.add(operation);
				proceed = false;
			} else {
				checkOperation();
			}
		} else {
			operation = new NotOperation(lineToParse);
			operation.setComment(true);
			operation.setAddress(address);
			
			if (operations.size() == (size + 1)) {
				if (!endChecked) {
					operation.setErrorMessage("There should be an END to the program!!!!");
					operation.setCorrect(false);
					proceed = false;
				}
			}
			
			operations.add(operation);
		}

	}

	private void splitLine(String lineToParse) {
		lineToParse = lineToParse.replaceFirst("\\s++$", "");
		if (lineToParse.length() <= 66) { //kda sa7
			line = new String[4];
		} else { //error
			line = new String[4];
			line[3] = "ERROR";
		}
		
		if ((lineToParse.trim().length() == 0) || (lineToParse.charAt(0) == '.')) {
			comment = true;
			return;
		} else {
			comment = false;
		}
		
		for (int i = 0; i < lineToParse.length(); i++) {
			char c = lineToParse.charAt(i);
			  if (c == '\t') {
				    if (i != 0) {
					    lineToParse = lineToParse.substring(0, i) 
					    		+ "    " + lineToParse.substring(i+1);

				    } else {
					    lineToParse = "    " + lineToParse.substring(i+1, lineToParse.length());

				    }
			  }
		}
		
		if (lineToParse.length() <= 15) { //no operand 
			line[0] = lineToParse.substring(0, 8);
			line[1] = lineToParse.substring(9, lineToParse.length()).toLowerCase();
			line[2] = null;
			line[3] = null;
		} else if (lineToParse.length() <= 35) { //no comment
			line[0] = lineToParse.substring(0, 8);
			line[1] = lineToParse.substring(9, 15).toLowerCase();
			line[2] = lineToParse.substring(17, lineToParse.length());
			line[3] = null;

		} else {
			line[0] = lineToParse.substring(0, 8);
			line[1] = lineToParse.substring(9, 15).toLowerCase();
			line[2] = lineToParse.substring(17, 35);
			line[3] = lineToParse.substring(35, lineToParse.length());
		}
		
		for(int i = 0; i < 4; i++) {
			if (line[i] != null) {
				line[i] = line[i].trim();
				if (line[i].length() == 0) {
					line[i] = null;
	 			}
			}
		}
	}
	
	private void checkOperation(){
		boolean isInstruction = loadClassesAndCheck("instructions" , instructionClassesNames);
		boolean isDirective = false;
		if (!isInstruction) {
			isDirective = loadClassesAndCheck("directives", directiveClassesNames);
		}
		if ((!isInstruction) && (!isDirective)) { //error
			operation = new NotOperation(address, symbolTable);
			operation.setLine(line);
			operation.setErrorMessage("The word [" + line[1] + "] is neither an instruction nor a directive!");
			operation.setCorrect(false);
			operations.add(operation);
			proceed = false;
		}
	}

	private boolean loadClassesAndCheck(String type, String[] classesNames){
		int j = 0; //mashy m3 el array of lines
		for (int i = 0; i < classesNames.length; i++) {
			Class loadedClass = loader.loadOperationClass(type ,classesNames[i]);
			try {
				operation = (Operation) loadedClass.getDeclaredConstructor(
						int.class, SymbolTable.class).newInstance(address, symbolTable);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			if (operation.isOperation(line)) {
				
				
				operation.setAddressingMode(addressingMode);
				operation.checkOperation(line);
				
				if (!endChecked) {
					if (!startChecked) {
						checkStart();
					} else { // program already contains start -- no other start allowed
						if (operation instanceof START) {
							operation.setCorrect(false);
							operation.setErrorMessage("The program already contains start directive");
							startAvailable = false;
							proceed = false;
						}
					}
				} else { //there is an end, no other ends are supposed to be there
					if (operation instanceof NotOperation) {
						
					} else {
						operation.setErrorMessage("There should only be one END for the program!");
						operation.setCorrect(false);
						proceed = false;
					}
				}
				if ((operation instanceof END) && !endChecked) {
					endChecked = true;
				}
				
				operations.add(operation);
				
				if ((!startAvailable) && (operations.size() == size)) {
					size = size + 1; //Because there is a NOT OPERATION added to the first entry by default.
				}
				
				if (operations.size() == size) {
					if (!endChecked) {
						operation.setErrorMessage("There should be an END statement!");
						operation.setCorrect(false);
						proceed = false;
					}
				}
				
				
				address = operation.getReturnAddress();
				
				if (!operation.isCorrect()) {
					proceed = false;
				}
//				break;
				return true;
			}
		}
		return false;
	}

	private void checkStart() {
		if (operations.size() != 0) {
			if (!line[1].toLowerCase().equals("start")) { //error
				Operation operation1 = new NotOperation();
				operation1.setErrorMessage("Your program should begin with a \"START\"!");
				operation1.setLine(line);
				operation1.setCorrect(false);
				proceed = false;
				operations.add(operation1);
				startChecked = true;
				startAvailable = false;
			}
		} else {
			for (Operation op : operations) {
				if (op instanceof NotOperation) { //comment
					
				} else { //not, therefore error
					operation = new NotOperation();
					operation.setErrorMessage("Your program should begin with a \"START\"!");
					operation.setLine(line);
					operation.setCorrect(false);
					proceed = false;
					operations.add(operation);
					proceed = false;
					startChecked = true;
					startAvailable = false;
					break;
				}
			}
			if ((!startChecked) && (!line[1].toLowerCase().equals("start"))) { //error
				Operation operation1 = new NotOperation();
				operation1.setErrorMessage("Your program should begin with a \"START\"!");
				operation1.setLine(line);
				operation1.setCorrect(false);
				proceed = false;
				operations.add(operation1);
				proceed = false;
				startChecked = true;
				startAvailable = false;
			}
			
		};		startChecked = true;
	}

	private String[] getListOfDirectiveClasses() {
		File folder = new File("src\\operations\\directives");
		File[] listOfFiles = folder.listFiles();
		String[] directiveNames = new String[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
		        directiveNames[i] = listOfFiles[i].getName().replaceAll(".java", "");
		      } else if (listOfFiles[i].isDirectory()) {
		      }
		}	
		return directiveNames;
	}

	private String[] getListOfInstructionClasses() {

		File folder = new File("src\\operations\\instructions\\");
		File[] listOfFiles = folder.listFiles();
		String[] instructionNames = new String[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				instructionNames[i] = listOfFiles[i].getName().replaceAll(".java", "");
			} else if (listOfFiles[i].isDirectory()) {
			}
		}		
		return instructionNames;
	}
	
	private String[] getListOfAdressingClass() {

		File folder = new File("src\\addressing\\");
		File[] listOfFiles = folder.listFiles();
		String[] addressingModes = new String[listOfFiles.length];

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				addressingModes[i] = listOfFiles[i].getName().replaceAll(".java", "");
			} else if (listOfFiles[i].isDirectory()) {
			}
		}		
		return addressingModes;
	}

	public boolean isProceed() {
		return proceed;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
}