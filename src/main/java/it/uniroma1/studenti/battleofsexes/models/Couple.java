package it.uniroma1.studenti.battleofsexes.models;

import it.uniroma1.studenti.battleofsexes.beans.RandomPicker;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Couple {

	private final Man man;
	private final Woman woman;

	private final Set<Human> children;

	public Couple(Man man, Woman woman) {
		this.man = man;
		this.woman = woman;
		children = new HashSet<>();
	}

	public void procreate() {

		RandomPicker randomPick = RandomPicker.getInstance();
		Random r = new Random();

		for(int i = 0; i < randomPick.sons(); i++) {

			// 50/50 chance
			Human child = (r.nextBoolean()) ? new Man(man.getType()) : new Woman(woman.getType());
			children.add(child);

		}

	}

	public Set<Human> getChildren() {
		return children;
	}

}
