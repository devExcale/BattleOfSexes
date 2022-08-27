package it.uniroma1.studenti.battleofsexes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class BattleOfSexesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleOfSexesApplication.class, args);
	}

	private static ApplicationArguments args;
	private static float a;
	private static float b;
	private static float c;
	private static int groupSize;
	private static int nCounselors;
	private static int nRegistries;

	@Autowired
	public void setArguments(ApplicationArguments arguments) {

		// TODO: application properties on exec path
		args = arguments;

		a = Optional.ofNullable(args.getOptionValues("a"))
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(Float::parseFloat)
				.orElse(15f);

		b = Optional.ofNullable(args.getOptionValues("b"))
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(Float::parseFloat)
				.orElse(20f);

		c = Optional.ofNullable(args.getOptionValues("a"))
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(Float::parseFloat)
				.orElse(3f);

		groupSize = Optional.ofNullable(arguments.getOptionValues("groupsize"))
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(Integer::parseInt)
				.filter(x -> x > 2)
				.orElse(3);

		nCounselors = Optional.ofNullable(arguments.getOptionValues("counselors"))
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(Integer::parseInt)
				.filter(x -> x > 0)
				.orElse(4);

		nRegistries = Optional.ofNullable(arguments.getOptionValues("registries"))
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(Integer::parseInt)
				.filter(x -> x > 2)
				.orElse(2);

	}

	public static ApplicationArguments getArguments() {
		return args;
	}

	public static float getA() {
		return a;
	}

	public static float getB() {
		return b;
	}

	public static float getC() {
		return c;
	}

	public static int getGroupSize() {
		return groupSize;
	}

	public static int getNCounselors() {
		return nCounselors;
	}

	public static int getNRegistries() {
		return nRegistries;
	}

}
