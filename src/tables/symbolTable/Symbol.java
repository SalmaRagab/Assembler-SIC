package tables.symbolTable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Symbol {
	
	private String value;
	private String address;
	private int length;
	
	/**
	 * Checks if the operand is a valid one or no,
	 * it will check that this operand isn't one of the reserved words
	 * read from a file containing all the reserved words,
	 * also it will check for other naming conventions.
	 * @param operand the operand to be checked.
	 * @return true if it follows the naming convention.
	 * @throws Exception 
	 */
	public String isValidSymbol(String operand)  {
		try {
//			check if it is from reserved words
			FileInputStream fstream = new FileInputStream("./resources/Reserved Words.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				if (operand.equals(strLine)) {
					return "The lable [ " + operand + " ] cannot be from reserved words [ " + strLine + " ]!";
				}
			}
			br.close();
		} catch (IOException e) {}
	
		if (operand.contains("'") || operand.contains("\\") || operand.contains("_") || 
				operand.contains("-") || operand.contains("+") || operand.contains("*") ||
				operand.contains("/")) {
			return "Label cannot contain such characters [', \\, _, -, +, /, *]";
		}
//		check if it starts with a number
		try {
			Integer.parseInt(operand.substring(0, 1));
			return "The Label [ " + operand + " ] cannot start with a number!";
		} catch (Exception e) {}

		return null;

	}

	public String getValue() {
		return value;
	}
	public String getAddress() {
		return address;
	}
	public int getLength() {
		return length;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLenght(int lenght) {
		this.length = lenght;
	}
	
	

	
	

}
