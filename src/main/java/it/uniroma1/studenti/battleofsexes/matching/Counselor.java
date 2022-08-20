package it.uniroma1.studenti.battleofsexes.matching;

import it.uniroma1.studenti.battleofsexes.models.Man;
import it.uniroma1.studenti.battleofsexes.models.Woman;

import java.util.Queue;

public class Counselor extends Thread {

	private final Queue<Woman> women;
	private final Queue<Man> men;

	public Counselor(Queue<Woman> women, Queue<Man> men) {
		this.women = women;
		this.men = men;
	}

	@Override
	public void run() {

		// Keep matching until there's no more women nor men
		while(true) {

			Woman w1 = women.poll(), w2 = women.poll();
			Man m1 = men.poll(), m2 = men.poll();

			// If there are < 4 people, end of queue
			if(w1 == null || w2 == null || m1 == null || m2 == null)
				return;

		}

	}

}
