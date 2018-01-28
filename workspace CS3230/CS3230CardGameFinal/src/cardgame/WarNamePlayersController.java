package cardgame;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import cardgame.cards.Player;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class WarNamePlayersController implements Initializable, ControlledScreen {
	ScreensController myController;
	ArrayList<TextField> names;
	boolean readyToSave = false;
	
	@FXML
	CheckBox checkbox_Autoplay;
	@FXML
	AnchorPane CardGameMainAnchorPane;
	
	@FXML
	TextField warName1;
	
	@FXML
	TextField warName2;
	
	@FXML
	Button btn_warStart;
	
	@FXML
	Button btn_warSaveNames;
	
	@FXML
	Label label_PlaySpeedPercent;
	
	@FXML
	Slider sliderWarPlaySpeed;
	
	@Override
	public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		names = new ArrayList<>();
		this.sliderWarPlaySpeed.valueProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            	Game.warSpeed = (int) sliderWarPlaySpeed.getValue();
            	label_PlaySpeedPercent.setText(String.valueOf(Game.warSpeed) + "%");
            }
        });
	}
	
	public void closeProgram(){
		Platform.exit();
	}
	
	public void nameYourPlayers(){
		btn_warStart.setDisable(true);
		btn_warSaveNames.setDisable(false);
		addPlayers();
		// name your players
		// testIfReadyToSave() will be called on every keypress
		for(TextField name : names){
			name.setText("");
		}
		
		for(TextField name : names){
			name.setDisable(false);
		}
		
		warName1.requestFocus();
	}
	
	@FXML
	public void disableStartOnKeyPress(KeyEvent event){
		btn_warStart.setDisable(true);
	}
	
	public void useDefaultNames(){
		Game.players.clear();
		
		addPlayers();
		
		names.get(0).setText("Opponent");
		names.get(1).setText("Player");
		
		saveNames();
		btn_warSaveNames.setDisable(true);
		btn_warStart.setDisable(false);
	}
	
	public void testIfReadyToSave(){
		// once it's ready to save, this method will flip the Save button to not disabled
		// and the save button will instantiate the players and give them names
		
		// .replaceAll("\\s+","") removes white space: spaces, tabs, etc.
		for(int i=0; i<Game.warPlayers; i++){
			if(names.get(i).getText().replaceAll("\\s+","").equals("")){
				readyToSave = false;
				btn_warStart.setDisable(true);
				break;
			}
			else{
				readyToSave = true;
			}
		}
		
		if(readyToSave){
			btn_warSaveNames.setDisable(false);
		}
	}
	
	public void saveNames(){
		Game.players.clear();

		// give the Game.players ArrayList the correct number of players and name them appropriately
		for(int i=0; i<Game.warPlayers; i++){
			Game.players.add(new Player(names.get(i).getText()));
		}
		
		testIfReadyToSave();
		
		if(readyToSave){
			btn_warStart.setDisable(false);
		}
		
	}
	
	public void startWar(){
		Game.warAutoplay = checkbox_Autoplay.isSelected();
		myController.setScreen(ScreensFramework.screen7ID);
	}
	
	public void backToMenu(){
		myController.setScreen(ScreensFramework.screen2ID);
	}
	
	public void backToSetup(){
		myController.setScreen(ScreensFramework.screen3ID);
	}
	
	public void addPlayers(){
		// addPlayers() will delete all elements from names arraylist and fill with new names
		// this ensures that pressing the buttons back and forth doesn't add more than just the
		// right amount of players
		
		names.clear();
		
		names.add(0, warName1);
		names.add(1, warName2);
	}
	
	public void disableStartOnKeyPress(){
		//You must press "save" if you want to start the game
		btn_warStart.setDisable(true);
	}
}
