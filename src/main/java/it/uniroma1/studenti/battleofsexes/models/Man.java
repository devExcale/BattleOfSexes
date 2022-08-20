package it.uniroma1.studenti.battleofsexes.models;

import it.uniroma1.studenti.battleofsexes.BattleOfSexesApplication;
import it.uniroma1.studenti.battleofsexes.beans.PayoffTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Man {

	private final Type type;

	public Woman expressPreference(Woman w1, Woman w2) {

		PayoffTable payoffs = BattleOfSexesApplication.getPayoffs();

		float p1 = payoffs.manToWoman(type, w1.getType());
		float p2 = payoffs.manToWoman(type, w2.getType());

		if(p1 == 0 && p2 != 0)
			// Payoff 1 is null and payoff 2 isn't null
			return w2;

		else if(p2 == 0 && p1 != 0)
			// Payoff 2 is null and payoff 1 isn't null
			return w1;

		else if(p1 == 0)
			// Both payoffs are null
			return null;

		else
			// Return woman with higher payoff
			return (p1 > p2) ? w1 : w2;

	}

	public enum Type {

		FAITHFUL("F"),
		PHILANDERER("P");

		@Getter
		private final String code;

		Type(String code) {
			this.code = code;
		}

	}

}
