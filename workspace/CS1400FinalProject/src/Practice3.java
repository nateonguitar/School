
public class Practice3 
{
	private int num;
	private int suit;
	
	//******************************************************
	
	public Practice3(int num, int suit)
	{
		this.num = num;
		this.suit = suit;
	}
	
	public int getNum() 
	{
		return num;
	}
	
	public int getSuit() 
	{
		return suit;
	}
	
	public String display()
	{
		return Integer.toString(num) + "\t" + Integer.toString(suit);
	}
}
