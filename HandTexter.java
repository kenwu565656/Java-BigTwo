
public class HandTexter {
	public static void main(String[] args) {
		
		BigTwoCard a = new BigTwoCard(1, 5);
		Card b = new Card(2, 5);
		Card c = new Card(3, 5);
		Card d = new Card(0, 5);
		Card e = new Card(1, 5);
		Card f = new Card(1, 0);
		Card g = new Card(3, 0);
		Card t = new Card(0, 0);
		Card y = new Card(2, 8);
		Card u = new Card(1, 8);
		CardGamePlayer x = new CardGamePlayer();
		CardList mylist = new CardList();
		CardList mylist2 = new CardList();
		mylist.addCard(a);
		mylist.addCard(b);
		mylist.addCard(c);
		mylist.addCard(d);	
		mylist.addCard(e);
		
		mylist2.addCard(g);
		mylist2.addCard(t);
		mylist2.addCard(y);
		mylist2.addCard(u);
		
		mylist2.addCard(f);
		
		Hand single = new Quad(x, mylist);
		System.out.println(single.contains(g) + single.getType());
		
		Hand single2 = new FullHouse(x, mylist2);
		System.out.println(single2.isValid() + single2.getType());
		System.out.println(single2.beats(single));
	}
	

}
