/******************************************************
* Nathan Brooks
* CS 1400
* 
* This class enables instances of the cards each player has in their hand
******************************************************/

import java.util.ArrayList;

public class Hand extends GroupOfCards
{
	int NUM;
	int maxNumOfCards;
	int shortest = 0;
	int currentSize = 0;
	
	
	public Hand(int num, int maxNumOfCards)
	{
		super(num);
		this.maxNumOfCards = maxNumOfCards;
	}
	//************************************************

	public void sort()
	{
		/* My initial for loop sorts the cards about 90% of the way,
		 * but I couldn't figure out how to make it work completely.
		 * by copying the for loop I got it to sort 100%.
		 * 
		 *  I know it's less efficient, but I was running out of time*/

		for (int i=0; i<2; i++)
		{
			for (int unsorted = 12; unsorted>0; unsorted--)
			{
				for (int j=0; j<unsorted; j++)
				{
					int check1 = (1000 * (getCards().get(unsorted).getSuit()) + (getCards().get(unsorted).getNum()));
					int check2 = (1000 * (getCards().get(j).getSuit()) + (getCards().get(j).getNum()));
					
					if (check1 <= check2)
					{
						getCards().add(unsorted, getCards().remove(j));
					}
				}
			}
		}		
	}
	
	

	//************************************************
  
	public void setShortest()
	{
			// 0 spades, 1 hearts, 2 diamonds, 3 clubs
		int numberOfSpades = 0;
		int numberOfHearts = 0;
		int numberOfDiamonds = 0;
		int numberOfClubs = 0;
		
			// test for aces, kings, or queens in spades
		boolean spadeAce = false;
		boolean spadeKing = false;
		boolean spadeQueen = false;
		
		shortest = 3;
		
		// test for aces, kings, or queens in spades
		if(find(0, 12) == 1)
		{
			spadeQueen = true;
		}
		if(find(0, 13) == 1)
		{
			spadeKing = true;
		}
		if(find(0, 14) == 1)
		{
			spadeAce = true;
		}
		
			// count the number of each suit
		for (int i=0; i<getCards().size(); i++)
		{
			switch (getCards().get(i).getSuit())
			{
			case 0:
				numberOfSpades++;
				break;
			
			case 1:
				numberOfHearts++;
				break;
				
			case 2:
				numberOfDiamonds++;
				break;
				
			case 3:
				numberOfClubs++;
				break;
			}
		}
		
			// 0 spades, 1 hearts, 2 diamonds, 3 clubs
			// If the number of	diamonds is less than or equal to the number of clubs, change shortest to diamonds.
		if (numberOfDiamonds <= numberOfClubs)
		{
			shortest = 2;
		}
		
			//If the number of spades is less than or equal to the shorter of those two, and your spades do
			//not include Ace, King, or Queen, change shortest to spades.
		if (shortest == 3) // if shortest = clubs
		{
			if (numberOfSpades <= numberOfClubs && spadeAce == false && spadeKing == false && spadeQueen == false)
			{
				shortest = 0;
			}
		}
		else if (shortest == 2) // if shortest = diamonds
		{
			if (numberOfSpades <= numberOfDiamonds && spadeAce == false && spadeKing == false && spadeQueen == false)
			{
				shortest = 0;
			}
		}
	}
  
	public void getShortest()
	{
		
	}
  
	//************************************************
  
	public Card playACard(Game game, Trick trick)
	{
		/* The playACard method is the most difficult one, because it contains many of the rules
		and all of the strategy for winning. We do not know the best way to write this method,
		because we are not perfect hearts players ourselves, so this is just a suggestion that works
		reasonably well. All of the methods listed after this one (except for the count method)
		are intended to be used by this method to make this method as simple as possible. Look at
		them before trying to write this method. */
		
		/* If the current size of the trick is zero (you are the first hand), let a local integer called
		index equal the highest card in your shortest suit, but if this suit is a void let index equal
		the lowest card in any suit. If the current size of the trick is one less than the total number
		of players (you are the last hand), and if the trick does not have the queen of spades or
		any hearts, let index equal the value returned by the findHighest(int suit)
		method.
		Next, deal with cases where you are the last hand and there are no bad cards in the trick.
		Since many of the helper methods return -1 if they fail, you can tighten the code in the
		playACard method by using an assignment to index inside an empty else-if
		statement, like this:
		else if ((trick.getCurrentSize() == game.PLAYERS - 1)
		&& !trick.getHearts() && !trick.getQueen()
		&& (index = findLastHigh(suit)) >= 0);
		Then, see what to do if you are a middle hand or the last hand with bad cards already in
		the trick: 
		
		else if ((index = findHighestBelow(winningCard)) >= 0);
		else if ((index = findMiddleHigh(game, suit)) >= 0);
		Then, see what to do if you are void, and able to discard a bad card or your own:
		else if ((index = find(12, 3)) >= 0); // queen of Spades
		else if ((index = find(14, 3)) >= 0); // Ace of Spaces
		else if ((index = find(13, 3)) >= 0); // King of Spades
		else if ((index = findHighest(2)) >= 0); // heart
		else
		{
		index = findHighest();
		}
		Then, remove the card with the resulting value of index, update the trick, update the
		game, and return the card selected. */
		
		
		
		return null; 
	}
	
	//************************************************
	
	public int findLowest()
	{
			/*Use the findLowest(int suit) method to find the lowest club dealt, to start the
			game. You’ll also need it in the rare situation when you have the lead, hearts have not
			been broken, and hearts are all you have left in your hand. Return the index of the lowest
			numbered card in the indicated suit. If you have no cards in that suit, return -1.
			*/
		return 0;
	}
	
	//************************************************
	
	private int findCount(int suit)
	{
		return 0;
	}
	
	//************************************************
	
	private int find(int num, int suit)
	{
		for (int i=0; i<getCards().size(); i++)
		{
			if (getCards().get(i).getSuit() == suit && getCards().get(i).getNum() == num)
			{

				return 1;
			}
		}
		return 0;
	}
	
	//************************************************
	
	private int findHighest(int suit)
	{
		return 0;
	}	
	
	//************************************************
	
	private int findLowest(Game game)
	{
		return 0;
	}
	
	//************************************************
	
	private int findLastHigh(int suit)
	{
		return 0;
	}
	
	//************************************************
	
	private int findHighestBelow(Card winningCard)
	{
		return 0;
	}
	
	public void addCard(Card card)
	{
		getCards().add(card);
	    currentSize++;
	}

	//************************************************
	
	public Card playACard()
	{
			/*If the current size of the trick is zero (you are the first hand), let a local integer called
		  index equal the highest card in your shortest suit, but if this suit is a void let index equal
		  the lowest card in any suit. If the current size of the trick is one less than the total number
		  of players (you are the last hand), and if the trick does not have the queen of spades or
		  any hearts, let index equal the value returned by the findHighest(int suit)
		  method.
		  Next, deal with cases where you are the last hand and there are no bad cards in the trick.
		  Since many of the helper methods return -1 if they fail, you can tighten the code in the
		  playACard method by using an assignment to index inside an empty else-if
		  statement, like this:
		  else if ((trick.getCurrentSize() == game.PLAYERS - 1)
		  && !trick.getHearts() && !trick.getQueen()
		  && (index = findLastHigh(suit)) >= 0);
		  Then, see what to do if you are a middle hand or the last hand with bad cards already in
		  the trick:
			  else if ((index = findHighestBelow(winningCard)) >= 0);
			  else if ((index = findMiddleHigh(game, suit)) >= 0);
			  Then, see what to do if you are void, and able to discard a bad card or your own:
			  else if ((index = find(12, 3)) >= 0); // queen of Spades
			  else if ((index = find(14, 3)) >= 0); // Ace of Spaces
			  else if ((index = find(13, 3)) >= 0); // King of Spades
			  else if ((index = findHighest(2)) >= 0); // heart
			  else
			  {
			  index = findHighest();
			  }
		  Then, remove the card with the resulting value of index, update the trick, update the
		  game, and return the card selected.  */
		return null;
	}
} // end Hand