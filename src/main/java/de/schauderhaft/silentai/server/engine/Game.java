package de.schauderhaft.silentai.server.engine;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class Game {

	Random random;
	private Deck allCards;

	Game() {
		this(System.currentTimeMillis());
	}

	int currentPlayer = 0;
	private List<Player> players = new ArrayList<>();

	public Game(long seed) {

		random = new Random(seed);
		allCards = new Deck(random);

		for (int i = 1; i <= 80; i++) {
			allCards.add(new IslandCard(i));
		}

		allCards.shuffle( );

	}

	public void register(Player player) {
		this.players.add(player);
	}

	public void start() {
		move();
	}

	public int getPlayerCount() {
		return players.size();
	}

	public void next() {
		move();
	}


	private void move() {

		players.get(currentPlayer)
				.makeMove(
						new Hand(
								take5()
						)
				);
		currentPlayer = (currentPlayer + 1) % getPlayerCount();
	}

	private List<Card> take5() {

		return allCards.take(5);
	}

}
