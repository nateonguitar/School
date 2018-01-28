import java.util.Random;

public class PlayingCardsTest
{
   public static void main( String args[] )
   {
      Deck game = new Deck();
      Random rand = new Random();
      
      System.out.printf( "The size of the deck is %d cards\n", game.getDeckSize() );
      System.out.println( game.toString() );
      
      game.shuffle();
      
   }
}