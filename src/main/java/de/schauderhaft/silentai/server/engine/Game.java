package de.schauderhaft.silentai.server.engine;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class Game {

	Random random;
	private List<Card> allCards;

	Game() {
		this(System.currentTimeMillis());
	}

	int currentPlayer = 0;
	private List<Player> players = new ArrayList<>();

	public Game(long seed) {

		random = new Random(seed);

		allCards = new ArrayList<>();
		for (int i = 1; i <= 80; i++) {
			allCards.add(new IslandCard(i));
		}
		Collections.shuffle(allCards, random);

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

		List<Card> hand = new ArrayList<>(allCards.subList(0, 5));
		allCards.removeAll(hand);
		return hand;
	}

}
