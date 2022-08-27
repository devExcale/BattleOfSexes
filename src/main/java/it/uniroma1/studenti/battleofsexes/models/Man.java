package it.uniroma1.studenti.battleofsexes.models;

import it.uniroma1.studenti.battleofsexes.beans.PayoffTable;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class Man extends Human {

	public Man(Man.Type type) {
		super(type);
	}

	@Override
	public Man.Type getType() {
		return ((Man.Type) type);
	}

	/**
	 * Based on payoffs, a woman may prefer some men to other men.
	 * This method calculates the preferences of the woman for some men,
	 * and returns only the best ones.
	 *
	 * @param women an array of men to choose from
	 * @return preferred men
	 */
	public Set<Woman> topPreferences(Set<Woman> women) {

		PayoffTable payoffs = PayoffTable.getInstance();

		// Group men by their payoffs
		Map<Float, Set<Woman>> rankings = women.stream()
				.collect(Collectors.groupingBy(woman -> payoffs.get(type, woman.getType()), Collectors.toSet()));

		// Ignore null payoffs
		rankings.remove(0f);

		// Pick highest payoff
		Optional<Float> maxPayoff = rankings.keySet()
				.stream()
				.max(Float::compare);

		// No compatible men check (all payoffs are null)
		if(maxPayoff.isEmpty())
			return Collections.emptySet();

		// Return the set containing the men with highest payoff
		return rankings.get(maxPayoff.get());

	}

	public enum Type implements GeneType {

		FAITHFUL("F"),
		PHILANDERER("P");

		@Getter
		private final String code;

		Type(String code) {
			this.code = code;
		}

	}

}
