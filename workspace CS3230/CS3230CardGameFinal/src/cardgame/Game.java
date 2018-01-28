package cardgame;

import java.util.ArrayList;

import cardgame.cards.Player;

final class Game {
	public static int blackjackPlayers = 2;
	public static int blackjackDecks = 1;
	public static final int warPlayers = 2;
	public static ArrayList<Player> players = new ArrayList<>();
	public static boolean blackjackNameYourPlayers = false;
	public static boolean warAutoplay = true;
	public static int warSpeed = 100;
}
