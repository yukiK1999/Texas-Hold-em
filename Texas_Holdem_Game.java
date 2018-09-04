package yuki_project;

import java.util.ArrayList;
import java.util.Scanner;

public class Texas_Holdem_Game {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		// int player = numberOfPlayer(input);
		int player = 4;
		Texas_Holdem_Player[] players = startingMoney(player, input);
		//better to make temp arraylist for each round. 
		
		// int sBlind = blind(input);
		// int dealer = 0;
		Texas_Holdem_Board board = new Texas_Holdem_Board();
		// while (players.length != 1) {
		// int pot = 0;
		card_Deck deck = shuffleNewDeck();
		preFlop(players, deck);
		for (int i = 0; i < players.length; i++) {
			System.out.println(players[i].toString());
		}
		// force bet from small and big blinds
		// make choices
		// if(everybody call continue to next phase)
		// else if( all in)
		// else the pot gives to the winner
		flop(board, deck);
		setRanks(players, board);
		System.out.println(board.toString());
		// make choices, skip folded player and all in player.
		// if(everybody call continue to next phase)
		// else if( all in)
		// else the pot gives to the winner
		turn(board, deck);
		setRanks(players, board);

		System.out.println(board.toString());
		// make choices, skip folded player and all in player.
		// if(everybody call continue to next phase)
		// else if( all in)
		// else the pot gives to the winner
		river(board, deck);

		System.out.println(board.toString());
		setRanks(players, board);

		ArrayList<Texas_Holdem_Player> winners = getWinners(players);
		for (Texas_Holdem_Player winner : winners) {
			System.out.println(winner);
		}
		System.out.println("Winning rank is a:" + winners.get(0).getHandRanking() + winners.get(0).getRankInNum());
		System.out.println(winners.get(0).getRankingList());

		// make choices, skip folded player and all in player.
		// if(everybody call continue to next phase)
		// else if( all in)
		// else the pot gives to the winner

		// show hand and judge who wins, assuming everyone didn't fold at this
		// stage

	}

	private static void setRanks(Texas_Holdem_Player[] players, Texas_Holdem_Board board) {
		for (Texas_Holdem_Player player : players) {
			Texas_Holdem_Hand_Evaluation.checkRanking(player, board.getBoard());
		}
	}

	private static ArrayList<Texas_Holdem_Player> getWinners(Texas_Holdem_Player[] players) {
		double max = 0;
		ArrayList<Texas_Holdem_Player> result = new ArrayList<>();
		for (Texas_Holdem_Player player : players) {
			if (max < player.getRankInNum()) {
				max = player.getRankInNum();
				result.clear();
				result.add(player);
			} else if (max == player.getRankInNum()) {
				result.add(player);
			}
		}
		return result;
	}

	// did this for clarity
	private static void river(Texas_Holdem_Board board, card_Deck deck) {
		board.setBurnCard(deck.draw());// third burn card
		board.setBoardCard(deck.draw()); // river card
	}

	private static void turn(Texas_Holdem_Board board, card_Deck deck) {
		board.setBurnCard(deck.draw());// second burn card
		board.setBoardCard(deck.draw()); // turn card
	}

	private static void flop(Texas_Holdem_Board board, card_Deck deck) {
		board.setBurnCard(deck.draw());// first burn card
		for (int i = 0; i < 3; i++) {
			board.setBoardCard(deck.draw());// first three community card
		}

	}

	private static card_Deck shuffleNewDeck() {
		card_Deck result = new card_Deck();
		for (int i = 0; i < 5; i++) {
			result.shuffle();
		}
		return result;
	}

	// give two card to each player
	private static void preFlop(Texas_Holdem_Player[] players, card_Deck deck) {
		for (Texas_Holdem_Player hand : players) {
			hand.setBothCards(deck.draw(), deck.draw());// I guess doesn't
														// really matter
		}
	}

	private static int blind(Scanner input) {
		System.out.println("How much is the starting blind: ");
		return input.nextInt();
	}

	private static int numberOfPlayer(Scanner input) {
		System.out.println("How many players are in this game: ");
		return input.nextInt();
	}

	private static Texas_Holdem_Player[] startingMoney(int player, Scanner input) {
		Texas_Holdem_Player[] result = new Texas_Holdem_Player[player];
		System.out.println("How much is the starting money: ");
		int money = input.nextInt();
		for (int i = 0; i < result.length; i++) {
			result[i] = new Texas_Holdem_Player();
			result[i].money = money;
		}
		return result;
	}

}
