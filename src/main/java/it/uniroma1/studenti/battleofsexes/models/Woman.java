package it.uniroma1.studenti.battleofsexes.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Woman {

	private final Type type;

	public enum Type {

		COY("C"),
		FAST("S");

		@Getter
		private final String code;

		Type(String code) {
			this.code = code;
		}

	}

}
