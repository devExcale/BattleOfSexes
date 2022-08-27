package it.uniroma1.studenti.battleofsexes;

import it.uniroma1.studenti.battleofsexes.matching.Generation;
import it.uniroma1.studenti.battleofsexes.models.GeneType;
import it.uniroma1.studenti.battleofsexes.models.Man;
import it.uniroma1.studenti.battleofsexes.models.Woman;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Log4j2
@Component
public class Emulator implements CommandLineRunner {

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

	@Override
	public void run(String... args) throws Exception {

		int people = 50_000;
		final Random r = new Random();

		final Man.Type[] maleTypes = Man.Type.values();
		final Woman.Type[] femaleTypes = Woman.Type.values();

		List<Man> men = IntStream.range(0, people / 2)
				.mapToObj(i -> new Man(maleTypes[r.nextInt(2)]))
				.toList();

		List<Woman> women = IntStream.range(0, people / 2)
				.mapToObj(i -> new Woman(femaleTypes[r.nextInt(2)]))
				.toList();

		log.info("Starting gen");

		Generation gen = Generation.builder()
				.id("#1")
				.men(men)
				.women(women)
				.build();

		gen.compute();

		int total = gen.getTotalChildren();
		log.info("Total: {}", total);

		for(Map.Entry<GeneType, AtomicInteger> e : gen.getTypeStats()
				.entrySet()) {

			GeneType type = e.getKey();
			double subtotal = e.getValue()
					.get();
			double percent = subtotal / total * 100d;

			log.info("Gene [{}]: {} ({}%)", type.getCode(), subtotal, DECIMAL_FORMAT.format(percent));

		}

	}

}
