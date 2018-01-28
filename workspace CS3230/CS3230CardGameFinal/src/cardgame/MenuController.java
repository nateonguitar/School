package cardgame;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import cardgame.cards.Card;
import cardgame.cards.Deck;
import cardgame.cards.Numbers;
import cardgame.cards.Suits;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class MenuController implements Initializable, ControlledScreen {
	ScreensController myController;
	ArrayList<Card> cards = new ArrayList<>();
	
	@FXML
	AnchorPane CardGameMainAnchorPane;
	
	@Override
	public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		cards.add(new Card(Numbers.QUEEN, Suits.HEARTS, 165, 300, 0));
		cards.add(new Card(Numbers.KING, Suits.HEARTS, 165, 300, 0));
		cards.add(new Card(Numbers.ACE, Suits.HEARTS, 165, 300, 0));
		
		cards.add(new Card(Numbers.JACK, Suits.CLUBS, 555, 300, 0));
		cards.add(new Card(Numbers.JACK, Suits.DIAMONDS, 555, 300, 0));
		cards.add(new Card(Numbers.JACK, Suits.SPADES, 555, 300, 0));
		
		for(int i=0; i<cards.size(); i++){
			CardGameMainAnchorPane.getChildren().addAll(cards.get(i).getFace());
		}
		
		cards.get(0).moveToRelative(-25, 8, 8750, 0, true, -20);
		cards.get(1).moveToRelative(1, 1, 9000, 0, true, 0);
		cards.get(2).moveToRelative(25, 8, 9250, 0, true, 20);
		
		cards.get(3).moveToRelative(-25, 8, 9500, 0, true, -20);
		cards.get(4).moveToRelative(1, 1, 9750, 0, true, 0);
		cards.get(5).moveToRelative(25, 8, 10000, 0, true, 20);
		
	}

	public void startBlackjack(){
		myController.setScreen(ScreensFramework.screen3ID);
	}
	
	public void startWar(){
		myController.setScreen(ScreensFramework.screen6ID);
	}
	
	public void closeProgram(){
		Platform.exit();
	}
}
