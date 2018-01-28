import java.util.Scanner;

public class GameDriver 
{

	public static void main(String[] args)
	{
		Scanner stdIn = new Scanner(System.in);
		String again;
		Game game;
		
		do
		{
			game = new Game();
			game.playAGame();
			System.out.print("Play another game (y/n)?: ");
			again = stdIn.nextLine();
		} while (again.equals("y"));
	} // end main
} // end class GameDriver
