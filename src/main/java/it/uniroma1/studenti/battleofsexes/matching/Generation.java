package it.uniroma1.studenti.battleofsexes.matching;

import it.uniroma1.studenti.battleofsexes.BattleOfSexesApplication;
import it.uniroma1.studenti.battleofsexes.models.*;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Log4j2
public class Generation {

	@Getter
	private final String id;

	@Getter
	private final List<Man> maleChildren;
	@Getter
	private final List<Woman> femaleChildren;
	@Getter
	private final Map<GeneType, AtomicInteger> typeStats;

	private final Set<Counselor> counselors;

	@Builder
	private Generation(String id, List<Man> men, List<Woman> women) {
		this.id = id;

		int size = men.size() + women.size();

		maleChildren = Collections.synchronizedList(new ArrayList<>(size));
		femaleChildren = Collections.synchronizedList(new ArrayList<>(size));

		typeStats = new HashMap<>();
		initTypeStats();

		Queue<Man> menQueue = new ConcurrentLinkedQueue<>(List.copyOf(men));
		Queue<Woman> womenQueue = new ConcurrentLinkedQueue<>(List.copyOf(women));
		counselors = new HashSet<>();

		for(int i = 0; i < BattleOfSexesApplication.getNCounselors(); i++)
			counselors.add(new Counselor(this, menQueue, womenQueue));

	}

	private void initTypeStats() {

		for(Man.Type type : Man.Type.values())
			typeStats.put(type, new AtomicInteger(0));

		for(Woman.Type type : Woman.Type.values())
			typeStats.put(type, new AtomicInteger(0));

	}

	public void compute() {

		ExecutorService exec = Executors.newFixedThreadPool(BattleOfSexesApplication.getNCounselors());

		for(Counselor counselor : counselors)
			exec.submit(counselor);

		try {

			//noinspection ResultOfMethodCallIgnored
			exec.awaitTermination(BattleOfSexesApplication.getWaitTime(), MILLISECONDS);
			exec.shutdownNow();

		} catch(InterruptedException e) {
			log.warn(e);
		}

	}

	public Map<GeneType, Float> getTypeRatios() {

		float total = (float) getTotalChildren();
		Map<GeneType, Float> ratios = new HashMap<>();

		for(Map.Entry<GeneType, AtomicInteger> e : typeStats.entrySet()) {

			float n = e.getValue()
					.floatValue();

			ratios.put(e.getKey(), n / total);

		}

		return ratios;
	}

	public int getTotalChildren() {
		return maleChildren.size() + femaleChildren.size();
	}

	public void addCouple(Couple couple) {

		for(Human child : couple.getChildren()) {

			typeStats.get(child.getType())
					.incrementAndGet();

			if(child instanceof Man male)
				maleChildren.add(male);

			if(child instanceof Woman female)
				femaleChildren.add(female);

		}

	}

	@Override
	public String toString() {

		final int total = getTotalChildren();

		String percents = getTypeRatios().entrySet()
				.stream()
				.map(e -> {

					String key = e.getKey()
							.getCode();

					return "%s: %.2f%%".formatted(key, e.getValue() * 100f);
				})
				.sorted()
				.collect(Collectors.joining(", "));

		return String.format("Gen %s - Total: %d, ", id, total) + percents;
	}

}
