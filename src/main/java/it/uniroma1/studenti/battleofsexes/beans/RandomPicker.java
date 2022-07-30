package it.uniroma1.studenti.battleofsexes.beans;

import it.uniroma1.studenti.battleofsexes.utils.ProbabilityWheel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@NoArgsConstructor
@Component
public class RandomPicker {

	private ProbabilityWheel<Integer> sons;

	@Autowired
	public void setSonsProbabilities(@Value("#{${probability.sons}}") Map<Integer, Float> probabilities) {
		sons = new ProbabilityWheel<>(probabilities);
	}

	public Integer sons() {
		return sons.spin();
	}

}
