package yuki_project;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import yuki_project.pokerCard.RankEnum;
import yuki_project.pokerCard.SuitEnum;

public class Texas_Holdem_Hand_Evaluation implements Texas_Holdem_Constant {

	public static void checkRanking(Texas_Holdem_Player player, ArrayList<pokerCard> board) {

		ArrayList<pokerCard> rankingList = royalFlush(player, board);
		if (rankingList != null) {
			setPlayerRanking(player, 9.00, RankingEnum.ROYAL_FLUSH, rankingList);
			return;
		}

		rankingList = straightFlush(player, board);
		if (rankingList != null) {
			setPlayerRanking(player, 8.00 + rankingList.get(0).getRankToInt() * 0.01, RankingEnum.STRAIGHT_FLUSH,
					rankingList);
			return;
		}

		rankingList = fourOfAKind(player, board);
		if (rankingList != null) {
			setPlayerRanking(player,
					Math.floor((7.00 + rankingList.get(0).getRankToInt() * 0.01
							+ rankingList.get(4).getRankToInt() * 0.001) * 100000 + 0.5) / 100000,
					RankingEnum.FOUR_OF_A_KIND, rankingList);
			return;
		}

		rankingList = fullHouse(player, board);
		if (rankingList != null) {
			setPlayerRanking(player,
					6.00 + rankingList.get(0).getRankToInt() * 0.01 + rankingList.get(3).getRankToInt() * 0.001,
					RankingEnum.FULL_HOUSE, rankingList);
			return;
		}

		rankingList = flush(player, board);
		if (rankingList != null) {
			setPlayerRanking(player, 5.00 + getHighCardsInNum(rankingList, 0, 0), RankingEnum.FLUSH, rankingList);
			return;
		}

		rankingList = straight(player, board);
		if (rankingList != null) {
			setPlayerRanking(player, 4.00 + rankingList.get(0).getRankToInt() * 0.01, RankingEnum.STRAIGHT,
					rankingList);
			return;
		}

		rankingList = threeOfAKind(player, board);
		if (rankingList != null) {
			setPlayerRanking(player,
					3.00 + rankingList.get(0).getRankToInt() * 0.01 + getHighCardsInNum(rankingList, 3, 1),
					RankingEnum.THREE_OF_A_KIND, rankingList);
			return;
		}

		rankingList = twoPairs(player, board);
		if (rankingList != null) {
			setPlayerRanking(player,
					Math.floor(
							(2.00 + rankingList.get(0).getRankToInt() * 0.01 + rankingList.get(2).getRankToInt() * 0.001
									+ rankingList.get(4).getRankToInt() * 0.0001) * 10000 + 0.5)
							/ 10000.0,
					RankingEnum.TWO_PAIR, rankingList);
			return;
		}

		rankingList = pair(player, board);
		if (rankingList != null) {
			setPlayerRanking(player, Math.floor(
					(1.00 + rankingList.get(0).getRankToInt() * 0.01 + getHighCardsInNum(rankingList, 2, 1)) * 100000
							+ 0.5)
					/ 100000, RankingEnum.ONE_PAIR, rankingList);
			return;
		}

		rankingList = highCards(player, board);
		setPlayerRanking(player, Math.floor((getHighCardsInNum(rankingList, 0, 0)) * 1000000 + 0.5) / 1000000,
				RankingEnum.HIGH_CARD, rankingList);
	}

	// return royalFlush list if there is, return null if no
	// return in descending order
	public static ArrayList<pokerCard> royalFlush(Texas_Holdem_Player player, ArrayList<pokerCard> board) {

		ArrayList<pokerCard> temp = mergeList(player, board);
		for (pokerCard.SuitEnum suit : pokerCard.SuitEnum.values()) {
			ArrayList<pokerCard> result = new ArrayList<>();
			result.add(new pokerCard(suit, RankEnum.ACE));
			result.add(new pokerCard(suit, RankEnum.KING));
			result.add(new pokerCard(suit, RankEnum.QUEEN));
			result.add(new pokerCard(suit, RankEnum.JACK));
			result.add(new pokerCard(suit, RankEnum.CARD_10));
			if (temp.containsAll(result)) {
				return result;
			}
		}
		return null;
	}

	// return straightFlush if there is, return null if no.
	// return in descending order
	public static ArrayList<pokerCard> straightFlush(Texas_Holdem_Player player, ArrayList<pokerCard> board) {
		ArrayList<pokerCard> list = mergeList(player, board);
		ArrayList<pokerCard> result = new ArrayList<pokerCard>();
		RankEnum temp[] = RankEnum.values();
		for (SuitEnum suit : SuitEnum.values()) {
			result = new ArrayList<pokerCard>();
			// royalFlush is already checked, so it cant be royal flush
			result.add(new pokerCard(suit, RankEnum.ACE));
			result.add(new pokerCard(suit, RankEnum.KING));
			result.add(new pokerCard(suit, RankEnum.QUEEN));
			result.add(new pokerCard(suit, RankEnum.JACK));
			result.add(new pokerCard(suit, RankEnum.CARD_10));
			for (int i = temp.length - 1; i >= 3; i--) {
				if (list.containsAll(result)) {
					return result;
				}
				result.remove(0);
				if (i > 4) {
					result.add(new pokerCard(suit, temp[i - 5]));
				} else if (i == 4) {
					result.add(new pokerCard(suit, RankEnum.ACE));
				} else {
				}
			}
		}
		return null;
	}

	// return fourOfAKind if there is, return null if no.
	// return in descending order
	public static ArrayList<pokerCard> fourOfAKind(Texas_Holdem_Player player, ArrayList<pokerCard> board) {
		ArrayList<pokerCard> result = new ArrayList<pokerCard>();
		ArrayList<pokerCard> temp = mergeList(player, board);
		boolean isFour = false;
		for (RankEnum rank : RankEnum.values()) {
			for (SuitEnum suit : SuitEnum.values()) {
				if (!temp.contains(new pokerCard(suit, rank))) {
					result.clear();
					isFour = false;
					break;
				}
				isFour = true;
				result.add(new pokerCard(suit, rank));
			}
			if (isFour) {
				result.addAll(getHighCards(result, temp));
				return result;
			}
		}
		return null;
	}

	// return fullHouse if there is, return null if no.
	// return in descending order
	public static ArrayList<pokerCard> fullHouse(Texas_Holdem_Player player, ArrayList<pokerCard> board) {
		ArrayList<pokerCard> result = new ArrayList<pokerCard>();
		ArrayList<pokerCard> temp = mergeList(player, board);
		getOrderedCardList(temp);
		if (getPair(temp, 3) != null && getPair(temp, 2) != null) {
			result.addAll(getPair(temp, 3));
			result.addAll(getPair(temp, 2));
			return result;
		}
		return null;
	}

	// return flush if there is, return null if no.
	// return in descending order
	public static ArrayList<pokerCard> flush(Texas_Holdem_Player player, ArrayList<pokerCard> board) {
		ArrayList<pokerCard> result = new ArrayList<pokerCard>();
		ArrayList<pokerCard> temp = mergeList(player, board);
		getOrderedCardList(temp);
		for (SuitEnum suit : SuitEnum.values()) {
			// add all same suit color, since there is only 7 cards, it is
			// impossible to have two type of flush
			for (pokerCard card : temp) {
				if (card.getSuit() == suit) {
					result.add(card);
				}
			}
			// since we know the temp list is descending order, we can remove
			// the extra card for the flush from the end of the list.
			if (result.size() >= 5) {
				for (int i = result.size(); i > 5; i--) {
					result.remove(i - 1);
				}
				return result;
			} else {
				// if no more than 5 same suit
				result.clear();
			}
		}
		return null;
	}

	// return straight if there is, return null if no
	// return in descending order
	public static ArrayList<pokerCard> straight(Texas_Holdem_Player player, ArrayList<pokerCard> board) {
		ArrayList<pokerCard> result = new ArrayList<pokerCard>();
		ArrayList<pokerCard> list = mergeList(player, board);
		ArrayList<RankEnum> rankList = getRankList(list);
		RankEnum temp[] = RankEnum.values();

		ArrayList<RankEnum> temp1 = new ArrayList<RankEnum>();
		temp1.add(RankEnum.ACE);
		temp1.add(RankEnum.KING);
		temp1.add(RankEnum.QUEEN);
		temp1.add(RankEnum.JACK);
		temp1.add(RankEnum.CARD_10);

		for (int i = temp.length - 1; i >= 3; i--) {
			if (rankList.containsAll(temp1)) {
				for (RankEnum rank : temp1) {
					for (pokerCard card : list) {
						if (card.getRank() == rank) {
							result.add(card);
						}
					}
				}
				return result;
			}
			temp1.remove(0);
			if (i > 4) {
				temp1.add(temp[i - 5]);
			} else if (i == 4) {
				temp1.add(RankEnum.ACE);
			} else {
				// do nothing for the cycle
			}

		}
		return null;
	}

	// return threeOfAKind if there is, return null if no
	// return in descending order
	public static ArrayList<pokerCard> threeOfAKind(Texas_Holdem_Player player, ArrayList<pokerCard> board) {
		ArrayList<pokerCard> result = new ArrayList<pokerCard>();
		ArrayList<pokerCard> list = mergeList(player, board);
		getOrderedCardList(list);
		if (getPair(list, 3) != null) {
			result.addAll(getPair(list, 3));
			result.addAll(getHighCards(result, list));
			return result;
		}
		return null;
	}

	public static ArrayList<pokerCard> twoPairs(Texas_Holdem_Player player, ArrayList<pokerCard> board) {
		ArrayList<pokerCard> result = new ArrayList<pokerCard>();
		ArrayList<pokerCard> list = mergeList(player, board);
		getOrderedCardList(list);
		if (getPair(list, 2) != null) {
			result.addAll(getPair(list, 2));
			list.removeAll(getPair(list, 2));
			if (getPair(list, 2) != null) {
				result.addAll(getPair(list, 2));
				result.addAll(getHighCards(result, mergeList(player, board)));
				return result;
			}
		}
		return null;
	}

	public static ArrayList<pokerCard> pair(Texas_Holdem_Player player, ArrayList<pokerCard> board) {
		ArrayList<pokerCard> result = new ArrayList<pokerCard>();
		ArrayList<pokerCard> list = mergeList(player, board);
		getOrderedCardList(list);
		if (getPair(list, 2) != null) {
			result.addAll(getPair(list, 2));
			result.addAll(getHighCards(result, list));
			return result;
		}
		return null;
	}

	public static ArrayList<pokerCard> highCards(Texas_Holdem_Player player, ArrayList<pokerCard> board) {
		ArrayList<pokerCard> result = new ArrayList<pokerCard>();
		ArrayList<pokerCard> list = mergeList(player, board);
		return getHighCards(result, list);
	}

	private static ArrayList<pokerCard> getPair(ArrayList<pokerCard> mergedList, int pairSize) {
		ArrayList<pokerCard> checkedPair = new ArrayList<pokerCard>();
		for (pokerCard card1 : mergedList) {
			checkedPair.add(card1);
			for (pokerCard card2 : mergedList) {
				if (!card1.equals(card2) && card1.getRank().equals(card2.getRank())) {
					checkedPair.add(card2);
				}
			}
			if (checkedPair.size() == pairSize) {
				return checkedPair;
			}
			checkedPair.clear();
		}
		return null;
	}

	// make sure allCards is a temporary list that can be changed without.
	private static ArrayList<pokerCard> getHighCards(ArrayList<pokerCard> highestRanking, ArrayList<pokerCard> cards) {
		ArrayList<pokerCard> result = new ArrayList<>();
		cards.removeAll(highestRanking);
		getOrderedCardList(cards);
		for (int i = 0; i < 5 - highestRanking.size(); i++) {
			result.add(cards.get(i));
		}
		return result;
	}

	private static ArrayList<RankEnum> getRankList(ArrayList<pokerCard> list) {
		ArrayList<RankEnum> result = new ArrayList<>();
		for (pokerCard card : list) {
			result.add(card.getRank());
		}
		return result;
	}

	public static double getHighCardsInNum(ArrayList<pokerCard> cards, int index, int start) {
		double result = 0;
		int count = 2 + start;
		for (int i = index; i < cards.size(); i++) {
			result += cards.get(i).getRankToInt() * Math.pow(0.1, count);
			count++;
		}
		return result;
	}

	private static ArrayList<pokerCard> mergeList(Texas_Holdem_Player player, ArrayList<pokerCard> board) {
		ArrayList<pokerCard> result = new ArrayList<>();
		result.addAll(board);
		result.add(player.getCard(0));
		result.add(player.getCard(1));
		return result;
	}

	private static void setPlayerRanking(Texas_Holdem_Player player, double rankInNum, RankingEnum ranks,
			ArrayList<pokerCard> cards) {
		player.setRankingList(cards);
		player.setHandRanking(ranks);
		player.setRankInNum(rankInNum);
	}

	// order in descending order, same value doesn't matter on the suit
	public static void getOrderedCardList(ArrayList<pokerCard> result) {

		Collections.sort(result, new Comparator<pokerCard>() {

			public int compare(pokerCard c1, pokerCard c2) {
				return c1.getRankToInt() < c2.getRankToInt() ? 1 : -1;
			}

		});
	}

}
