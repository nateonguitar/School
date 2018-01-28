/*******************************************
* Nathan Brooks
*
* CS 1400 
*
*******************************************/

import java.util.Scanner;

public class Practice
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      
      double value;
      double absoluteValue;
      
      System.out.print("Enter a number to find the absolute value: ");
      value = stdIn.nextDouble();
      
      absoluteValue = Math.abs(value);
      System.out.println("The absolute value of " + value + " is " + absoluteValue + ".");
   } // end main
} // end class Practice