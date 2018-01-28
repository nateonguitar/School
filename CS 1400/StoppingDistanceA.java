/*******************************************
* Nathan Brooks
*
* CS 1400 This program checks if you will 
* have a wreck depending on your tailgating 
* distance and speed.
*******************************************/

import java.util.Scanner;

public class StoppingDistanceA
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      double speed;
      double stoppingDistance;
      double tailgate;
            
      System.out.print("Enter your speed (in mph): ");
      speed = stdIn.nextDouble();
      
      stoppingDistance = speed * (2.25 + speed / 21);
      
      System.out.print("Enter your tailgating distance (in feet): ");
      tailgate = stdIn.nextDouble();
      
      if (tailgate > stoppingDistance)
      {
         System.out.println("No problem.");
      }
      
      if (tailgate == stoppingDistance)
      {
         System.out.println("Minor wreck.");
      }
      
      else 
      {
         System.out.println("Major wreck!!!");
      }
   } // end main
} // end class StoppingDistanceA