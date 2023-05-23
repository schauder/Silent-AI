package de.schauderhaft.silentai.server.engine;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DeckTest {

	@Test
	void newDeckIsEmpty() {

		Deck deck = new Deck();
		assertThatThrownBy(() -> deck.take(1))
				.isInstanceOf(IllegalStateException.class);
	}

	@Test
	void deckReturnsLastCardsAdded() {

		IslandCard one = new IslandCard(1);
		IslandCard two = new IslandCard(2);
		IslandCard three = new IslandCard(3);

		Deck deck = new Deck();
		deck.add(one);
		deck.add(two);
		deck.add(three);

		List<Card> taken = deck.take(2);
		assertThat(taken).containsExactly(two, three);

		taken = deck.take(1);
		assertThat(taken).containsExactly(one);
	}
}