/**
 * The Quad class is a subclass of the Hand class and are used to model a hand of quad in a Big Two card game.
It contains a public constructor. It also override the isValid() and getType() methods of the Hand class.
The getType() method return the name of the class as a String object.
The isValid() method return the this hand is valid or not as a boolean value.
 * 
 * @author ken
 * @version 1.0
 */
public class Quad extends Hand{
	/**
	 * Quad(CardGamePlayer player, CardList cards) ¡V a constructor for building a quad
with the specified player and list of cards.
	 * 
	 * @param player
	 * @param cards
	 */
	public Quad(CardGamePlayer player, CardList card) {
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
			int rank;
			int start;
			int end;
		    if (this.getCard(3).getRank() == this.getCard(4).getRank()) {	    	
		        rank = this.getCard(1).getRank();
		        start = 2;
		        end = 5;
		    }else {
		    	rank = this.getCard(0).getRank();
		    	start = 1;
		    	end = 4;
		    }
		    	for (int i = start; i < end; i++) {
		    		if (this.getCard(i).getRank() != rank) {
		    			return false;
		    		}
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
		return "Quad";
	}
	
}
