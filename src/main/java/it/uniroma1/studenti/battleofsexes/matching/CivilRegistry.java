package it.uniroma1.studenti.battleofsexes.matching;

import it.uniroma1.studenti.battleofsexes.models.Couple;
import it.uniroma1.studenti.battleofsexes.models.Man;
import it.uniroma1.studenti.battleofsexes.models.Woman;

import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

public class CivilRegistry {

	Queue<Couple> couples;

	public CivilRegistry() {
		couples = new LinkedTransferQueue<>();
	}

	public void marry(Man m, Woman w) {

		couples.add(new Couple(m, w));

	}

}
