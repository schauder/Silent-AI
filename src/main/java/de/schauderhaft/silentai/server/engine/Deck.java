package de.schauderhaft.silentai.server.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

	private final Random random;
	List<Card> cards = new ArrayList<>();

	public Deck(Random random) {

		this.random = random;
	}

	public Deck() {
		this(new Random());
	}

	public List<Card> take(int count) {

		if (cards.size() < count) {
			throw new IllegalStateException("Can't take %d from a deck of %d cards.".formatted(count, cards.size()));
		}

		List<Card> subList = cards.subList(cards.size() - count, cards.size());
		List<Card> returnValue = new ArrayList<>(subList);

		subList.clear();

		return returnValue;
	}

	public void add(Card card) {
		cards.add(card);
	}

	public void shuffle() {
		Collections.shuffle(cards, random);
	}
}
