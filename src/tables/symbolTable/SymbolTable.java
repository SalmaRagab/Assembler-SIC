package tables.symbolTable;

import java.util.HashMap;

public class SymbolTable implements ISymbolTable{
	
	private HashMap<String, Symbol> table;

	
	public SymbolTable() {
		table = new HashMap<>();
	}

	@Override
	public void addVariable(String symbol) throws Exception {
		if (table.containsKey(symbol)) {
			throw new RuntimeException(); // duplicate key
		}
		table.put(symbol, null);
		
	}

	@Override
	public void addAddress(String symbolName, String address){
		if (!table.containsKey(symbolName)) {
			throw new RuntimeException();
		}
		Symbol symbol = new Symbol();
		symbol.setAddress(address);
		table.put(symbolName, symbol);
		
		
	}

	@Override
	public String getValue(String symbolName) {
		if (!table.containsKey(symbolName)) {
			throw new RuntimeException();
		}
		return table.get(symbolName).getValue();
	}

	@Override
	public String getAddress(String symbolName) {
		if (!table.containsKey(symbolName)) { // no symbol by this name
			throw new RuntimeException();
		}
		if (table.get(symbolName) == null) {
			return null;
		}

		return table.get(symbolName).getAddress();
	}

	public String[] getSymbols() {
		return (String[]) table.keySet().toArray(new String[table.size()]);
	}



}
