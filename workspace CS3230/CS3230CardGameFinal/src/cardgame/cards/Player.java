package cardgame.cards;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Label;

public class Player {
	Hand hand = new Hand();
	int numRounds = 0;
	boolean winStatus = false;
	int score = 0;
	String name;
	Scanner in = new Scanner(System.in);
	int x;
	int y;
	Label nameLabel;
	
	// need to be able to handle if aces are a 1 or their alternate 11
	// will only hold an entry for every ace contained in the hand
	ArrayList<Boolean> alternateTracker = new ArrayList<>();
	
	//-----------------------------------------------------------------------------
	
	public Player(){
		this.x = 0;
		this.y = 0;
	}
	
	public Player(String name){
		this.name = name;
		this.nameLabel = new Label(name);
	}
	
	public Player(String name, int x, int y){
		this.x = 0;
		this.y = 0;
		this.name = name;
		this.nameLabel = new Label(name);
	}
	
	//-----------------------------------------------------------------------------
	
	public Label getNameLabel(){
		return this.nameLabel;
	}
	
	//-----------------------------------------------------------------------------
	
	public int getX(){
		return this.x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	//-----------------------------------------------------------------------------
	
	public int getY(){
		return this.y;
	}
	
	public void setY(int y){
		this.y = y;
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
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	//-----------------------------------------------------------------------------
	
	public void setName(String name){
		this.name = name;
	}
	
	//-----------------------------------------------------------------------------
	
	public void emptyHand(){
		this.hand.emptyGroupOfCards();
	}
}
