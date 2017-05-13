package tables.symbolTable;

public interface ISymbolTable {
	
	/**
	 * @param symbol
	 * @throws Exception when symbol is duplicate
	 */
	public void addVariable (String symbol) throws Exception;
	
	/**
	 * @param symbolName
	 * @param value
	 * @param address
	 * @throws Exception when symbol doesnot exist
	 */
	public void addAddress (String symbolName,String address);
	
	/**
	 * @param symbolName
	 * @return
	 * @throws Exception when symbol doesnot exist
	 */
	public String getValue(String symbolName) throws Exception;

	/**
	 * @param symbolName
	 * @return
	 * @throws Exception when symbol doesnot exist
	 */
	public String getAddress(String symbolName) throws Exception;

}
