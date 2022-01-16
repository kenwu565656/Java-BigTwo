/**
 * The Hand class is a subclass of the CardList class and is used to model a hand of cards. It has
a private instance variable for storing the player who plays this hand. It also has methods for
getting the player of this hand, checking if it is a valid hand, getting the type of this hand,
getting the top card of this hand, and checking if it beats a specified hand. 
 * 
 * @author ken
 * @version 1.0
 */
public abstract class Hand extends CardList{
	private CardGamePlayer player;
	
	/**
	 * Hand(CardGamePlayer player, CardList cards) ¡V a constructor for building a hand
with the specified player and list of cards.
	 * 
	 * @param player
	 * @param cards
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player = player;
		for (int i = 0; i < cards.size(); i++) {
			this.addCard(cards.getCard(i));
		}
	}
	
	/**
	 * CardGamePlayer getPlayer() ¡V a method for retrieving the player of this hand.
	 * 
	 * @return a CardGamePlayer object reference - this hand's player
	 */
	public CardGamePlayer getPlayer() {
		return this.player;
	}
	
	/**
	 * Card getTopCard() ¡V a method for retrieving the top card of this hand
	 * 
	 * @return a card object reference of the top card of this hand
	 */
	public Card getTopCard() {
		this.sort();
		return this.getCard(this.size() - 1);
	}
	
	/**
	 * boolean isValid() ¡V a method for checking if this is a valid hand
	 * 
	 * @return boolean - represent the hand is valid or not 
	 */
	public abstract boolean isValid();
			
	/**
	 * String getType() ¡V a method for returning a string specifying the type of this hand.
	 * 
	 * @return string - represent the type of the hand
	 */
	public abstract String getType();
		
	/**
	 * boolean beats(Hand hand) ¡V a method for checking if this hand beats a specified hand.
	 * 
	 * @param hand
	 * @return boolean - represent the this hand beats the specified hand or not
	 */
	boolean beats(Hand hand) {
		if (this.size() != hand.size() || this == null) {
			return false;
		}else{
			if ((this.getType() == "Single" || this.getType() == "Pair" || this.getType() == "Triple") && this.getType() == hand.getType()) {
				if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
					return true;
				}else {
					return false;
				}
			}else {
				String[] order = {"Straight", "Flush", "FullHouse", "Quad", "StraightFlush"};
				int order_of_this = 0;
				for (int i = 0; i < order.length; i++) {
					if (this.getType() == order[i]) {
						order_of_this = i;
						break;
					}
				}
				int order_of_hand = 5;
				for (int w = 0; w < order.length; w++) {
					if (hand.getType() == order[w]) {
						order_of_hand = w;
						break;
					}
				}
				if (order_of_this > order_of_hand) {
					return true;
				}else if (order_of_this < order_of_hand) {
					return false;
				}else {
					if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
						return true;
					}else {
						return false;
					}
				}
				
			}
			
		}
	}
}
