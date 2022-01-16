/**
 * The BigTwoCard class is a subclass of the Card class and is used to model a card used in a
Big Two card game. It should override the compareTo() method it inherits from the Card
class to reflect the ordering of cards used in a Big Two card game.
 * 
 * @author ken
 * @version 1.0
 */
public class BigTwoCard extends Card{
	/**
	 * BigTwoCard(int suit, int rank) ¡V a constructor for building a card with the specified
suit and rank. suit is an integer between 0 and 3, and rank is an integer between 0 and
12.
	 * 
	 * @param suit - the integer representing the suit of the card
	 * @param rank - the integer representing the rank of the card
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
	}
	
	/**
	 * int compareTo(Card card) ¡V a method for comparing the order of this card with the
specified card. Returns a negative integer, zero, or a positive integer when this card is
less than, equal to, or greater than the specified card. The comparison is based on the BigTwo Game Rule.
	 * 
	 * @param card - the card to be compared with this object
	 * @return an integer to represent the comparison result 
	 */
	public int compareTo(Card card) {
		int true_rank, true_rank_card;
		if (this.rank == 0) {
			true_rank = 13;
		} else if (this.rank == 1) {
			true_rank = 14;
		}else {
			true_rank = this.rank;
		}
		if (card.rank == 0) {
			true_rank_card = 13;
		} else if (card.rank == 1) {
			true_rank_card = 14;
		}else {
			true_rank_card = card.rank;
		}
		if (true_rank > true_rank_card) {
			return 1;
		} else if (true_rank < true_rank_card) {
			return -1;
		} else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
	}
}

