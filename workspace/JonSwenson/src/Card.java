import java.util.*;

public class Card
{
   private static String[] suits = { "Hearts", "Spades", "Diamonds", "Clubs" };
   private static String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
   
   private int rank;
   private int suit;
   private static boolean faceUp;
   
   public Card( int suit, int rank )
   {
      this.suit = suit;
      this.rank = rank;
   } 
   
   public int getRank()
   {
      return rank;
   }
   
   public int getSuit()
   {
      return suit;
   }
   
   public boolean faceUpOrDown( boolean face )
   {  
      if( face == true )
      {
         faceUp = true;
         return true;
      }
      else
      {
         faceUp = false;
         return false;
      }
   }
   
   @Override
   public String toString()
   {
      if( faceUp = true )
      {
         return ranks[ rank ] + " of " + suits[ suit ] + "\n";
      }
      else
      {
         return "This card is face down";
      }
   }
}