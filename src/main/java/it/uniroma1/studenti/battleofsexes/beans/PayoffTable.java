package it.uniroma1.studenti.battleofsexes.beans;

import it.uniroma1.studenti.battleofsexes.models.Man;
import it.uniroma1.studenti.battleofsexes.models.Woman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

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

	private final float[][] menPayoffs;
	private final float[][] womenPayoffs;

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

		menPayoffs = new float[2][2];
		womenPayoffs = new float[2][2];

	}

	public void calculatePayoffs() {

		int F = FAITHFUL.ordinal(), P = PHILANDERER.ordinal();
		int C = COY.ordinal(), S = FAST.ordinal();

		menPayoffs[F][C] = faithfulToCoy();
		menPayoffs[F][S] = faithfulToFast();
		menPayoffs[P][C] = philandererToCoy();
		menPayoffs[P][S] = philandererToFast();

		womenPayoffs[C][F] = coyToFaithful();
		womenPayoffs[C][P] = coyToPhilanderer();
		womenPayoffs[S][F] = fastToFaithful();
		womenPayoffs[S][P] = fastToPhilanderer();

	}

	public float womanToMan(Woman.Type wt, Man.Type mt) {
		return womenPayoffs[wt.ordinal()][mt.ordinal()];
	}

	public float manToWoman(Man.Type mt, Woman.Type wt) {
		return womenPayoffs[mt.ordinal()][wt.ordinal()];
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
