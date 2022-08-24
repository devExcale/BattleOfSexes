package it.uniroma1.studenti.battleofsexes.models;

import it.uniroma1.studenti.battleofsexes.beans.PayoffTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Woman {

	private final Type type;

	/**
	 * Based on payoffs, a woman may prefer some men to other men.
	 * This method calculates the preferences of the woman for some men,
	 * and returns only the best ones.
	 *
	 * @param men an array of men to choose from
	 * @return preferred men
	 */
	public Set<Man> preferredMen(Set<Man> men) {

		PayoffTable payoffs = PayoffTable.getInstance();

		Map<Float, Set<Man>> rankings = men.stream()
				.collect(Collectors.groupingBy(man -> payoffs.womanToMan(type, man.getType()), Collectors.toSet()));

		// Ignore null payoffs
		rankings.remove(0f);

		// Get max payoff value
		Optional<Float> maxPayoff = rankings.keySet()
				.stream()
				.max(Float::compare);

		// No compatible men check (all payoffs are null)
		if(!maxPayoff.isPresent())
			return Collections.emptySet();

		// Return the set containing the highest ranking men
		return rankings.get(maxPayoff.get());

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
