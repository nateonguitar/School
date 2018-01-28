/****************************************************
* Nathan Brooks
*
* CS1400
* This class is the driver for the VowelCounter class
*****************************************************/

import java.util.Scanner;

public class VowelCounterDriver
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      String line;               // user entered line of text
      VowelCounter vowelCounter = new VowelCounter();
      
      System.out.print("Enter a line of characters (press enter by itself to quit): ");
      line = stdIn.nextLine();
      
      while (!line.isEmpty())
      {
         vowelCounter.processLine(line);
         System.out.println("Enter a line of characters (press enter by itself to quit): ");
         line = stdIn.nextLine();
      }
      
      vowelCounter.printSummary();
   
   }
} // end class VowelCounterDriver