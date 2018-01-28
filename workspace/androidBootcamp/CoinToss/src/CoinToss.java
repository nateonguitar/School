import java.util.Random;

class CoinToss 
{
	static int coinValue = 2;
	static int heads = 0;
	static int tales = 0;
	
	public static void main(String args[])
	{

		for(int i = 0; i<100; i++)
		{
			flipCoin();
		}
		
		System.out.println("Heads: " + heads + "\nTales: " + tales + "\n");
		
		if(heads > tales)
		{
			System.out.println("Heads wins!");
		}
		else if(heads == tales)
		{
			System.out.println("Draw!");
		}
		else
		{
			System.out.println("Tales wins!");
		}
	}
	
	
	//------------------------------------
	
	static void flipCoin()
	{
		Random rand = new Random();
		coinValue = rand.nextInt(2);
		
		testCoin();
	}
	
	//-------------------------------------
	
	static void testCoin()
	{
		if( (int)coinValue == 0 )
		{
			tales++;
		}
		else
		{
			heads++;
		}
	}
}

