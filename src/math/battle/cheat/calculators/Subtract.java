package math.battle.cheat.calculators;

import java.math.BigDecimal;

public class Subtract implements Calculator {

	@Override
	public boolean isAnswerCorrect(String left_side, String right_side, String answer) {
		return (new BigDecimal(left_side).subtract(new BigDecimal(right_side))).equals(new BigDecimal(answer));
	}

}
