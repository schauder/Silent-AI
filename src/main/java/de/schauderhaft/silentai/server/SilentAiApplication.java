package de.schauderhaft.silentai.server;

import de.schauderhaft.silentai.server.engine.Game;
import de.schauderhaft.silentai.stupid.StupidPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SilentAiApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SilentAiApplication.class);

	private final Game game;

	public SilentAiApplication(Game game) {
		this.game = game;
	}

	public static void main(String[] args) {
		SpringApplication.run(SilentAiApplication.class, args);
	}

	@Override
	public void run(String... args) {
		int playerCount = parsePlayerCount(args);
		for (int player = 0; player < playerCount; player++) {
			game.register(new StupidPlayer());
		}
		game.start();
	}

	private int parsePlayerCount(String[] args) {
		int playerCount;
		if (args.length == 0) {
			log.info("No player number provided. Using two players");
			playerCount = 2;
		} else if (args.length > 2) {
			throw new IllegalArgumentException("Too many arguments. SilentAI accepts only a single integer argument: The number of players");
		} else {
			playerCount = Integer.parseInt(args[0]);
			log.info("Playing with " + game.getPlayerCount() + " player(s)");
		}
		return playerCount;
	}
}
