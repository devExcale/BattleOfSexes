package it.uniroma1.studenti.battleofsexes.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Man {

	private final Type type;

	public enum Type {

		FAITHFUL("F"),
		PHILANDERERS("P");

		@Getter
		private final String code;

		Type(String code) {
			this.code = code;
		}

	}

}
