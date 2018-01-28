/****************************************************
* Dean & Dean
*
*This driver class demonstrates the Fraction class
*****************************************************/

public class FractionDriver
{
   public static void main(String[] args)
   {
      Fraction a = new Fraction(3, 4);    // calls to overloaded constructor
      Fraction b = new Fraction(3);       // calls to overloaded constructor
      
      a.printIt();
      b.printIt();
   } // end main
} // end class FractionDriver