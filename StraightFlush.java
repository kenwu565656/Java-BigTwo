/**
 * The StraightFlush class is a subclass of the Hand class and are used to model a hand of straight flush in a Big Two card game.
It contains a public constructor. It also override the isValid() and getType() methods of the Hand class.
The getType() method return the name of the class as a String object.
The isValid() method return the this hand is valid or not as a boolean value.
 * 
 * @author ken
 * @version 1.0
 */
public class StraightFlush extends Hand{
	/**
	 * StraightFlush(CardGamePlayer player, CardList cards) ¡V a constructor for building a straight flush
with the specified player and list of cards.
	 * 
	 * @param player
	 * @param cards
	 */
	public StraightFlush(CardGamePlayer player, CardList card) {
		super(player, card);
	}
	
	/**
	 * boolean isValid() ¡V a method for checking if this is a valid hand
	 * 
	 * @return boolean - represent the hand is valid or not 
	 */
	public boolean isValid() {
		CardList card = new CardList();
		for (int i = 0; i < this.size(); i++) {
			card.addCard(getCard(i));
		}
		Flush a = new Flush(this.getPlayer(), card); 
		Straight b = new Straight(this.getPlayer(), card);
		return (a.isValid()) && (b.isValid());
	}
	
	/**
	 * String getType() ¡V a method for returning a string specifying the type of this hand.
	 * 
	 * @return string - represent the type of the hand
	 */
	public String getType() {
		return this.getClass().getSimpleName();
	}
	

}
