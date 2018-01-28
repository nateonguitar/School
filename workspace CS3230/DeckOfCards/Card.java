package brooksNathan;

public class Card {
	private Numbers number;
	private Suits suit;
	private String faceValue;
	
	//-----------------------------------------------------------------------------
	
	public Card(Numbers num, Suits suit){
		this.number = num;
		this.suit = suit;
	}
	
	//-----------------------------------------------------------------------------
	
	public Numbers getNumber(){
		return this.number;
	}
	
	//-----------------------------------------------------------------------------
	
	public Suits getSuit(){
		return this.suit;
	}
}