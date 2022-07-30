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

			Woman woman = women.poll();
			Man man = men.poll();

			if(woman == null || man == null)
				return;

		}

	}

}
