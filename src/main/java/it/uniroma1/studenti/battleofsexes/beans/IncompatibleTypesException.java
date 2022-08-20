package it.uniroma1.studenti.battleofsexes.beans;

import it.uniroma1.studenti.battleofsexes.models.Man;
import it.uniroma1.studenti.battleofsexes.models.Woman;

public class IncompatibleTypesException extends Exception {

	public IncompatibleTypesException(Woman.Type wt, Man.Type mt) {
		super(String.format("Incompatible types: %s, %s", wt, mt));
	}

}
