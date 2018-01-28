package brooksNathan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameOfBlackJack implements Game {
	private Comparer comparer;
	private int numDecks;
	private int numPlayers;
	private int aceCount = 0;
	private boolean looping = true;
	private Dealer dealer; // has the specified number of decks
	private Boolean someoneWon = false;
	private ArrayList<Player> players;
	private int numRoundsPlayed = 0;
	private final int MAX_NUM_ROUNDS = 10000;
	private Scanner in = new Scanner(System.in);
	private int dealerIndex;
	private Random rand = new Random();
	private int chance = 0;
	private boolean gotToEndOfAlternateTracker;
	private int highestScore = 0;
	boolean hitChecker = true;
	String hit = "";
	int threshholdForHit;
	
	// ----------------------------------------------------------

	@Override
	public void initialize() {
		///////////////////////////////////////////////////////////////////////////////////
		// can be used to compare any two values
		//
		// comparing "x" to "y = 21" is a way to see if someone needs to hit,
		/////////////////////////////////////////////////////////////////////////////////// lost,
		/////////////////////////////////////////////////////////////////////////////////// or
		/////////////////////////////////////////////////////////////////////////////////// won
		// 1 means they went over 21, possible loss
		// 0 means exactly 21
		// -1 means they're under 21
		comparer = (x, y) -> {
			if (x < y) {
				return -1;
			} else if (x > y) {
				return 1;
			} else {
				return 0;
			}
		};
		///////////////////////////////////////////////////////////////////////////////////

		players = new ArrayList<>(); // a collection holding all the players as
										// to iterate through for dealing cards

		System.out.println("*************");
		System.out.println("* Blackjack *");
		System.out.println("*************"); 

		// set number of decks
		System.out.print("Decks: ");
		while (true) {
			try{
				numDecks = Integer.parseInt(in.next());
				break;
			}
			catch(Exception e){		
				System.out.println("* Error: enter an integer *" + "\nDecks: ");
			}
		}

		// set number of players
		System.out.print("\nPlayers: ");
		while (true) {
			try{
				numPlayers = Integer.parseInt(in.next());
				break;
			}
			catch(Exception e){		
				System.out.println("* Error: enter an integer *" + "\nPlayers: ");
			}
		}
				
		// dealer extends player
		dealer = new Dealer(numDecks);

		// players.getLast(0); will always return the dealer
				players.add(dealer);
				
		// fill the players array with players
		for (int i = 0; i < numPlayers; i++) {
			players.add(new Player());
		}
		
		
		// players.get(dealerIndex); will return the dealer specifically
		dealerIndex = players.size() - 1;
		
		
		//////////////////////////////////////////////////////////
		// to show that the dealer now has multiple decks just uncomment this
		//
		// dealer.getDeck().orderHand();
		// System.out.println(dealer.getDeck().toString());
		//
		//////////////////////////////////////////////////////////

		// shuffle the deck the correct number of times
		for (int i = 0; i < numDecks; i++) {
			dealer.getDeck().shuffle();
		}

		// set names and
		// deal 2 cards to each player
		for (Player player : players) {
			if(player instanceof Dealer){
				player.setName("Dealer");
			}
			else{
				System.out.print("Enter a player name: ");
				player.setName(in.next());
			}
			
			// CAN TEST SPECIFIC CARDS
			// BUT YOU HAVE TO USE ENOUGH DECKS TO COVER THAT NUMBER OF CARDS
			// DON'T PULL 12 ACES OUT OF 2 DECKS
			
			//dealer.dealCard(player, Numbers.ACE);
			//dealer.dealCard(player, Numbers.ACE);
			//dealer.dealCard(player, Numbers.ACE);
			//dealer.dealCard(player, Numbers.QUEEN);
			//dealer.dealCard(player, Numbers.THREE);
			
			//dealer.dealCard(player, Numbers.THREE);
			//dealer.dealCard(player, Numbers.NINE);
			
			dealer.dealCard(player);
			dealer.dealCard(player);
		}
	}

	// ----------------------------------------------------------

	@Override
	public void play() {
		/*
		Player has an alternateTracker ArrayList that is parallel to the player's cards
		it works like this:
		
			|A| -> |true | -- count Ace as 11
			|3| -> |false|
			|K| -> |false|
			|A| -> |true | -- count Ace as 11
		
		*now the score is 35 but that's a bust, so we can flip an ace down to see if score is good
		
			|A| -> |false| -- count Ace as 1
			|3| -> |false|
			|K| -> |false|
			|A| -> |true | -- count Ace as 11
		
		*now the score is 25, still bust
		
			|A| -> |false| -- count Ace as 1
			|3| -> |false|
			|K| -> |false|
			|A| -> |false| -- count Ace as 1
		
		*now the score is 15, the player will take another card
		
		*/
		// for each player, try to get 21
		// following the blackjack rules
		// last player is the dealer
		for(Player player : players){
			if(!(player instanceof Dealer)){
				System.out.println("\n\n\n   *** Press any key to start the next player ***");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("\n\n****************************************\n*** " + player.getName() + "'s turn\n****************************************");
			looping = true;
			
			// reset aceCount for the next player to use
			aceCount = 0;
			
			while(looping == true){
				// reset the player's alternateTracker 
				// so it is the same size as the player's hand and all false
				player.resetAlternateTracker();
				
				
				// count number of aces this player has
				// set to alternate to get highest score possible to begin with
				for(int i=0; i<player.getHand().cards.size(); i++){
					if(player.getHand().cards.get(i).getNumber() == Numbers.ACE){
						aceCount++;
						player.getAlternateTracker().set(i, true);
					}
				}
				
				// get the player's score with all aces as 11
				getScore(player);
				
				
				
				
				
				// now the player has the highest score possible, 
				//now we drop aces one by one and see if it's under the thresh-hold
				
				// if the player has an ace and their score is over 21
				// they can reduce their score
				
				
				
				
				// while the player's score is a bust but they can swap an ace down
				while(comparer.compare(player.getScore(), 21) == 1){
					if(aceCount > 0) {
					//		comparer will return:
					//		 1 if over 21     -- bust
					//		 0 if exactly 21
					//		-1 if less than 21
						gotToEndOfAlternateTracker = false;
						
						for(int i=0; i<player.getAlternateTracker().size(); i++){
							// on the first true we find
							// set it to false and stop looping
							if(player.getAlternateTracker().get(i) == true){
								player.getAlternateTracker().set(i, false);
								break;
							}
							
							if(i == player.getAlternateTracker().size() - 1){
								gotToEndOfAlternateTracker = true;
							}
						}
						
						// get the player's full score
						getScore(player);
						if(gotToEndOfAlternateTracker){
							break;
						}
					}
					else{
						break;
					}
				}
				
				
				
				
				// display current score
				System.out.println("\n\n" + player.getName() + ":\n" + player.getHand().toString());
				System.out.println("   " + player.getName()  + " has a score of: " + player.getScore());
				
				
				
				
				/////////////////////////////////
				// decide if player hits again //
				/////////////////////////////////
				
				// stop loop if 21 or bust
				if(player.getScore() > 21){
					System.out.println("   " + player.getName() + " Busted!!\n");
					looping = false;
				}
				else if(player.getScore() == 21){
					System.out.println("   BLACKJACK!!!!\n");
					looping = false;
				}	
				
				
				
				
				
				if(player instanceof Dealer){ 	// dealer stops at 17 or higher
					if(player.getScore() > 16){
						looping = false;
					}
					else{
						dealer.dealCard(player);
						System.out.println("Dealer takes another card...");
					}
				}
				
				
				// not a dealer
				// auto hit if you're score is under 9
				// change to "1" if you want to manually hit every turn
				else{
					threshholdForHit = 9;
					
					if(player.getScore() >= threshholdForHit && player.getScore() < 21){
						System.out.print("   \"h\" to hit, any other character to stay: ");
						hit = in.next();
						
						if(hit.equals("h")){
							System.out.println("   " + player.getName() + " hits...");
							looping = true;
						}
						else{
							looping = false;
						}
						
						if(looping){
							dealer.dealCard(player);
						}
					}
					else if(looping){
						dealer.dealCard(player);
						System.out.println(player.getName() + " hits...");
					}
				}
			}
		}
	}

	// ----------------------------------------------------------

	@Override
	public void displayWinner() {
		
		System.out.println("\n\n***********************************"
				+ "\n       RESULTS\n"
				+ "***********************************");
		
		
		
		// display the total scores and find out what the highest score was
		for(Player player : players){
			System.out.println(player.getName() + " - " + player.getScore());
			if(player.getScore() < 22 && player.getScore() > highestScore){
				highestScore = player.getScore();
			}
		}
		
		
		System.out.println("------------------------------");
		
		
		
		// all could have busted
		if(highestScore == 0){
			System.out.println("- Nobody wins, everyone busted");
		}
		else{
			// find out which players had the highest score
			// by removing any player that didn't have that score
			for(int i=players.size(); i>0; i--){
				if(players.get(i-1).getScore() != highestScore){
					players.remove(players.get(i-1));
				}
			}
			
			// single winner
			if(players.size() == 1){
				System.out.println("- " + players.get(0).getName() + " Wins!!!");
			}
			
			// tie
			else{
				System.out.print("- Tie between: ");
				
				for(int i=0; i<players.size(); i++){
					if(i == players.size() - 1){
						System.out.print(players.get(i).getName() + "\n");
					}
					else{
						System.out.print(players.get(i).getName() + " - ");
					}
					
				}
			}
		}
		
		System.out.println("------------------------------");
	}
	
	// ----------------------------------------------------------
	
	public void getScore(Player player){
		player.setScore(0); // start from 0
		
		for(int i=0; i<player.getHand().cards.size(); i++){
			if(player.getAlternateTracker().get(i) == true){
				player.setScore(player.getScore() + player.getHand().cards.get(i).getNumber().alternate());
			}
			else{
				player.setScore(player.getScore() + player.getHand().cards.get(i).getNumber().value());
			}
		}
	}
}
