package math.battle.cheat.calculators;

import java.math.BigDecimal;

public class Multiply implements Calculator {

	@Override
	public boolean isAnswerCorrect(String left_side, String right_side, String answer) {
		return (new BigDecimal(left_side).multiply(new BigDecimal(right_side))).equals(new BigDecimal(answer));
	}

}
