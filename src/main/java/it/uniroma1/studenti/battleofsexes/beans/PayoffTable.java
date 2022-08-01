package it.uniroma1.studenti.battleofsexes.beans;

import it.uniroma1.studenti.battleofsexes.models.Man;
import it.uniroma1.studenti.battleofsexes.models.Woman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static it.uniroma1.studenti.battleofsexes.models.Man.Type.FAITHFUL;
import static it.uniroma1.studenti.battleofsexes.models.Man.Type.PHILANDERER;
import static it.uniroma1.studenti.battleofsexes.models.Woman.Type.COY;
import static it.uniroma1.studenti.battleofsexes.models.Woman.Type.FAST;

@SuppressWarnings("DuplicatedCode")
@Component
public class PayoffTable {

	private final float a;
	private final float b;
	private final float c;

	@Autowired
	public PayoffTable(ApplicationArguments args) {

		a = Optional.ofNullable(args.getOptionValues("a"))
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(Float::parseFloat)
				.orElse(15f);

		b = Optional.ofNullable(args.getOptionValues("b"))
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(Float::parseFloat)
				.orElse(20f);

		c = Optional.ofNullable(args.getOptionValues("a"))
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(Float::parseFloat)
				.orElse(3f);

	}

	public Map<Man.Type, Float> getCoyProbabilities() {

		float f = coyToFaithful(), p = coyToPhilanderer();
		float total = f + p;

		Map<Man.Type, Float> probabs = new HashMap<>();
		probabs.put(FAITHFUL, f / total * 100);
		probabs.put(PHILANDERER, p / total * 100);

		return probabs;
	}

	public Map<Man.Type, Float> getFastProbabilities() {

		float f = fastToFaithful(), p = fastToPhilanderer();
		float total = f + p;

		Map<Man.Type, Float> probabs = new HashMap<>();
		probabs.put(FAITHFUL, f / total * 100);
		probabs.put(PHILANDERER, p / total * 100);

		return probabs;
	}

	public Map<Woman.Type, Float> getFaithfulProbabilities() {

		float c = faithfulToCoy(), s = faithfulToFast();
		float total = c + s;

		Map<Woman.Type, Float> probabs = new HashMap<>();
		probabs.put(COY, c / total * 100);
		probabs.put(FAST, s / total * 100);

		return probabs;
	}

	public Map<Woman.Type, Float> getPhilandererProbabilities() {

		float c = philandererToCoy(), s = philandererToFast();
		float total = c + s;

		Map<Woman.Type, Float> probabs = new HashMap<>();
		probabs.put(COY, c / total * 100);
		probabs.put(FAST, s / total * 100);

		return probabs;
	}

	public float coyToFaithful() {
		return a - (b / 2) - c;
	}

	public float coyToPhilanderer() {
		return 0f;
	}

	public float fastToFaithful() {
		return a - (b / 2);
	}

	public float fastToPhilanderer() {
		return a - b;
	}

	public float faithfulToCoy() {
		return a - (b / 2) - c;
	}

	public float faithfulToFast() {
		return a - (b / 2);
	}

	public float philandererToCoy() {
		return 0f;
	}

	public float philandererToFast() {
		return a;
	}

}
