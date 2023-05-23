package de.schauderhaft.silentai.server.engine;

public interface Move {
	static Move islandCard(IslandCard card, int slot){
		return new IslandCardMove(card, slot);
	}
}
