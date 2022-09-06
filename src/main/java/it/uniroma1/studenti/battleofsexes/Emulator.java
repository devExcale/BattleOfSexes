package it.uniroma1.studenti.battleofsexes;

import it.uniroma1.studenti.battleofsexes.beans.RandomPicker;
import it.uniroma1.studenti.battleofsexes.matching.Generation;
import it.uniroma1.studenti.battleofsexes.models.GeneType;
import it.uniroma1.studenti.battleofsexes.models.Man;
import it.uniroma1.studenti.battleofsexes.models.Woman;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Log4j2
@Component
public class Emulator implements CommandLineRunner {

	private final RandomPicker r;
	private final int startingPopulation;
	private final float errorMargin;

	public Emulator(RandomPicker r, @Value("${population.start.total:1000000}") int startingPopulation,
			@Value("${generation.errorMargin:0.001f}") float errorMargin) {
		this.r = r;
		this.startingPopulation = startingPopulation;
		this.errorMargin = errorMargin;
	}

	@Override
	public void run(String... args) {

		List<Man> men = IntStream.range(0, startingPopulation / 2)
				.mapToObj(i -> new Man(r.maleGene()))
				.toList();

		List<Woman> women = IntStream.range(0, startingPopulation / 2)
				.mapToObj(i -> new Woman(r.femaleGene()))
				.toList();

		Generation gen = null;
		boolean stable = false;
		Map<GeneType, Float> prevRatios = null;

		log.info("Initial population: {}", BattleOfSexesApplication.getDecimalFormat()
				.format(startingPopulation));

		for(int i = 0; !stable && i < 100; i++) {

			gen = Generation.builder()
					.id("#" + i)
					.men(men)
					.women(women)
					.build();

			gen.compute();
			log.info(gen);

			Map<GeneType, Float> currentRatios = gen.getTypeRatios();
			stable = isStable(prevRatios, currentRatios);
			prevRatios = currentRatios;

			men = gen.getMaleChildren();
			women = gen.getFemaleChildren();

		}

		if(stable)
			log.info("Evolution reached a stable point with generation " + gen.getId());
		else
			log.info("Evolution can't reach a stable point by generation " + gen.getId());

	}

	private boolean isStable(Map<GeneType, Float> stats1, Map<GeneType, Float> stats2) {

		if(stats1 == null || stats2 == null)
			return false;

		boolean stable = true;

		for(Map.Entry<GeneType, Float> e : stats1.entrySet()) {

			float num = e.getValue();
			float denom = stats2.get(e.getKey());

			if(num != 0 && denom != 0)
				stable &= Math.abs(1f - e.getValue() / stats2.get(e.getKey())) <= errorMargin;

		}

		return stable;
	}

}
