/*******************************************
* Nathan Brooks
*
* CS 1400 
*This program simulates dice throws, allows you to enter as many
*Throws as you like, and prints them out in a graph.
*******************************************/

import java.util.Scanner;

public class DiceSimulation
{
   Scanner stdIn = new Scanner(System.in);
   int numRolls = 0;                         // user specified number of rolls
   int[] frequency = new int[13];            // 11 possible outcomes, 2 through 12
   int totalRolls = 0;                       // add up all rolls
   
   //***************************************************************
   //***************************************************************
   
      // This method clears out the frequency array (assigns all elements to zero), 
      // prompts the user for the number of rolls, and then simulates the rolls.
   public void newSimulation()
   {
         // set the values in the array to 0
      final int CLEAR = 0;
      
      for (int i = 0; i < frequency.length; i++)
      {
         frequency[i] = CLEAR;
      }
         // set the roll counter back to 0
      totalRolls = CLEAR;
      
      additionalRolls();
   } // end newSimulation()
   
   //***************************************************************
   //***************************************************************
   
      // This method prompts the user for the number of rolls that should be added 
      // to the current dice-rolling simulation. It then simulates the additional rolls.
   public void additionalRolls()
   {
   System.out.print("How many dice rolls would you like to simulate? ");
      numRolls = stdIn.nextInt();
      
         // To help with calculating in the print statement
      totalRolls += numRolls;
      
      for (int j = 0; j < numRolls; j++)
      {
            // roll dice
         int rollDice = ((int) (Math.random() * 6 + 1)) + ((int) (Math.random() * 6 + 1));
         
         frequency[rollDice]++;
      } // end for
   }   
   
   //***************************************************************
   //***************************************************************
   
      //  This method prints the dice-rolling simulation results.
   public void printReport()
   {
   System.out.println("---------------------------------------------");
   System.out.println("DICE ROLLING SIMULATION RESULTS");
      System.out.println("\nEach \"*\" represents 1% of the total number of rolls\n" +
         "(unless you roll a very small number of times)\n");
      System.out.println("Total number of rolls = " + totalRolls);
      
         // print with an extra space to make them line up right
      for (int j = 2; j < 10; j++)
      {
            // if frequency[j] = 0 you get an error, can't divide 0 or you get errors.
            // 150 was a good number to get a similar sized graph before you start dividing.
         System.out.print(j + " : ");
         if (totalRolls < 150)
         {
            for (int i=0; i < frequency[j]; i++)
            {
               System.out.print("*");
            }
            System.out.println();
         }
         else
         {
            {
               System.out.print("*");
            }
            System.out.println();
         }
      }
      
         // print without an extra space to make them line up right
      for (int j = 10; j < frequency.length; j++)
      {
         System.out.print(j + ": ");
         
         if (totalRolls < 150)
         {
            for (int i=0; i < frequency[j]; i++)
            {
               System.out.print("*");
            }
            System.out.println();
         }
         else
         {
            for (int i=0; i < (frequency[j]) / (totalRolls / 100); i++)
            {
               System.out.print("*");
            }
            System.out.println();
         }
      }
      System.out.println("---------------------------------------------\n\n"); 
   }  
   //***************************************************************
   //***************************************************************
} // end class DiceSimulation