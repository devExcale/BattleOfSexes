package it.uniroma1.studenti.battleofsexes.matching;

import it.uniroma1.studenti.battleofsexes.BattleOfSexesApplication;
import it.uniroma1.studenti.battleofsexes.models.Couple;
import it.uniroma1.studenti.battleofsexes.models.Man;
import it.uniroma1.studenti.battleofsexes.models.Woman;

import java.util.HashSet;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Counselor extends Thread {

	private final Generation generation;
	private final Queue<Woman> women;
	private final Queue<Man> men;
	private final int groupSize;
	private volatile boolean run;

	public Counselor(Generation generation, Queue<Man> men, Queue<Woman> women) {
		this.generation = generation;
		this.women = Objects.requireNonNull(women);
		this.men = Objects.requireNonNull(men);
		groupSize = BattleOfSexesApplication.getGroupSize();
		run = true;
	}

	@Override
	public void run() {

		Set<Man> matchMen;
		Set<Woman> matchWomen;

		// Keep matching until there's no more women nor men
		while(run && (matchMen = pollMan()).size() == groupSize && (matchWomen = pollWoman()).size() == groupSize)
			match(matchMen, matchWomen);

	}

	@Override
	public void interrupt() {
		run = false;
		super.interrupt();
	}

	private Set<Man> pollMan() {

		Set<Man> set = new HashSet<>();

		for(int i = 0; i < groupSize; i++) {

			Man m = men.poll();

			if(m == null)
				break;

			set.add(m);

		}

		return set;
	}

	private Set<Woman> pollWoman() {

		Set<Woman> set = new HashSet<>();

		for(int i = 0; i < groupSize; i++) {

			Woman w = women.poll();

			if(w == null)
				break;

			set.add(w);

		}

		return set;
	}

	private void match(Set<Man> men, Set<Woman> women) {

		Set<Man> matchedMen = new HashSet<>();

		// Try to match all men
		for(Man man : men) {

			// With every woman
			for(Woman woman : man.topPreferences(women)) {

				// If a woman considers the man as a top preference
				Set<Man> pm = woman.topPreferences(men);
				if(pm.contains(man)) {

					// It's a match, deliver children
					Couple couple = new Couple(man, woman);
					couple.procreate();

					generation.addCouple(couple);

					// Remove the couple from the options
					women.remove(woman);
					matchedMen.add(man);

					break;
				}
			}

		}

		// Put free men and women in queue
		men.removeAll(matchedMen);
		this.men.addAll(matchedMen);
		this.women.addAll(women);

	}

}
