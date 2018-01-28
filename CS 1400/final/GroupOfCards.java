/******************************************************
* Nathan Brooks
* CS 1400
* 
* This class lets you instantiate a group of cards, no matter
* the size or reason of use.  Deck, Hand
******************************************************/

import java.util.ArrayList;

public class GroupOfCards
{
  private ArrayList<Card> cards = new ArrayList<Card>();
  private int currentSize = 0;
  private int NUM;
  private int players;
  private int count = 0;
  
  
  public GroupOfCards(final int num)
  {
	  this.NUM = num;
  }
  //********************************************

  public int getCurrentSize()
  {
    return currentSize;
  }

  //****************************************************

  public void addCard(Card card)
  {
    cards.add(card);
    currentSize++;
  } // end addCard

  //****************************************************

  public Card removeCard(int index)
  {
    currentSize--;
    Card pulledCard = getCards().get(index);
    cards.remove(index);
    return pulledCard;
  } // end addCard

  //****************************************************

  public void display()
  {
    System.out.println("NUM \tSUIT");
    for (int i=0; i<currentSize; i++)
    {
      cards.get(i).display();
    }
    
  } // end display
  
  //************************************************
  
  public ArrayList<Card> getCards() 
  {
	return cards;
  }
  
  public int getNUM() 
  {
	return NUM;
  }
  
  public int getCount() 
  {
	return count;
}
} // end GroupOfCards