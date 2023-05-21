package de.schauderhaft.silentai.server.engine;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GameTest {

	@Test
	void gameAsksSinglePlayerToMove() {

		Player player = mock(Player.class);

		Game game = new Game();
		game.register(player);
		game.start();

		verify(player).makeMove(any(Hand.class));
	}

}
