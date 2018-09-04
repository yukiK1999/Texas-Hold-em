package yuki_project;

public interface Texas_Holdem_Constant {

	public final int PLAYER_HAND = 2;
	public final int BOARD_CARD = 5;
	public final int RANKS = 13;
	public final int SUITS = 4;
	public final int BURN_CARD = 3;
	public final int DECK_CARDS = 52;
	public enum RankingEnum {
		HIGH_CARD,
		ONE_PAIR,
		TWO_PAIR,
		THREE_OF_A_KIND,
		STRAIGHT,
		FLUSH,
		FULL_HOUSE,
		FOUR_OF_A_KIND,
		STRAIGHT_FLUSH,
		ROYAL_FLUSH
	}
}
