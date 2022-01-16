/**
 * The Triple class is a subclass of the Hand class and are used to model a hand of triple in a Big Two card game.
It contains a public constructor. It also override the isValid() and getType() methods of the Hand class.
The getType() method return the name of the class as a String object.
The isValid() method return the this hand is valid or not as a boolean value.
 * 
 * @author ken
 * @version 1.0
 */
public class Triple extends Hand{
	/**
	 * Triple(CardGamePlayer player, CardList cards) ¡V a constructor for building a triple
with the specified player and list of cards.
	 * 
	 * @param player
	 * @param cards
	 */
	public Triple(CardGamePlayer player, CardList card) {
		super(player, card);
	}
	
	/**
	 * boolean isValid() ¡V a method for checking if this is a valid hand
	 * 
	 * @return boolean - represent the hand is valid or not 
	 */
	public boolean isValid() {
		if (this.size() == 3) {
			if ((this.getCard(0).getRank() == this.getCard(1).getRank()) && (this.getCard(0).getRank() == this.getCard(2).getRank()) && (this.getCard(1).getRank() == this.getCard(2).getRank())) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	/**
	 * String getType() ¡V a method for returning a string specifying the type of this hand.
	 * 
	 * @return string - represent the type of the hand
	 */
	public String getType() {
		return "Triple";
	}

}
