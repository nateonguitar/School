
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
