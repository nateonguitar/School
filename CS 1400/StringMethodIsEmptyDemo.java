/*******************************************
* Nathan Brooks
*
* CS 1400 
* This program exercises the String class's 
* isEmpty method
*******************************************/

import java.util.Scanner;

public class StringMethodIsEmptyDemo
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      String name;
      
      System.out.print("Enter yoru name: ");
      name = stdIn.nextLine();
      
      while (name.isEmpty())
      {
         System.out.print("Invalid entry.  You must enter your name: ");
         name = stdIn.nextLine();  
      }
      System.out.println("Hello, " + name + "!");   
   } // end main
} // end class StringMethodIsEmptyDemo