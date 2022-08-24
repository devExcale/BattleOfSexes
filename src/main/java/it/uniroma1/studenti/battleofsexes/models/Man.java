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
public class Man {

	private final Type type;

	/**
	 * Based on payoffs, a man may prefer some women to other women.
	 * This method calculates the preferences of the man for some women,
	 * and returns only the best ones.
	 *
	 * @param women an array of women to choose from
	 * @return preferred women
	 */
	public Set<Woman> preferredWomen(Set<Woman> women) {

		PayoffTable payoffs = PayoffTable.getInstance();

		Map<Float, Set<Woman>> rankings = women.stream()
				.collect(Collectors.groupingBy(woman -> payoffs.manToWoman(type, woman.getType()), Collectors.toSet()));

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

		FAITHFUL("F"),
		PHILANDERER("P");

		@Getter
		private final String code;

		Type(String code) {
			this.code = code;
		}

	}

}
