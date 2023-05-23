package de.schauderhaft.silentai.server.engine;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Game {

	private static final int BOARD_SIZE = 36;
	private static final int DECK_SIZE = 80;
	private Random random;
	private Deck allCards;
	private Card[] slots = new Card[BOARD_SIZE];

	int currentPlayer = 0;
	private List<Player> players = new ArrayList<>();


	Game() {
		this(System.currentTimeMillis());
	}

	public Game(long seed) {

		random = new Random(seed);
		allCards = new Deck(random);

		for (int i = 1; i <= DECK_SIZE; i++) {
			allCards.add(new IslandCard(i));
		}
		allCards.shuffle();


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

		Move move = players.get(currentPlayer)
				.makeMove(
						new Hand(
								take5()
						)
				);

		process(move);
		currentPlayer = (currentPlayer + 1) % getPlayerCount();
	}

	private void process(Move move) {

		if (move instanceof IslandCardMove islandCardMove) {
			slots[islandCardMove.slot()] = islandCardMove.card();
		}
	}

	private List<Card> take5() {

		return allCards.take(5);
	}

	public Card getSlot(int slot) {
		return slots[slot];
	}
}
