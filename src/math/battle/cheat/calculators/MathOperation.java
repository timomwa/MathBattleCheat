package math.battle.cheat.calculators;

public enum MathOperation {
	
MULTIPLY("X"),DIVIDE("/"),SUBTRACT1("-"), SUBTRACT2("—"),ADD("+"), EQUALS("=");
	private String symbol;
	public String getSymbol(){
		return this.symbol;
	}
	private MathOperation(String symbol){
		this.symbol = symbol;
	}
	
	public static MathOperation fromSymbol(String symbol){
		if(symbol==null || symbol.isEmpty())
			return null;
		for(MathOperation op :  MathOperation.values())
			if(op.symbol.trim().equalsIgnoreCase(symbol))
				return op;
		return null;
	}
	

}
