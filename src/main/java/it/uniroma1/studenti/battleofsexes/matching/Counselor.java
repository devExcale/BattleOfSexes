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

	private final Queue<Woman> women;
	private final Queue<Man> men;
	private final int groupSize;

	private volatile boolean run;

	public Counselor(Queue<Woman> women, Queue<Man> men) {
		this.women = Objects.requireNonNull(women);
		this.men = Objects.requireNonNull(men);
		groupSize = BattleOfSexesApplication.getGroupSize();
		run = true;
	}

	public void end() {
		run = false;
	}

	@Override
	public void run() {

		// Keep matching until there's no more women nor men
		while(run) {

			Set<Man> menToMatch = new HashSet<>();
			Set<Woman> womenToMatch = new HashSet<>();

			// Take as many men/women needed
			for(int i = 0; i < groupSize; i++) {

				Man m = men.poll();
				Woman w = women.poll();

				// If one of them is missing we've reached the end of the queue,
				// there are no more possible matches
				if(m == null || w == null)
					return;

				menToMatch.add(m);
				womenToMatch.add(w);

			}

			match(menToMatch, womenToMatch);

		}

	}

	private void match(Set<Man> men, Set<Woman> women) {

		// Try to match all men
		for(Man man : men) {

			for(Woman woman : man.preferredWomen(women)) {

				Set<Man> pm = woman.preferredMen(men);

				if(pm.contains(man)) {

					Couple couple = new Couple(man, woman);

				}

			}

		}

	}

}
