/*******************************************
* Nathan Brooks
*
* CS 1400 This program checks if you will 
* have a wreck depending on your tailgating 
* distance and speed +/- 40 feet.
*******************************************/

import java.util.Scanner;

public class StoppingDistance
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      
      double speed;
      double stoppingDistance;
      double tailgateDistance;
      double range = 40.0;
            
      System.out.print("Enter your speed (in mph): ");
      speed = stdIn.nextDouble();
      
      stoppingDistance = speed * (2.25 + speed / 21);
      
      System.out.print("Enter your tailgating distance (in feet): ");
      tailgateDistance = stdIn.nextDouble();
      
      if (tailgateDistance < (stoppingDistance + range) && tailgateDistance > (stoppingDistance - range / 2))
      {
         System.out.println("Minor wreck.");
      }
      
      if (tailgateDistance > (stoppingDistance + range / 2))
      {
         System.out.println("No problem.");
      }
      
      else
      {
         System.out.println("Major wreck!!!");
      }
   } // end main
} // end class StoppingDistance