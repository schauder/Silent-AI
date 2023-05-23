package de.schauderhaft.silentai.server.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.*;

public class Hand {

	private final List<Card> cards = new ArrayList<>();

	public Hand(Card ... cards) {
		this.cards.addAll(asList(cards));
	}

	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}
}
