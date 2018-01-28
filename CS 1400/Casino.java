import java.util.*;

public class Casino {

	public static void main (String[] args) {
		
		Scanner console = new Scanner (System.in);
		
		double wallet = 100.00; //Start the user off with $100
		double bet = 0.00; //User bet
		double result = 0.00; //To store the winnings/losses of each game
		String input = ""; //User input

		//Create instance of the Roulette game (that you're creating)
		Roulette game = new Roulette();
		
		do {
		
			System.out.println("How much would you like to bet?");
			bet = console.nextDouble();
			wallet = wallet - bet;
			
			//Play a round and store the results
			result = game.betOnce(bet);
			
			//Flush the buffer
			console.nextLine();
			
			//Update wallet amount and notify user
			wallet = wallet + result;
			System.out.println("You have $" + wallet + " in your wallet.");
			System.out.println("Bet again?  Y or N?");
			input = console.nextLine();
		
		} while (!input.equalsIgnoreCase("N"));
	
	} //end main

} //end class