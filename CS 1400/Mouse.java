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

public class Mouse
{
   private int age = 0;                // age of mouse in days
   private double weight = 1.0;        // mouse weight in grams
   private double percentGrowthRate;   // increase per day
   
   //************************************************************************************
   
      // This method assigns the mouse's percent growth rate.
   
   public void setPercentGrowthRate(double percentGrowthRate)
   {
         // -this- is used to access instance variables, use this dot.
      this.percentGrowthRate = percentGrowthRate;   
   } // end setPercentGrowthRate
   
   
   
   
   
   //************************************************************************************
   
      // This method simulates one day of growth for the mouse.
      
   public void grow()
   {
      this.weight += (.01 * this.percentGrowthRate * this.weight);
      this.age++;
   } // end grow   
   
   
   
   //************************************************************************************
   
      // This method prints t he mouse's age and weight.
      
   public void display()
   {
      System.out.printf("Age = %d, weight = %.3f\n", this.age, this.weight);
   } // end display
      
   //************************************************************************************
} // end of class Mouse