/****************************************************************
* Card.java
* Nathan Brooks
* 
* CS 1400
*
* implements the functionality of a deck of cards.
****************************************************************/

import java.util.ArrayList;

public class Deck
{
      // Deck constructor:
   private ArrayList<Card> deck = new ArrayList<Card>();
   
   //***********************************************************
      
      // This method removes the highest-indexed card in the ArrayList and returns it
      // In general, a method should not do more than what it’s supposed to do. 
      // Thus, dealCard should not print anything.
      public Deck()
      {
         for (int i=1; i<=13; i++)
         {
            deck.add(new Card(i, 'C'));
         }
         
         for (int i=1; i<=13; i++)
         {
            deck.add(new Card(i, 'D'));
         }
         
         for (int i=1; i<=13; i++)
         {
            deck.add(new Card(i, 'H'));
         }
         
         for (int i=1; i<=13; i++)
         {
            deck.add(new Card(i, 'S'));
         }
      }
   
   //***********************************************************
   
   public Card dealCard()
   {
      return deck.remove(deck.size() - 1);
   }
   
   //***********************************************************
   
      // This method returns the deck’s contents using the format shown in the output session. 
      // In particular, note that toString should insert a newline after every fifth card. 
      // Hint: In coming up with a return value, use a String local variable. 
      // As you generate card values and newlines, concatenate those items to your local 
      // variable using the += operator.
   public String toString()
   {
      String contents = "";
      
      for (int i=0; i<deck.size(); i++)
      {
         contents += deck.get(i) + " ";
         if ((i+1) % 5 == 0)
         {
            contents += "\n";
         }
      } // end for
      
      return contents;
   } // end toString()
} // end class Deck