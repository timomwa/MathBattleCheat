package math.battle.cheat;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import math.battle.cheat.calculators.Add;
import math.battle.cheat.calculators.Calculator;
import math.battle.cheat.calculators.Divide;
import math.battle.cheat.calculators.MathOperation;
import math.battle.cheat.calculators.Multiply;
import math.battle.cheat.calculators.Subtract;
import math.battle.cheat.output.Output;
import math.battle.cheat.output.VoiceOutPutWAVPlayer;

public class MathParser {

	public static Map<String, String> replacements = new HashMap<String, String>();
	public static Map<String, MathOperation> operators = new HashMap<String, MathOperation>();
	public static Map<MathOperation, Calculator> calculator_registry = new HashMap<MathOperation, Calculator>();
	//public Output voiceTTS = new VoiceOutPutWAVPlayer();
	
	static {
		initValues();
	}

	public boolean parse(String original_str) throws Exception{
		if (original_str == null || original_str.trim().isEmpty())
			return false;
		String tmp = original_str;
		String ocr_string = "";
		String left_side = "";
		String right_side = "";
		String answer = "";
		MathOperation operator = MathOperation.EQUALS;
		boolean iscorrect = false;
		
		try{
			
			original_str = clean(original_str);
			ocr_string = original_str.trim().toLowerCase();
			ocr_string = clean(ocr_string);
			
			String first_line = ocr_string.split("\\n")[0].toLowerCase();
			String second_line = ocr_string.split("\\n")[1].toLowerCase();
	
			answer = second_line.split(MathOperation.EQUALS.getSymbol().toLowerCase())[1].trim();
			
			for (Map.Entry<String, MathOperation> entry : operators.entrySet()) {
	
				String symbol = entry.getValue().getSymbol().toLowerCase();
				if (Matcher.quoteReplacement(first_line).contains(Matcher.quoteReplacement(symbol))) {
					left_side = first_line.substring(0, first_line.indexOf( symbol )).replace(symbol, "");
					right_side = first_line.substring((first_line.indexOf( symbol )+1), (first_line.length())).replace(symbol, "");
					operator = entry.getValue();
					break;
				}
	
			}
	
			
			iscorrect = calculator_registry.get(operator).isAnswerCorrect(left_side, right_side, answer);
			
			DesktopParser.consecutive_errors.set(0);
			
			return iscorrect;
		}catch(Exception ooe){
			//voiceTTS.output("error");
			DesktopParser.consecutive_errors.getAndIncrement();
			System.out.println("\n==========");
			System.out.println("tmp [" + tmp+"]");
			System.out.println("ocr_string " + ocr_string);
			System.out.println("left_side [" + left_side+"]");
			System.out.println("right_side [" + right_side+"]");
			System.out.println("answer [" + answer +"]");
			System.out.println("operator.getSymbol() " + operator.getSymbol());
			System.out.println("is correct? " + iscorrect);
			System.out.println("==========");
			ooe.printStackTrace();
			return (Math.random() < 0.5);//Coin toss lol!
		}
	}

	private static String clean(String ocr_string) {
		for(Map.Entry<String, String> entry : replacements.entrySet())
			ocr_string = ocr_string.replace( entry.getKey(), entry.getValue());
		return 	ocr_string;
	}

	private static void initValues() {
		operators.put("-", MathOperation.SUBTRACT1);
		operators.put("—", MathOperation.SUBTRACT2);
		operators.put("/", MathOperation.DIVIDE);
		operators.put("+", MathOperation.ADD);
		operators.put("X", MathOperation.MULTIPLY);
		operators.put("=", MathOperation.EQUALS);
		
		calculator_registry.put(MathOperation.SUBTRACT1, new Subtract());
		calculator_registry.put(MathOperation.SUBTRACT2, calculator_registry.get(MathOperation.SUBTRACT1));
		calculator_registry.put(MathOperation.DIVIDE, new Divide());
		calculator_registry.put(MathOperation.ADD, new Add());
		calculator_registry.put(MathOperation.MULTIPLY, new Multiply());
		
		replacements.put("!", "1");
		replacements.put("l", "1");
		replacements.put("i", "1");
		replacements.put("\"", "11");
		replacements.put("\\s", "");
		replacements.put(" ", "");
		replacements.put("o", "0");
		replacements.put("[", "1");
		replacements.put("(", "1");
		replacements.put(":", "1");
		replacements.put("s", "8");
		replacements.put("t", "1");
		//replacements.put("m", "1");
		replacements.put("?", "1");
		replacements.put("h", "11");
		replacements.put("n", "11");
		replacements.put("q", "9");
		replacements.put("><", "x");
		replacements.put("”", "1 x");
		replacements.put("e", "10");
		replacements.put(")", "");
		replacements.put("w", "17");
		replacements.put("m", "111");
		replacements.put("z", "2");
		replacements.put("u", "11");
		replacements.put("é", "6");
		replacements.put("a", "11");
		replacements.put("~", "-");


	}

}
