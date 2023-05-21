package de.schauderhaft.silentai.server.engine;

import org.springframework.stereotype.Component;

@Component
public class Game {


	private Player player;

	public void register(Player player) {

		this.player = player;
	}

	public void start() {
		player.makeMove(new Hand());
	}

	public int getPlayerCount() {
		return 0;
	}
}
