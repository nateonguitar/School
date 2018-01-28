/*************************************************************
* DeckDriver.java
* Nathan Brooks
* 
* CS 1400
*
* This class tests the Deck class.
*************************************************************/

public class DeckDriver
{
  public static void main(String[] args)
  {
    Deck deck = new Deck();
    System.out.println(deck.dealCard());
    System.out.println(deck.dealCard());
    System.out.println();
    System.out.println(deck);
  } // end main
} // end DeckDriver class