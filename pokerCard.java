package yuki_project;

public class pokerCard implements Texas_Holdem_Constant {
	private RankEnum rank;
	private SuitEnum suit;
	//Enum for ranks instead of using index
	public enum RankEnum {
		CARD_2, CARD_3, CARD_4, CARD_5, CARD_6, CARD_7, CARD_8, CARD_9, CARD_10, JACK, QUEEN, KING, ACE
	}
	//Suit Enum, suit doesn't matter in Texas Holdem in terms of suit's rank. 
	public enum SuitEnum {
		CLUBS, DIAMONDS, HEARTS, SPADES
	}

	// A is 12, K is 11, 2 is 0
	// constructor for specific card
	public pokerCard(SuitEnum suit, RankEnum rank) {
		this.rank = rank;
		this.suit = suit;
	}

	//return the enum suit
	public SuitEnum getSuit() {
		return suit;
	}

	// 0 is 2 K is 11 A is 12
	public RankEnum getRank() {
		return rank;
	}

	//get rank index
	public int getRankToInt() {
		return rank.ordinal();
	}
	//compare suit
	public int compareSuit(pokerCard o1) {
		if (suit.compareTo(o1.suit) > 0) {
			return 1;
		} else if (suit.compareTo(o1.suit) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	// compare the value only
	public int compareRank(pokerCard o1) {
		if (rank.compareTo(o1.rank) > 0) {
			return 1;
		} else if (rank.compareTo(o1.rank) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	//compare rank first then suit
	public int compareRank_Suit(pokerCard o1) {
		int result = compareRank(o1);
		if (result == 0) {
			return compareSuit(o1);
		} else {
			return result;
		}

	}

	// compare the suit and then compare the value, sorting card deck purposes
	public int compareSuit_Rank(pokerCard o1) {
		int result = compareSuit(o1);
		if (result == 0) {
			return compareRank(o1);
		} else {
			return result;
		}

	}

	// equal suit and equal value, identical card 
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (!(other instanceof pokerCard))
			return false;
		pokerCard otherCard = (pokerCard) other;
		return (otherCard.rank == this.rank) && (otherCard.suit == this.suit);
	}

	// print out the card
	public String toString() {
		String result = suit.toString() + " of " + rank.toString();
		return result;
	}
}
