public class Card
{
	private int num;
	private int suit;
	
	//******************************************************
	
	public Card(int num, int suit)
	{
		this.num = num;
		this.suit = suit;
	}
	
	//******************************************************
	
	public int getNum() 
	{
		return num;
	}
	
	//******************************************************
	
	public int getSuit() 
	{
		return suit;
	}
	
	//******************************************************
	
	public void display()
	{
		System.out.println(num + "\t" + suit);
	}
} // end class Card
