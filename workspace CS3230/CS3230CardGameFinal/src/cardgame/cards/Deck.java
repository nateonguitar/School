package cardgame.cards;

public class Deck extends GroupOfCards {
	
	int x = -150;
	int y = 250;
	int startAngle = 90;
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
			
			cards.add(new Card(Numbers.ACE,    suit, x, y, startAngle));
			cards.add(new Card(Numbers.TWO,    suit, x, y, startAngle));
			cards.add(new Card(Numbers.THREE,  suit, x, y, startAngle));
			cards.add(new Card(Numbers.FOUR,   suit, x, y, startAngle));
			cards.add(new Card(Numbers.FIVE,   suit, x, y, startAngle));
			cards.add(new Card(Numbers.SIX,    suit, x, y, startAngle));
			cards.add(new Card(Numbers.SEVEN,  suit, x, y, startAngle));
			cards.add(new Card(Numbers.EIGHT,  suit, x, y, startAngle));
			cards.add(new Card(Numbers.NINE,   suit, x, y, startAngle));
			cards.add(new Card(Numbers.TEN,    suit, x, y, startAngle));
			cards.add(new Card(Numbers.JACK,   suit, x, y, startAngle));
			cards.add(new Card(Numbers.QUEEN,  suit, x, y, startAngle));
			cards.add(new Card(Numbers.KING,   suit, x, y, 90));
		}
	}
}
