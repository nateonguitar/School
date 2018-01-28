package brooksNathan;

public class Deck extends GroupOfCards {
	public Deck(){
		super();
		
		// fill the deck
		fillDeck();
	}
	
	//-----------------------------------------------------------------------------
	
	public void fillDeck(){
		Suits suit;
		
		for(int i=0; i<4; i++){
			switch(i){
			case 0:
				suit = Suits.HEARTS;
				break;
			case 1:
				suit = Suits.DIAMONDS;
				break;
			case 2:
				suit = Suits.CLUBS;
				break;
			default:
				suit = Suits.SPADES;
				break;
			}
			
			cards.add(new Card(Numbers.ACE, suit));
			cards.add(new Card(Numbers.TWO, suit));
			cards.add(new Card(Numbers.THREE, suit));
			cards.add(new Card(Numbers.FOUR, suit));
			cards.add(new Card(Numbers.FIVE, suit));
			cards.add(new Card(Numbers.SIX, suit));
			cards.add(new Card(Numbers.SEVEN, suit));
			cards.add(new Card(Numbers.EIGHT, suit));
			cards.add(new Card(Numbers.NINE, suit));
			cards.add(new Card(Numbers.TEN, suit));
			cards.add(new Card(Numbers.JACK, suit));
			cards.add(new Card(Numbers.QUEEN, suit));
			cards.add(new Card(Numbers.KING, suit));
		}
	}
}
