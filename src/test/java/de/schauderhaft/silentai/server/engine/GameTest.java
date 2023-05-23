package de.schauderhaft.silentai.server.engine;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {


	@Nested
	class RegisterPlayers {
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
	}

	@Nested
	class MakeMove {
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

	@Nested
	class Hands {

		@Test
		void gameGivesRandomHand() {

			Hand firstHand = checkSingleDeal(-1);
			for (int i = 0; i < 100; i++) {

				assertThat(firstHand).isNotEqualTo(checkSingleDeal(i));
			}
		}

		@Test
		void gameGivesDifferentHandToDifferentPlayers() {

			Game game = new Game(0);
			RecordingPlayer[] players = new RecordingPlayer[5];
			for (int i = 0; i < 5; i++) {
				players[i] = new RecordingPlayer();
				game.register(players[i]);
			}

			game.start();
			for (int i = 1; i < 5; i++) {
				game.next();
			}

			ArrayList<Card> allHands = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				allHands.addAll(players[i].hand.getCards());
			}

			assertThat(allHands).hasSize(25);

			List<Card> islandCards = allHands.stream().filter(c -> c instanceof IslandCard).toList();
			Set<Card> islandCardSet = new HashSet<>(islandCards);

			assertThat(islandCards).hasSizeBetween(20, 25);
			assertThat(islandCards).containsExactlyInAnyOrder(islandCardSet.toArray(Card[]::new));
		}

		private static Hand checkSingleDeal(long seed) {

			RecordingPlayer player = new RecordingPlayer();
			Game game = new Game(seed);
			game.register(player);
			game.start();
			Hand hand = player.hand;

			assertThat(hand.getCards())
					.hasSize(5)
					.doesNotContain(SpecialCard.DEPART);

			List<Card> islandCards = hand.getCards().stream().filter(c -> c instanceof IslandCard).toList();
			HashSet<Card> singleCards = new HashSet<>(islandCards);

			assertThat(islandCards).containsExactlyInAnyOrder(singleCards.toArray(Card[]::new));
			return hand;
		}

		private static class RecordingPlayer implements Player {
			Hand hand;

			@Override
			public Move makeMove(Hand hand) {
				this.hand =hand;
				return null;
			}
		}
	}

	@Nested
	class ProcessingMove{
		@Test
		void moveAffectsSlots(){
					Card[] card = new Card[1];
			Player player = new Player() {
				@Override
				public Move makeMove(Hand hand) {
					card[0] = hand.getCards().get(0);
					return Move.islandCard((IslandCard) card[0], 15);
				}
			};

			Game game = new Game();
			game.register(player);
			game.start();

			assertThat(game.getSlot(15)).isEqualTo(card[0]);
		}
	}

}
