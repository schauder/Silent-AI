package de.schauderhaft.silentai.server.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.*;

public class Hand {

	private final List<Card> cards = new ArrayList<>();

	public Hand(List<Card> cards) {
		this.cards.addAll(cards);
	}

	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hand hand = (Hand) o;
		return Objects.equals(cards, hand.cards);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cards);
	}

	@Override
	public String toString() {
		return "Hand{" +
				"cards=" + cards +
				'}';
	}
}
