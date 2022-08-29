package it.uniroma1.studenti.battleofsexes.models;

import lombok.Getter;

import java.util.Objects;

/**
 *
 */
public abstract class Human {

	@Getter
	protected final GeneType type;

	protected Human(GeneType type) {
		this.type = Objects.requireNonNull(type);
	}

}
