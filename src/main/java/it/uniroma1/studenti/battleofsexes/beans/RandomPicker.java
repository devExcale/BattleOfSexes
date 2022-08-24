package it.uniroma1.studenti.battleofsexes.beans;

import it.uniroma1.studenti.battleofsexes.utils.ProbabilityWheel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RandomPicker {

	// === STATIC ===

	public static RandomPicker instance;

	public static RandomPicker getInstance() {
		return instance;
	}

	// === INSTANCE ===

	private ProbabilityWheel<Integer> sons;

	public RandomPicker() {
		instance = this;
	}

	@Autowired
	public void setSonsProbabilities(@Value("#{${probability.sons}}") Map<Integer, Float> probabilities) {
		sons = new ProbabilityWheel<>(probabilities);
	}

	public Integer sons() {
		return sons.spin();
	}

}
