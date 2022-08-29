package it.uniroma1.studenti.battleofsexes;

import it.uniroma1.studenti.battleofsexes.beans.RandomPicker;
import it.uniroma1.studenti.battleofsexes.matching.Generation;
import it.uniroma1.studenti.battleofsexes.models.Man;
import it.uniroma1.studenti.battleofsexes.models.Woman;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Log4j2
@Component
public class Emulator implements CommandLineRunner {

	private RandomPicker r;

	public Emulator(RandomPicker r) {
		this.r = r;
	}

	@Override
	public void run(String... args) throws Exception {

		int people = 1_000_000;

		List<Man> men = IntStream.range(0, people / 2)
				.mapToObj(i -> new Man(r.maleGene()))
				.toList();

		List<Woman> women = IntStream.range(0, people / 2)
				.mapToObj(i -> new Woman(r.femaleGene()))
				.toList();

		log.info("Using ");

		Generation gen;
		for(int i = 0; i < 10; i++) {

			gen = Generation.builder()
					.id("#" + 1)
					.men(men)
					.women(women)
					.build();

			gen.compute();
			log.info(gen);

			men = gen.getMaleChildren();
			women = gen.getFemaleChildren();

		}

	}

}
