package it.uniroma1.studenti.battleofsexes.utils;

import java.util.*;

public class ProbabilityWheel<T> {

	private final Map<T, Float> originals;

	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private final LinkedHashMap<T, Float> ranges;

	private final Random r;

	public ProbabilityWheel(Map<T, Float> probabilities) {

		float sum = probabilities.values()
				.stream()
				.reduce(Float::sum)
				.orElse(0f);

		if(sum != 100f)
			throw new IllegalArgumentException("Probabilities don't sum up to 100");

		r = new Random();

		originals = Collections.unmodifiableMap(new HashMap<>(probabilities));
		ranges = new LinkedHashMap<>();

		// Span elements
		float ps = 0f;
		for(Map.Entry<T, Float> e : originals.entrySet()) {

			ps += e.getValue();
			e.setValue(ps / 100f);

		}

	}

	/**
	 * Spins the wheel, returning a random element
	 * based on given probabilities
	 *
	 * @return A random element
	 */
	public T spin() {
		//noinspection OptionalGetWithoutIsPresent
		return ranges.entrySet()
				.stream()
				.filter(e -> r.nextFloat() < e.getValue())
				.map(Map.Entry::getKey)
				.findFirst()
				.get();
	}

	/**
	 * Get the probabilities given
	 * when creating this object
	 *
	 * @return Given probabilities
	 */
	public Map<T, Float> getProbabilities() {
		return originals;
	}

	public Map<T, Float> getRanges() {
		return Collections.unmodifiableMap(ranges);
	}

}
