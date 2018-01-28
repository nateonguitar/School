package brooksNathan;

public class GameOfWar implements Game {
	Deck deck = new Deck();
	Player player1 = new Player();
	Player player2 = new Player();
	Boolean someoneWon = false;
	int numRoundsPlayed = 0;
	int amountCardsWon = 0;
	final int MAX_NUM_ROUNDS = 10000;
	GroupOfCards pile;
	
	
	
	//**************************************************
	// the pile is meant for each player to throw cards into
	// is a GroupOfCards but has an extra method to give all
	// of it's cards to a hand
	class Pile extends GroupOfCards{
		Pile(){
			super();
		}
		
		// sticks cards at the end of the hand
		public void giveAllCardsToAHand(Hand hand){
			amountCardsWon = 0;
			int runTimes = cards.size();
			
			for(int i=0; i<runTimes; i++){
				Card temp = cards.getFirst();
				cards.removeFirst();
				hand.addLast(temp);
				
				amountCardsWon++;
			}
		}
	}
	//**************************************************
	
	
	//----------------------------------------------------------
	
	
	@Override
	public void initialize() {
		
		pile = new Pile(); // ^^^
		
		deck.shuffle();
		
		
		for(int i=(deck.getSize() / 2); i>0; i--){
			deck.dealCard(player1.getHand());
			deck.dealCard(player2.getHand());
		}
	}

	
	//----------------------------------------------------------
	
	
	@Override
	public void play() {
		Comparer comparer = (x, y) -> {
			if(x > y){
				return -1;
			}
			else if(x < y){
				return 1;
			}
			else{
				return 0;
			}
		};
		
		int result = 0;
		try{
			while(!someoneWon     &&     numRoundsPlayed < MAX_NUM_ROUNDS     &&     player1.getHand().getSize() != 0 &&  player2.getHand().getSize() != 0     ){
				result = comparer.compare(player1.getHand().getTop().number(), player2.getHand().getTop().number());
				
				System.out.println("Player 1 -> " + player1.getHand().getTop().faceValue() + " vs " + player2.getHand().getTop().faceValue() + " <- Player 2");
				
				player1.getHand().dealCard(pile);
				player2.getHand().dealCard(pile);
				
				
				checkIfPlayer1Wins(result);
				
				checkIfPlayer2Wins(result);
				
				// WAR!!!1!!!1111!!!
				checkIfWar(result);
			}
		}
		catch(IndexOutOfBoundsException e){
			someoneWon = true;
		}
		
		if(player1.getHand().getSize() > player2.getHand().getSize()){
			((Pile) pile).giveAllCardsToAHand(player1.getHand());
		}
		else if(player1.getHand().getSize() < player2.getHand().getSize()){
			((Pile) pile).giveAllCardsToAHand(player2.getHand());
		}
	}
	
	
	//----------------------------------------------------------
	
	
	@Override
	public void displayWinner() {
		if(player1.getHand().getSize() > player2.getHand().getSize()){
			System.out.println("Player 1 wins!!!");
		}
		else if(player1.getHand().getSize() < player2.getHand().getSize()){
			System.out.println("Player 2 wins!!!");
		}
		
		else{
			System.out.println("Tie!!!!");
		}
		
		System.out.println("------------------"
							+ "\nPlayer 1: " + player1.getHand().getSize() + " cards"
							+ "\nPlayer 2: " + player2.getHand().getSize() + " cards"
							+ "\n------------------");
		
	}
	
	public void checkIfPlayer1Wins(int result){
		// player1 wins round
		if(result == -1){
			numRoundsPlayed++;
			((Pile) pile).giveAllCardsToAHand(player1.getHand());
			System.out.println("Player 1 wins round " + numRoundsPlayed  
					+ "\nPlayer 1 won " + amountCardsWon + " cards"
					+ "\n------------------"
					+ "\nPlayer 1: " + player1.getHand().getSize() + " cards"
					+ "\nPlayer 2: " + player2.getHand().getSize() + " cards"
					+ "\n------------------\n\n\n");
		
		}
	}
	
	public void checkIfPlayer2Wins(int result){
		// player 2 wins round
		if(result == 1){
			numRoundsPlayed++;
			((Pile) pile).giveAllCardsToAHand(player2.getHand());
			System.out.println("Player 2 wins round " + numRoundsPlayed 
					+ "\nPlayer 2 won " + amountCardsWon + " cards"
					+ "\n------------------"
					+ "\nPlayer 1: " + player1.getHand().getSize() + " cards"
					+ "\nPlayer 2: " + player2.getHand().getSize() + " cards"
					+ "\n------------------\n\n\n");
		}
	}
	
	public void checkIfWar(int result){
		if(result == 0){
			System.out.println(
					  "***************************\n"
					+ "!!!! WAR !!!!\n"
					+ "***************************\n"
					+ "* Both players put 3 cards in the pile *\n\n");
			
			for(int i=0; i<3; i++){
				player1.getHand().dealCard(pile);
				player2.getHand().dealCard(pile);
			}
		}
	}
}
