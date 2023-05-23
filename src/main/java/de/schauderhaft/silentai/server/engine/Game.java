package de.schauderhaft.silentai.server.engine;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Game {

	int currentPlayer = 0;
	private List<Player> players = new ArrayList<>();

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

		List<Card> allCards = new ArrayList<>();
		for (int i = 1; i <= 80; i++) {
			allCards.add(new IslandCard(i));
		}
		Collections.shuffle(allCards);

		players.get(currentPlayer)
				.makeMove(
						new Hand(
								allCards.subList(0, 5)
						)
				);
		currentPlayer = (currentPlayer + 1) % getPlayerCount();
	}

}
