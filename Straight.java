/**
 * The Straight class is a subclass of the Hand class and are used to model a hand of straight in a Big Two card game.
It contains a public constructor. It also override the isValid() and getType() methods of the Hand class.
The getType() method return the name of the class as a String object.
The isValid() method return the this hand is valid or not as a boolean value.
 * 
 * @author ken
 * @version 1.0
 */
public class Straight extends Hand{
	/**
	 * Straight(CardGamePlayer player, CardList cards) ¡V a constructor for building a straight
with the specified player and list of cards.
	 * 
	 * @param player
	 * @param cards
	 */
	public Straight(CardGamePlayer player, CardList card) {
		super(player, card);
	}
	
	/**
	 * boolean isValid() ¡V a method for checking if this is a valid hand
	 * 
	 * @return boolean - represent the hand is valid or not 
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			this.sort();
			int real_rank;
			int previous_rank = this.getCard(0).getRank();
			for (int i = 1; i < this.size(); i++) {
				if (this.getCard(i).getRank() == 0) {
					real_rank = 13;
				}else if(this.getCard(i).getRank() == 1){
					real_rank = 14;
				}else {
					real_rank = this.getCard(i).getRank();
				}
				if (real_rank - previous_rank != 1) {
					return false;
				}
				previous_rank = real_rank;
			}
            return true;
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
		return "Straight";
	}
	
}
