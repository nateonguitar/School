/*******************************************
* Nathan Brooks
*
* CS 1400 
* This program checks the validity of a 
* proposed Java identifier
*******************************************/

import java.util.Scanner;

public class IdentifierChecker
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
      String line;            // user entry
      char ch;
      boolean legal = true;   // is entered line a legal identifier?
      
      System.out.println("This program checks the validity of a proposed Java identifier");
      System.out.print("Enter a proposed identifyer: ");
      line = stdIn.nextLine();
      ch = line.charAt(0);
      
      if (!(Character.isLetter(ch) || ch == '$' || ch == '_'))
      {
         legal = false;
      }
      
      for (int i=1; i<line.length() && legal; i++)
      {
         ch = line.charAt(i);
         if (!(Character.isLetterOrDigit(ch) || ch == '$' || ch == '_'))
         {
            legal = false;
         }
      }
      
      if (legal)
      {
         System.out.println("Congratulations, " + line + " is a legal Java identifier.");
      }
      else
      {
         System.out.println("Sorry, " + line + " is not a legal Java identifier.");
      }
   } // end main
} // end class IdentifierChecker