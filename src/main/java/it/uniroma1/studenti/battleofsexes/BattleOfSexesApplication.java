package it.uniroma1.studenti.battleofsexes;

import it.uniroma1.studenti.battleofsexes.beans.PayoffTable;
import it.uniroma1.studenti.battleofsexes.beans.RandomPicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BattleOfSexesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleOfSexesApplication.class, args);
	}

	private static PayoffTable payoffs;

	public static PayoffTable getPayoffs() {
		return payoffs;
	}

	@Autowired
	public void setPayoffs(PayoffTable payoffs) {
		BattleOfSexesApplication.payoffs = payoffs;
	}

	@Bean
	public RandomPicker randomsBean() {
		return new RandomPicker();
	}

}
