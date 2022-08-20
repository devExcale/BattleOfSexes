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
public class Woman {

	private final Type type;

	public Man expressPreference(Man m1, Man m2) {

		PayoffTable payoffs = BattleOfSexesApplication.getPayoffs();

		float p1 = payoffs.womanToMan(type, m1.getType());
		float p2 = payoffs.womanToMan(type, m2.getType());

		if(p1 == 0 && p2 != 0)
			// Payoff 1 is null and payoff 2 isn't null
			return m2;

		else if(p2 == 0 && p1 != 0)
			// Payoff 2 is null and payoff 1 isn't null
			return m1;

		else if(p1 == 0)
			// Both payoffs are null
			return null;

		else
			// Return woman with higher payoff
			return (p1 > p2) ? m1 : m2;

	}

	public enum Type {

		COY("C"),
		FAST("S");

		@Getter
		private final String code;

		Type(String code) {
			this.code = code;
		}

	}

}
