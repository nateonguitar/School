/****************************************************
* Nathan Brooks
* CS 1400
*
*This class has methods to run in Casino.java
*****************************************************/

import java.util.*;

public class Roulette
{
   public double betOnce(double bet)
   {
      Scanner console = new Scanner(System.in);
      int randomNumber = (int)(Math.random() * 37);
      int gameMode;
      double winnings = 0;
      int guess;
      
      System.out.println("Please select a bet method:\n" + 
      "1) Low (1 - 18)\n" + 
      "2) High (19 - 36)\n" +
      "3) Choose a specific number (higher returns)");
      gameMode = console.nextInt();
      
         // do one of these depending on gameMode's value
      switch(gameMode)
      {
            // if (gameMode = 1)
         case 1:
            if (randomNumber < 19)
            {
               winnings = bet * 2;
            }
            else
            {
               winnings = 0;
            }
            break;
         
         case 2:
            if (randomNumber > 18)
            {
               winnings = bet * 2;
            }
            else
            {
               winnings = 0;
            }
            break;
         
         case 3: 
            System.out.println("Please enter a number between 1 and 36");
            guess = console.nextInt();
            
            if (guess == randomNumber)
            {
               winnings = bet * 35;
            }
            else
            {
               winnings = 0;
            }
            break;
            
         default:
            break;         
      } // end switch
      
      return winnings;
      
   } // end betOnce()     
} // end Roulette class