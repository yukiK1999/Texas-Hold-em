package yuki_project;

import java.util.ArrayList;

public class Texas_Holdem_Player implements Texas_Holdem_Constant {
	private pokerCard[] hand = new pokerCard[PLAYER_HAND];
	public int money;
	private RankingEnum handRank;
	// 5 cards that is the highest for the player
	private ArrayList<pokerCard> cardList;
	private double rankInNum;

	// set Rank in double for breaking tie
	public void setRankInNum(double rankInNum) {
		this.rankInNum = rankInNum;
	}

	// get Rank in double for breaking tie
	public double getRankInNum() {
		return rankInNum;
	}

	// set type of hand in RankingEnum.
	public void setHandRanking(RankingEnum rank) {
		handRank = rank;
	}

	// get type of hand in RankingEnum.
	public RankingEnum getHandRanking() {
		return handRank;
	}

	// set the biggest list of board and hand.
	public void setRankingList(ArrayList<pokerCard> rankingList) {
		this.cardList = rankingList;
	}

	// get the biggest list of board and hand.
	public ArrayList<pokerCard> getRankingList() {
		return cardList;
	}

	// set the hand with two cards
	public void setBothCards(pokerCard one, pokerCard two) {
		hand[0] = one;
		hand[1] = two;
	}

	// get the cards in hand
	public pokerCard[] getHand() {
		return hand;
	}

	public pokerCard getCard(int index) {
		return hand[index];
	}

	// return visual representation of hands
	public String toString() {
		
		return "The hand is: " + hand[0].toString() + " and " + hand[1].toString();
	}
}
