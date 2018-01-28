package brooksNathan;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	Hand hand = new Hand();
	int numRounds = 0;
	boolean winStatus = false;
	int score = 0;
	String name;
	Scanner in = new Scanner(System.in);
	
	// need to be able to handle if aces are a 1 or their alternate 11
	// will only hold an entry for every ace contained in the hand
	ArrayList<Boolean> alternateTracker = new ArrayList<>();
	
	//-----------------------------------------------------------------------------
	
	public Player(){
		
	}
	
	//-----------------------------------------------------------------------------
	
	public Hand getHand(){
		return this.hand;
	}
	
	//-----------------------------------------------------------------------------
	
	public boolean getWinStatus(){
		return winStatus;
	}
	
	//-----------------------------------------------------------------------------
	
	public void setWinStatus(boolean status){
		this.winStatus = status;
	}
	
	//-----------------------------------------------------------------------------
	
	public ArrayList<Boolean> getAlternateTracker(){
		return this.alternateTracker;
	}
	
	//-----------------------------------------------------------------------------
	
	public void resetAlternateTracker(){
		// delete all elements in the tracker
		alternateTracker.clear();
		
		// add an entry for each card the player has
		for(Card card : getHand().cards){
			alternateTracker.add(false);
		}
	}
	
	//-----------------------------------------------------------------------------
	
	public int getScore(){
		return this.score;
	}
	
	//-----------------------------------------------------------------------------
	
	public void setScore(int score){
		this.score = score;
	}
	
	//-----------------------------------------------------------------------------
	
	public String getName(){
		return this.name;
	}
	
	//-----------------------------------------------------------------------------
	
	public void setName(String name){
		this.name = name;
	}
}
