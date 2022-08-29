package it.uniroma1.studenti.battleofsexes.beans;

import it.uniroma1.studenti.battleofsexes.models.Man;
import it.uniroma1.studenti.battleofsexes.models.Woman;
import it.uniroma1.studenti.battleofsexes.utils.ProbabilityWheel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class.
 */
@Component
public class RandomPicker {

	// === STATIC ===

	public static RandomPicker instance;

	public static RandomPicker getInstance() {
		return instance;
	}

	// === INSTANCE ===

	private ProbabilityWheel<Integer> sons;
	private ProbabilityWheel<Man.Type> maleGenes;
	private ProbabilityWheel<Woman.Type> femaleGenes;

	public RandomPicker() {
		instance = this;
	}

	@Autowired
	public void setSons(@Value("#{${probability.sons}}") Map<Integer, Float> probabilities) {
		sons = new ProbabilityWheel<>(probabilities);
	}

	@Autowired
	public void setMaleGenes(@Value("#{${population.start.men}}") Map<String, Float> probabilities) {
		Map<Man.Type, Float> typeProbabilities = probabilities.entrySet()
				.stream()
				.collect(Collectors.toMap(e -> Man.Type.MAPPINGS.get(e.getKey()), Map.Entry::getValue));
		maleGenes = new ProbabilityWheel<>(typeProbabilities);
	}

	@Autowired
	public void setFemaleGenes(@Value("#{${population.start.women}}") Map<String, Float> probabilities) {
		Map<Woman.Type, Float> typeProbabilities = probabilities.entrySet()
				.stream()
				.collect(Collectors.toMap(e -> Woman.Type.MAPPINGS.get(e.getKey()), Map.Entry::getValue));
		femaleGenes = new ProbabilityWheel<>(typeProbabilities);
	}

	public Integer sons() {
		return sons.spin();
	}

	public Man.Type maleGene() {
		return maleGenes.spin();
	}

	public Woman.Type femaleGene() {
		return femaleGenes.spin();
	}

}
