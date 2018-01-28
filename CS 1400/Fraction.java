/****************************************************
* Dean & Dean
*
*This class stores and prints fractions
*****************************************************/

public class Fraction
{
   private int numerator;
   private int denominator;
   private double quotient;   // numerator / denominator
   
   //********************************************************
   
   public Fraction(int n)
   {
      this(n, 1); // this statement calls the other constructor
   }
   
   //********************************************************
   
   public Fraction(int n, int d)
   {
      this.numerator = n;
      this.denominator = d;
      this.quotient = (double) this.numerator / this.denominator;
   }
   
   //********************************************************
   
   public void printIt()
   {
      System.out.println(this.numerator + " / " + this.denominator + " = " + this.quotient);
   } // end printIt()
} // end class Fraction