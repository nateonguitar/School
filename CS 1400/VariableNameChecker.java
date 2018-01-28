/*******************************************
* Nathan Brooks
*
* CS 1400 
*
* A program that checks if you've made an
* acceptable variable name
*******************************************/
import javax.swing.JOptionPane;
import java.util.Scanner;

public class VariableNameChecker
{
   public static void main(String[] args)
   {
      Scanner stdIn = new Scanner(System.in);
        
      String line;         // user-entered variable name
      char ch;
      boolean legal;       // is it a legal variable?
      boolean goodStyle;   // is it a well styled variable?
      
      System.out.println("This program checks the properness of a proposed Java variable name.");
      
      System.out.println("Enter a variable name or 'q' to quit: ");
      line = stdIn.nextLine();
      ch = line.charAt(0);
      
      while (ch != 'q' || line.length() > 1 )
      {
         legal = true;
         goodStyle = true;
         
         if (!(Character.isLetter(ch) || ch == '$' || ch == '_'))
         {
            legal = false;
         }
         else if (!(Character.isLowerCase(ch)))
         {
            goodStyle = false;
         }
         
         
         for (int i=1; i<line.length() && legal; i++)
         {
            ch = line.charAt(i);
            if (!(Character.isLetterOrDigit(ch) || ch == '$' || ch == '_'))
            {
               legal = false;
            }
            else if (!Character.isLetterOrDigit(ch))
            {
               goodStyle = false;
            }
         }
      
         if (legal && goodStyle)
         {
            System.out.println("Good");
         }
         else if (legal && !goodStyle)
         {
            System.out.println("Bad style, but your variable will work.");
         }
         else
         {
            System.out.println("Illegal.");
         }
         
         System.out.println("Enter a variable name or 'q' to quit: ");
         line = stdIn.nextLine();
         ch = line.charAt(0);
      } // end of while     
   } // end main
} // end class VariableNameChecker