/******************************************************
* Nathan Brooks
* CS 1400
* 
* The deck of cards before being shuffled and delt.
* Is a group of cards
******************************************************/

public class Deck extends GroupOfCards
{
  public static final int TOTAL_CARDS = 52;

  //********************************************

  public Deck(int d)
  {
	  super(d);
	  for (int i=0; i<TOTAL_CARDS; i++)
	  {
		  addCard(new Card((2 + i%13), (i/13)));
	  }
  } // end constructor

  //********************************************

  public void shuffle()
  {
	  for(int i=0; i<TOTAL_CARDS; i++)
	  {
		  Card temp = new Card(0, 0);
		  
		  getCards().add(getCards().remove((int)(Math.random() * 52)));
	  }	  
  }

  //********************************************

  public Card dealCard()
  {
	  	// Pull a card and remove it from the deck
	  return removeCard(0);
  }
} // end class Deck