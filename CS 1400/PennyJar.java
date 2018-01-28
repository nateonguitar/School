/****************************************************
* Dean & Dean
*
*This class counts pennies stored in a group of penny jars
*****************************************************/
   
public class PennyJar
{
      // class variables
   private static int goal;
   private static int allPennies = 0;
   private int pennies = 0;

   //**********************************************************
   
      // class method
   public static void setGoal(int goal)
   {
      PennyJar.goal = goal;
   }
   
   //**********************************************************
   
      // class method
   public static int getAllPennies()
   {
      return PennyJar.allPennies;
   }
   
   //**********************************************************   
   
   public int getPennies()
   {
      return this.pennies;
   }
   
   //**********************************************************
   
   public void addPenny()
   {
      System.out.println("Clink!");
      this.pennies++;
      PennyJar.allPennies++;
      
      if (PennyJar.allPennies >= PennyJar.goal)
      {
      
      }  System.out.println("Time to spend!");
   } // end addPenny
} // end class PennyJar   