/******************************************************
* Nathan Brooks
* CS 1400
* 
* Didn't get a whole lot done on this file, tried to follow
* the UML, but I don't completely know how to play this game,
* or what the "Trick" is supposed to be.
*
* I looked it up and couldn't figure it out without uncertainty
* I think this is supposed to be each round?  But I don't really
* know how to impliment it's function, since I don't understand
* it's function.
*
* I ran out of time.
******************************************************/

public class Trick extends GroupOfCards
{
	private int winner;
	private Card winningCard;
	private boolean hearts = false;
	private boolean queen = false;
	private int players;
	
	//************************************************
	
	public Trick(int players)
	{
		super(players);
	}
	
	//************************************************
	
	public int getWinner() 
	{
		return winner;
	}
	
	//************************************************
	
	public Card getWinningCard() 
	{
		return winningCard;
	}
	
	//************************************************
	
	public void update(int playerNum, Card card)
	{
		
	}
	
	//************************************************
	
	private boolean isWinner(Card card)
	{
		return false;
	}
	
	//************************************************
} // end class Trick