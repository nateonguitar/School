package brooksNathan;

public class GameDriver {

	public static void main(String[] args) {
		GameOfWar game = new GameOfWar();
		//GameOfBlackJack game = new GameOfBlackJack();
		game.initialize();
		game.play();
		game.displayWinner();
	}
}
