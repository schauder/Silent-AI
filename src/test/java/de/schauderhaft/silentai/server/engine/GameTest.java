package de.schauderhaft.silentai.server.engine;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {

	@Test
	void gameReportPlayerCount() {

		Player one = mock(Player.class);
		Player two = mock(Player.class);
		Player three = mock(Player.class);

		Game game = new Game();
		assertThat(game.getPlayerCount()).isEqualTo(0);

		game.register(one);
		assertThat(game.getPlayerCount()).isEqualTo(1);

		game.register(two);
		assertThat(game.getPlayerCount()).isEqualTo(2);

		game.register(three);
		assertThat(game.getPlayerCount()).isEqualTo(3);
	}

	@Test
	void gameAsksSinglePlayerToMove() {

		Player player = mock(Player.class);

		Game game = new Game();
		game.register(player);
		game.start();

		verify(player).makeMove(any(Hand.class));
	}

	@Test
	void gameAsksThreePlayersInRoundRobinFashionToMove() {

		Player one = mock(Player.class);
		Player two = mock(Player.class);
		Player three = mock(Player.class);

		Game game = new Game();
		game.register(one);
		game.register(two);
		game.register(three);
		game.start();

		verify(one).makeMove(any(Hand.class));
		verifyNoInteractions(two, three);
		reset(one);

		game.next();

		verify(two).makeMove(any(Hand.class));
		verifyNoInteractions(one, three);
		reset(two);

		game.next();

		verify(three).makeMove(any(Hand.class));
		verifyNoInteractions(one, two);
		reset(three);

		game.next();

		verify(one).makeMove(any(Hand.class));
		verifyNoInteractions(two, three);
		reset(one);
	}

}
