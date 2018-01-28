/****************************************************
* Dean & Dean
*
*This class contains constants and methods for fancy printing
*****************************************************/

public class PrintUtilities
{
   public static final int MAX_COL = 80; // last allowed collumn
   public static final int MAX_ROW = 50; // last allowed row
   
   //****************************************************
   
      // print given string horizontally centered
   public static void printCentered(String s)
   {
      int startingCol;  // starting point for string
      startingCol = (MAX_COL / 2) - (s.length() / 2);
      
      for (int i=0; i<startingCol; i++)
      {
         System.out.print(" ");
      }
      
      System.out.println(s);
   } // end printCentered()
   
   //****************************************************
   
      // Print given string with dashes underneath it.
   public static void printUnderlined(String s)
   {
      System.out.println(s);
      for (int i=0; i<s.length(); i++)
      {
         System.out.print("-");
      }
   } // end printUnderlined()
} // end class Print Utilities