package de.schauderhaft.silentai.server.engine;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

		players.get(currentPlayer).makeMove(new Hand(new IslandCard(1), new IslandCard(2), new IslandCard(3), new IslandCard(4), new IslandCard(5)));
		currentPlayer = (currentPlayer + 1) % getPlayerCount();
	}

}
