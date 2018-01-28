/*******************************************
* Nathan Brooks
*
* CS 1400 
*
* Simulates rolling dice until the user rolls doubles
*******************************************/

import java.util.Scanner;

public class RollDice
{
   public static void main(String args[])
   {
      Scanner stdIn = new Scanner(System.in);
      int die1, die2; // value of 2 dice
      
      System.out.println("Can you roll doubles?");
      
      do
      {
         System.out.print("Press enter to roll the dice:");
         stdIn.nextLine();
         die1 = rollDie();          // method call
         die2 = rollDie();          // method call
         printResult(die1, die2);   // method call
      } while (die1 != die2);
   } // end main
   
//************************************************************
   
      // this method returns the value of a randomly rolled die.   
   public static int rollDie()
   {
      return (int) (Math.random() * 6 + 1);
   }  // end rollDie
   
//************************************************************
   
      // Print the dice value and whether doubles were rolled. 
                              // (    parameters    )  
   public static void printResult(int die1, int die2) // method heading
   {
      if (die1 == die2)
      {
         System.out.printf("Doubles! You rolled two %d's." +
            " Thank you for playing!\n", die1);
      }
      else
      {
         System.out.printf("No doubles. You rolled a %d and a %d." +
            " Try again.\n", die1, die2);
      }
   } // end printResult
} // end of class RollDice