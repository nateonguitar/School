/*******************************************
* Nathan Brooks
*
* CS 1400 
*
* This program lets you enter a phone number in
* and splits its pieces up into segments.
*
* It also makes sure you don't type any words.
*******************************************/

import java.util.Scanner;

public class MouseDriver
{
   public static void main (String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      double growthRate;
      Mouse gus = new Mouse();
      Mouse jaq = new Mouse();
      
      System.out.print("Enter % growth rate: ");
      growthRate = stdIn.nextDouble();
      gus.setPercentGrowthRate(growthRate);
      jaq.setPercentGrowthRate(growthRate);
      gus.grow();
      jaq.grow();
      gus.grow();
      gus.display();
      jaq.display();
   } // end main
} // end of class MouseDriver