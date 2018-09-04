package yuki_project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

import yuki_project.pokerCard.RankEnum;
import yuki_project.pokerCard.SuitEnum;

public class card_Deck implements Texas_Holdem_Constant {

	// The deck's top card is the end of the list, Queue for other purposes
	private ArrayList<pokerCard> deck = new ArrayList<>();

	// give full deck in order
	public card_Deck() {
		for (SuitEnum suit : SuitEnum.values()) {
			for (RankEnum rank : RankEnum.values()) {
				deck.add(new pokerCard(suit, rank));
			}
		}
	}

	// put a card at the bottom of the deck
	public void bottom(pokerCard card) {
		deck.add(0, card);
	}

	public void shuffle() {
		ArrayList<pokerCard> temp = new ArrayList<>();
		while (!deck.isEmpty()) {
			temp.add(deck.remove((int) (deck.size() * Math.random())));
		}
		deck = temp;
	}

	// sort the card Deck
	public void putInOrder() {
		Collections.sort(deck, new Comparator<pokerCard>() {
			public int compare(pokerCard o1, pokerCard o2) {
				return o1.compareSuit_Rank(o2);
			}
		});
	}

	// draw a card, remove top card from the deck and no card left then return
	// null;
	public pokerCard draw() {
		if (deck.size() == 0) {
			return null;
		}
		return deck.remove(deck.size() - 1);
	}

	// list all the deck in order
	public String toString() {
		String result = "";
		for (int i = deck.size() - 1; i >= 0; i--) {
			result += deck.get(i).toString() + "\n";
		}
		return result;
	}
}
