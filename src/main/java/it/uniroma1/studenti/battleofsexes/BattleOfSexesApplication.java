package it.uniroma1.studenti.battleofsexes;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@SpringBootApplication
public class BattleOfSexesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleOfSexesApplication.class, args);
	}

	@Getter
	private static DecimalFormat decimalFormat;

	// Evolution parameters
	@Getter
	private static float a;
	@Getter
	private static float b;
	@Getter
	private static float c;

	// Matching parameters
	@Getter
	private static int groupSize;
	@Getter
	private static int counselors;
	@Getter
	private static int waitTime;

	public BattleOfSexesApplication() {

		DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.ITALIAN);
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');

		decimalFormat = new DecimalFormat("#,###.##", dfs);

	}

	@Autowired
	public void setEvolutionA(@Value("${evolution.a:15}") float a) {
		BattleOfSexesApplication.a = a;
	}

	@Autowired
	public void setEvolutionB(@Value("${evolution.b:20}") float b) {
		BattleOfSexesApplication.b = b;
	}

	@Autowired
	public void setEvolutionC(@Value("${evolution.c:3}") float c) {
		BattleOfSexesApplication.c = c;
	}

	@Autowired
	public void setGroupSize(@Value("${matching.groupsize:2}") int size) {
		groupSize = (size > 2) ? size : 2;
	}

	@Autowired
	public void setCounselors(@Value("${generation.waitTime:4}") int n) {
		counselors = (n > 1) ? n : 2;
	}

	@Autowired
	public void setGenerationWaitTime(@Value("${generation.waitTime:3000}") int waitTime) {
		BattleOfSexesApplication.waitTime = waitTime;
	}

}
