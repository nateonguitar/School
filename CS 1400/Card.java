/****************************************************************
* Card.java
* Nathan Brooks
* 
* CS 1400
*
* This class stores a Card's information.
****************************************************************/

public class Card
{
  private int num;   // hold a number between 1 and 13
  private char suit; // holds 'C' for clubs, 'D' for diamonds,
                     // 'H' for hearts, 'S' for spades

  //**************************************************

  public Card(int num, char suit)
  {
    this.num = num;
    this.suit = suit;
  } // end Card constructor

  //**************************************************

  // Return the card's value in the form of a concatenated
  // number and character.
  // For example, 1C = ace of clubs, 12H = queen of hearts.

  public String toString()
  {
    return Integer.toString(num) + suit;
  }
} // end class Card