package yuki_project;

import java.util.ArrayList;

public class Texas_Holdem_Board implements Texas_Holdem_Constant {
	private ArrayList<pokerCard> board = new ArrayList<>();
	private ArrayList<pokerCard> burnCard = new ArrayList<>();

	// default constructor
	public Texas_Holdem_Board() {
	}

	public void clearBoard() {
		board = new ArrayList<>();
		burnCard = new ArrayList<>();
	}

	public void setBoardCard(pokerCard card) {
		board.add(card);
	}

	public pokerCard getBoardCard(int index) {
		return board.get(index);
	}
	
	public ArrayList< pokerCard >getBoard() {
		return board;
	}

	public void setBurnCard(pokerCard card) {
		burnCard.add(card);
	}

	public pokerCard getBurnCard(int index) {
		return burnCard.get(index);
	}

	@Override
	public String toString() {
		String result = "";
		for (pokerCard card : board) {
			result += card.toString() +"\n";
		}
		return result +"\n";
	}
}