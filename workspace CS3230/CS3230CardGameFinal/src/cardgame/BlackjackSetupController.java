package cardgame;

import java.net.URL;
import java.util.ResourceBundle;

import cardgame.cards.Card;
import cardgame.cards.Numbers;
import cardgame.cards.Suits;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

public class BlackjackSetupController implements Initializable, ControlledScreen {
	ScreensController myController;
	
	@FXML
	AnchorPane CardGameMainAnchorPane;
	
	@FXML
	Label labelPlayersCount;
	
	@FXML
	Label labelDecksCount;
	
	@FXML
	Slider sliderDecks;
	
	@FXML
	Slider sliderPlayers;
	
	Card card;
	
	@Override
	public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.sliderDecks.valueProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            	Game.blackjackDecks = (int) sliderDecks.getValue();
        		labelDecksCount.setText(String.valueOf(Game.blackjackDecks));
            }
        });
		
		this.sliderPlayers.valueProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            	Game.blackjackPlayers = (int) sliderPlayers.getValue();
        		labelPlayersCount.setText(String.valueOf(Game.blackjackPlayers));
            }
        });
	}
	
	public void closeProgram(){
		Platform.exit();
	}
	
	public void goToBlackjackNamePlayers(){
		myController.setScreen(ScreensFramework.screen4ID);
	}
	
	public void backToMenu(){
		myController.setScreen(ScreensFramework.screen2ID);
	}
}