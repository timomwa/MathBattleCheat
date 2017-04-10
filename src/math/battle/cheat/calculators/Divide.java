package math.battle.cheat.calculators;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide implements Calculator {

	@Override
	public boolean isAnswerCorrect(String left_side, String right_side, String answer) {
		return (new BigDecimal(left_side).divide(new BigDecimal(right_side), 0, RoundingMode.HALF_EVEN)).equals(new BigDecimal(answer));
	}

}
