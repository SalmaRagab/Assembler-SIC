package operations;

import tables.symbolTable.SymbolTable;

public class NotOperation extends Operation {

	private String comment;
	
	public NotOperation(int address, SymbolTable symbolTable) {
		super(address, symbolTable);
		isDirective = false;
		hasOperand = false;
		opCode = null;
		isCorrect = false;
	}

	public NotOperation() {
		// TODO Auto-generated constructor stub
	}
	
	public NotOperation(String comment) {
		super(comment);
		this.comment = comment;
		isComment = true;
	}
	
	@Override
	public boolean isOperation(String[] line) {
		if (line[1].equals(null)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void checkOperation (String[] line) {
		errorMessage =  " The operatio [ " + line[1] + " ] is not valid";
		isCorrect = false;
		
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



}
