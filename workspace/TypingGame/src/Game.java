import java.util.ArrayList;
import java.util.Scanner;

/**************************************************
 * 
 * Main method for a typing game
 *
 **************************************************/

public class Game 
{
	public static void main(String[] args) 
	{
		try
		{
			Scanner input = new Scanner(System.in);
			int numPlayers;
			ArrayList players = new ArrayList<>();
			
			System.out.print("How many players are there (1 or 2): ");
			numPlayers = input.nextInt();
			
			
			
		}
		catch(Exception e)
		{
			System.out.println("Invalid");
		}
	}
}
